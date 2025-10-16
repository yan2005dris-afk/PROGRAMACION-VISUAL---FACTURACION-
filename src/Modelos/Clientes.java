/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import javafx.collections.ObservableList;

/**
 * Representa un cliente del sistema.
 * Contiene datos personales, de contacto y estado.
 * @author Alejandro
 */
public class Clientes {
    
    private int cli_id;
    private String cli_cedula, cli_apellidos, cli_nombres, cli_direccion, cli_telefono, cli_correo, cli_estado;
    
    // Constructor vacío (necesario para JavaFX y frameworks)
    public Clientes() {
    }
    
    // Constructor sin ID ni estado (para nuevos registros)
    public Clientes(String cedula, String apellidos, String nombres, String direccion, String telefono, String correo) {
        this.cli_cedula = cedula;
        this.cli_apellidos = apellidos;
        this.cli_nombres = nombres;
        this.cli_direccion = direccion;
        this.cli_telefono = telefono;
        this.cli_correo = correo;
    }
    
    // Constructor completo
    public Clientes(int id, String cedula, String apellidos, String nombres, String direccion, 
                    String telefono, String correo, String estado) {
        this.cli_id = id;
        this.cli_cedula = cedula;
        this.cli_apellidos = apellidos;
        this.cli_nombres = nombres;
        this.cli_direccion = direccion;
        this.cli_telefono = telefono;
        this.cli_correo = correo;
        this.cli_estado = estado;
    }
    
    // Getters
    public int getCli_id() {
        return cli_id;
    }
    
    public String getCli_cedula() {
        return cli_cedula;
    }

    public String getCli_apellidos() {
        return cli_apellidos;
    }

    public String getCli_nombres() {
        return cli_nombres;
    }

    public String getCli_direccion() {
        return cli_direccion;
    }

    public String getCli_telefono() {
        return cli_telefono;
    }

    public String getCli_correo() {
        return cli_correo;
    }
    
    public String getCli_estado() {
        return cli_estado;
    }
    
    // Setters
    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }
    
    public void setCli_cedula(String cli_cedula) {
        this.cli_cedula = cli_cedula;
    }

    public void setCli_apellidos(String cli_apellidos) {
        this.cli_apellidos = cli_apellidos;
    }

    public void setCli_nombres(String cli_nombres) {
        this.cli_nombres = cli_nombres;
    }

    public void setCli_direccion(String cli_direccion) {
        this.cli_direccion = cli_direccion;
    }

    public void setCli_telefono(String cli_telefono) {
        this.cli_telefono = cli_telefono;
    }

    public void setCli_correo(String cli_correo) {
        this.cli_correo = cli_correo;
    }
    
    public void setCli_estado(String cli_estado) {
        this.cli_estado = cli_estado;
    }

    @Override
    public String toString() {
        return cli_nombres + " " + cli_apellidos + " (" + cli_cedula + ")";
    }
    
    public ObservableList<Clientes> getListaClientes(String ConsultaSQL){
        General.BD accBD= new General.BD();
        ObservableList<Clientes> listaAux=accBD.getListaConsulta(ConsultaSQL, 
            rs->{
                try {
                    return new Clientes(
                            rs.getInt("cli_id"),
                            rs.getString("cli_cedula"),
                            rs.getString("cli_apellidos"),
                            rs.getString("cli_nombres"),
                            rs.getString("cli_direccion"),
                            rs.getString("cli_telefono"),
                            rs.getString("cli_correo"),
                            rs.getString("cli_estado")
                            
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                
            }
        );
        return listaAux;
    }
}

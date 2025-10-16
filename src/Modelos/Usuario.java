/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import javafx.collections.ObservableList;

/**
 * Representa a un usuario del sistema. 
 * Almacena información de identificación, credenciales de acceso, estado y 
 * perfil de rol.
 * @author Alejandro
 */
public class Usuario {
    
    private int usr_id;
    private int per_id;
    private String usr_nombres;
    private String usr_usuario;
    private String usr_clave;
    private String usr_estado;
    

    // Constructor vacío (necesario para JavaFX y frameworks)
    public Usuario() {
    }

    // Constructor con parámetros

    public Usuario(int usr_id, int per_id, String usr_nombres, String usr_usuario, String usr_clave, String usr_estado) {
        this.usr_id = usr_id;
        this.per_id = per_id;
        this.usr_nombres = usr_nombres;
        this.usr_usuario = usr_usuario;
        this.usr_clave = usr_clave;
        this.usr_estado = usr_estado;
    }
    
    // GETTERS
    public int getUsr_id() {
        return usr_id;
    }

    public String getUsr_nombres() {
        return usr_nombres;
    }

    public String getUsr_usuario() {
        return usr_usuario;
    }

    public String getUsr_clave() {
        return usr_clave;
    }

    public String getUsr_estado() {
        return usr_estado;
    }

    public int getPer_id() {
        return per_id;
    }
    
    // SETTERS
    public void setUsr_id(int usr_id) {
        this.usr_id = usr_id;
    }

    public void setUsr_nombres(String usr_nombres) {
        this.usr_nombres = usr_nombres;
    }

    public void setUsr_usuario(String usr_usuario) {
        this.usr_usuario = usr_usuario;
    }

    public void setUsr_clave(String usr_clave) {
        this.usr_clave = usr_clave;
    }

    public void setUsr_estado(String usr_estado) {
        this.usr_estado = usr_estado;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }
    
    @Override
    public String toString() {
        return usr_nombres + " (" + usr_usuario + ") ";
    }
    
    public ObservableList<Usuario> getListaUsuarios(String ConsultaSQL){
        General.BD accBD= new General.BD();
        ObservableList<Usuario> listaAux=accBD.getListaConsulta(ConsultaSQL, 
            rs->{
                try {
                    return new Usuario(
                            rs.getInt("usr_id"),
                            rs.getInt("per_id"),
                            rs.getString("usr_nombres"),
                            rs.getString("usr_usuario"),
                            rs.getString("usr_clave"),
                            rs.getString("usr_estado")
                            
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


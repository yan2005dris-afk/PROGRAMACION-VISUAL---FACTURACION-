/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import javafx.collections.ObservableList;

/**
 * Representa una entidad empresarial dentro del sistema.
 * Almacena información de identificación, contacto y ubicación, y está diseñada 
 * para integrarse con JavaFX y otros frameworks que requieren un constructor 
 * vacío y métodos de acceso
 * @author Alejandro
 */
public class Empresa {
    
    private String emp_ruc;
    private String emp_nombre;
    private String emp_direccion;
    private String emp_telefono;
    //private String emp_direccionSucursal;
    private String emp_estado;

    // Constructor vacío (necesario para JavaFX y frameworks)
    public Empresa() {
    }
    
    // Constructor con parámetros
    public Empresa(String emp_ruc, String emp_nombre, String emp_direccion, String emp_telefono, String emp_estado) {
        this.emp_ruc = emp_ruc;
        this.emp_nombre = emp_nombre;
        this.emp_direccion = emp_direccion;
        this.emp_telefono = emp_telefono;
        this.emp_estado = emp_estado;
    }
    
    // GETTERS
    public String getEmp_ruc() {
        return emp_ruc;
    }

    public String getEmp_nombre() {
        return emp_nombre;
    }

    public String getEmp_direccion() {
        return emp_direccion;
    }

    public String getEmp_telefono() {
        return emp_telefono;
    }

    public String getEmp_estado() {
        return emp_estado;
    }
    
    /*public String getEmp_direccionSucursal() {
        return emp_direccionSucursal;
    }*/
    
    // SETTERS
    public void setEmp_ruc(String emp_ruc) {
        this.emp_ruc = emp_ruc;
    }

    public void setEmp_nombre(String emp_nombre) {
        this.emp_nombre = emp_nombre;
    }

    public void setEmp_direccion(String emp_direccion) {
        this.emp_direccion = emp_direccion;
    }

    public void setEmp_telefono(String emp_telefono) {
        this.emp_telefono = emp_telefono;
    }

    public void setEmp_estado(String emp_estado) {
        this.emp_estado = emp_estado;
    }
    /*public void setEmp_direccionSucursal(String emp_direccionSucursal) {
        this.emp_direccionSucursal = emp_direccionSucursal;
    }*/

    @Override
    public String toString() {
        return emp_nombre + " (RUC: " + emp_ruc + ")";
    }
    
    public ObservableList<Empresa> getListaEmpresas(String ConsultaSQL){
        General.BD accBD= new General.BD();
        ObservableList<Empresa> listaAux=accBD.getListaConsulta(ConsultaSQL, 
            rs->{
                try {
                    return new Empresa(
                            rs.getString("emp_ruc"),
                            rs.getString("emp_nombre"),
                            rs.getString("emp_direccion"),
                            rs.getString("emp_telefono"),
                            rs.getString("emp_estado")
                            
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

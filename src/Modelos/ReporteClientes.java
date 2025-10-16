/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 * Es un modelo de datos simplificado que representa información resumida de un 
 * cliente.
 * Está pensada para ser utilizada en listados, reportes o tablas donde no es 
 * necesario mostrar todos los datos del cliente, sino solo los más relevantes: 
 * cédula, nombres y teléfono.
 * @author Alejandro
 */
public class ReporteClientes {
    private String cedula;
    private String nombres;
    private String telefono;

    public ReporteClientes() {
    }

    public ReporteClientes(String cedula, String nombres, String telefono) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.telefono = telefono;
    }
    
    // GETTERS
    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombres() {
        return nombres;
    }
    
    // SETTERS
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

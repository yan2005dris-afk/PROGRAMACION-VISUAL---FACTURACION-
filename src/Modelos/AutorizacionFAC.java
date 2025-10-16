/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;

/**
 * Representa una autorización de facturación emitida por el SRI.
 * Contiene el número de autorización, fechas y estado.
 * 
 * @author Alejandro
 */
public class AutorizacionFAC {
    
    private int aut_id;
    private String aut_autorizacionSRI;
    private long aut_desde;
    private long aut_numFacturaActual; //Debe ir dentro del rando de desde-hasta
    private long aut_hasta;
    private String aut_estado;
    
    //Asignar rango de facturas a generar de acuerdo al punto de venta.
    public AutorizacionFAC() {
    }

    public AutorizacionFAC(String autorizacionSRI) {
        this.aut_autorizacionSRI = autorizacionSRI;
    }
}

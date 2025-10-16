/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SRI;

/**
 *
 * @author Yandris Rivera
 */
public class SRIWebServicesConfig {
    // --- AMBIENTE DE PRUEBAS (Certificación) ---
    public static final String RECEPCION_PRUEBAS = 
            "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    
    public static final String AUTORIZACION_PRUEBAS = 
            "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";

    // --- AMBIENTE DE PRODUCCIÓN ---
    public static final String RECEPCION_PRODUCCION = 
            "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
            
    public static final String AUTORIZACION_PRODUCCION = 
            "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
}

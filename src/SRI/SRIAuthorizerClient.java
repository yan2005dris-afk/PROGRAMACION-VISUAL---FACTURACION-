/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SRI;

/**
 *
 * @author Yandris Rivera
 */
public class SRIAuthorizerClient {
    private String ambiente;

    public SRIAuthorizerClient(String ambiente) {
        this.ambiente = ambiente;
        // Lógica de inicialización del cliente SOAP (Ej: service = new ... )
    }

    /**
     * Consulta el estado final de un comprobante usando su Clave de Acceso.
     * @param claveAcceso La Clave de Acceso (49 dígitos) a consultar.
     * @return El estado final (AUT, NAT, PPR).
     * @throws Exception Si falla la comunicación.
     */
    public String consultarEstado(String claveAcceso) throws Exception {
        
        // Determinar la URL del WSDL
        String urlWsdl = ambiente.equals("1") ? SRIWebServicesConfig.AUTORIZACION_PRUEBAS : 
                                               SRIWebServicesConfig.AUTORIZACION_PRODUCCION;
        
        // 1. Invocar el método del WS: autorizacionComprobante(String claveAcceso)
        // RespuestaAutorizacionComprobante respuesta = clienteSoap.autorizacionComprobante(claveAcceso);
        
        // --- SIMULACIÓN DE LA RESPUESTA ---
        // En un sistema real, se recibiría el estado final AUT, NAT, o PPR.
        
        String estadoAutorizacion = "PPR"; // Simular el estado de procesamiento
        
        // Lógica de simulación para fines de ejemplo:
        if (Math.random() > 0.8) {
            estadoAutorizacion = "AUT";
        } else if (Math.random() < 0.1) {
            estadoAutorizacion = "NAT";
        } else {
            estadoAutorizacion = "PPR";
        }
        
        // Los estados definidos son:
        // "PPR" (En procesamiento), "AUT" (Autorizado), "NAT" (No autorizado)[cite: 147].
        
        return estadoAutorizacion;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prueba;
import Model.InfoFactura;
import Model.InfoTributaria;
import Util.ClaveAccesoGenerator;
import Util.DateFormatter;
import Util.XMLBuilder;
import Util.XMLStreamer;
import Sign.XAdESSIgner;
import SRI.SRIReceiverClient;
import SRI.SRIAuthorizerClient;
import Modelos.Factura;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author Yandris Rivera
 */
public class FacturacionEcuatoriana {
    // Configuración general de la prueba
    private static final String AMBIENTE_PRUEBAS = "1";// 1=Pruebas [cite: 122]
    private static final String RUC_EMISOR = "1792146739001"; 
    private static final String P12_PATH = "C:\\path\\to\\my\\certificate.p12";
    private static final String P12_PASSWORD = "my_secure_password";
    private static final String CODIGO_NUMERICO = "12345678"; // 8 dígitos [cite: 89]

    public static void main(String[] args) {
        try {
            // ==========================================================
            // ETAPA 1: CREACIÓN Y GENERACIÓN DE LA CLAVE DE ACCESO
            // ==========================================================
            System.out.println("--- 1. Creando Modelo de Factura ---");
            
            // 1.1. Crear el objeto raíz
            Factura factura = new Factura(); // Asumiendo que Factura tiene un constructor o setters
            
            // 1.2. Llenar InfoTributaria (Datos del Emisor)
            InfoTributaria infoTributaria = new InfoTributaria();
            infoTributaria.setAmbiente(AMBIENTE_PRUEBAS); // 1 = Pruebas
            infoTributaria.setTipoEmision("1"); // 1 = Emisión normal [cite: 111]
            infoTributaria.setRuc(RUC_EMISOR);
            infoTributaria.setCodDoc("01"); // 01 = Factura [cite: 115]
            infoTributaria.setEstab("001"); 
            infoTributaria.setPtoEmi("001");
            infoTributaria.setSecuencial("000000001"); 
            infoTributaria.setRazonSocial("DISTRIBUIDORA PRUEBA S.A.");
            infoTributaria.setDirMatriz("AV. AMAZONAS");
            // ... (otros campos obligatorios)
            
            // 1.3. Llenar InfoFactura (Datos del Cliente y Totales)
            LocalDate hoy = LocalDate.now();
            InfoFactura infoFactura = new InfoFactura();
            // Formatear la fecha para el XML (dd/mm/aaaa)
            infoFactura.setFechaEmision(DateFormatter.formatToXMLDate(hoy)); 
            infoFactura.setTotalSinImpuestos((float) 100.00);
            // ... (otros campos obligatorios: identificacionComprador, totalConImpuestos, etc.)
            
            // 1.4. Generar la Clave de Acceso (y asignarla a InfoTributaria)
            String claveAcceso = ClaveAccesoGenerator.generClaveAcceso(
                infoTributaria, 
                infoFactura, 
                CODIGO_NUMERICO
            );
            System.out.println("Clave de Acceso Generada: " + claveAcceso);
            
            // 1.5. Ensamblar la Factura
           
            //Factura.setInfoTributaria(infoTributaria);
            //Factura.setInfoFactura(infoFactura);
            // ... factura.setDetalles(...)
            
            // ==========================================================
            // ETAPA 2: SERIALIZACIÓN Y FIRMA DIGITAL
            // ==========================================================
            System.out.println("--- 2. Serializando y Firmando XML ---");
            
            // 2.1. Serializar el Objeto a XML (String)
            String xmlSinFirma = XMLBuilder.buildFacturaXML(factura);
            
            // 2.2. Inicializar el Firmador con el certificado
            Sign.XAdESSIgner signer = new XAdESSIgner(P12_PATH, P12_PASSWORD);
            
            // 2.3. Aplicar la Firma Digital (Devuelve el XML con el bloque <ds:Signature>)
            String xmlFirmado = signer.firmarComprobante(xmlSinFirma, claveAcceso);
            
            // 2.4. Convertir a Array de Bytes (UTF-8) para el Web Service
            byte[] xmlBytes = XMLStreamer.toUTF8ByteArray(xmlFirmado);
            
            // ==========================================================
            // ETAPA 3: COMUNICACIÓN CON EL SRI (Flujo Asíncrono)
            // ==========================================================
            System.out.println("--- 3. Enviando a WS de Recepción ---");
            
            // 3.1. Enviar a WS de Recepción
            SRIReceiverClient receiver = new SRIReceiverClient(AMBIENTE_PRUEBAS);
            String estadoRecepcion = receiver.enviarComprobante(xmlBytes, claveAcceso);
            
            if ("RECIBIDA".equals(estadoRecepcion)) {
                
                System.out.println("Enviado con éxito. Esperando Autorización...");
                SRIAuthorizerClient authorizer = new SRIAuthorizerClient(AMBIENTE_PRUEBAS);
                String estadoFinal = "PPR"; // En Procesamiento [cite: 147]
                int intentos = 0;
                
                // 3.2. Bucle Asíncrono para Autorización
                while ("PPR".equals(estadoFinal) && intentos < 10) {
                    System.out.println("Intento " + (++intentos) + ": En procesamiento (PPR). Esperando 3 segundos...");
                    Thread.sleep(3000); // Esperar 3 segundos (configurable)
                    
                    estadoFinal = authorizer.consultarEstado(claveAcceso);
                }
                
                // 3.3. Resultado Final
                if ("AUT".equals(estadoFinal)) {
                    System.out.println("\n✅ ¡Factura AUTORIZADA! Clave: " + claveAcceso);
                } else if ("NAT".equals(estadoFinal)) {
                    System.err.println("\n❌ Factura NO AUTORIZADA (NAT). Debe corregir el XML y re-enviar.");
                } else {
                    System.err.println("\n⚠️ La factura sigue en procesamiento (PPR) o el tiempo de espera expiró.");
                }

            } else {
                 System.err.println("❌ Falló la recepción inicial: " + estadoRecepcion);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocurrió un error crítico en el proceso de facturación.");
        }
    }
}

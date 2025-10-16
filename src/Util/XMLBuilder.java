/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Modelos.Factura;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 *
 * @author Yandris Rivera
 */
public class XMLBuilder {
    /**
     * Convierte el objeto Factura a una cadena XML con codificación UTF-8.
     * * @param factura El objeto Factura completamente llenado.
     * @return La cadena XML lista para firmar.
     * @throws Exception Si ocurre un error durante la serialización JAXB.
     */
    public static String buildFacturaXML(Factura factura) throws Exception {
        
        // 1. Crear el contexto JAXB
        // Se inicializa con la clase raíz del modelo (Factura.class)
        JAXBContext jaxbContext = JAXBContext.newInstance(Factura.class);
        
        // 2. Crear el Marshaller (el objeto que hace la serialización)
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // Configuración de las propiedades del Marshaller:
        
        // 2.1. Formato de Salida
        // <?xml version="1.0" encoding="UTF-8" standalone="yes"?> (o similar)
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        
        // 2.2. Omitir la declaración de standalone (opcional, pero puede ayudar)
        // jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); 
        
        // 2.3. No formatear el output para que quede en una sola línea (SRI no exige indentación)
        // jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // 3. Serializar el objeto a un StringWriter
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(factura, sw);
        
        String xmlOutput = sw.toString();

        // 4. Post-procesamiento y Limpieza (CRUCIAL para el SRI)
        
        // 4.1. Reemplazar la declaración XML si JAXB la genera con standalone
        // y asegurar que la primera línea sea la estándar.
        if (xmlOutput.contains("standalone=\"yes\"")) {
             xmlOutput = xmlOutput.replace("standalone=\"yes\"", "");
        }
        
        // 4.2. Corrección de caracteres especiales (Ampersand & -> &amp;)
        // Nota: JAXB debería manejar esto correctamente, pero es una validación común.
        // Si hay errores, se debe hacer una limpieza más agresiva aquí,
        // asegurándose de no afectar entidades XML ya correctas como &lt; o &gt;
        // La Ficha Técnica indica: "& (ampersand) deberá incorporarse como “&amp;”"[cite: 1296, 1297].
        
        // 5. Devolver la cadena XML
        return xmlOutput;
    }
}

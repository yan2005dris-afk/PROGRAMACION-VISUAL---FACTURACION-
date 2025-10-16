/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author Yandris Rivera
 */
public class XMLStreamer {
    /**
     * Convierte la cadena XML a un array de bytes con codificación UTF-8, 
     * aplicando limpieza de caracteres.
     * * @param xmlString La cadena XML generada por XMLBuilder.
     * @return Array de bytes (byte[]) con codificación UTF-8.
     * @throws Exception Si falla la codificación o manejo de streams.
     */
    public static byte[] toUTF8ByteArray(String xmlString) throws Exception {
        
        // 1. Manejo de Caracteres Especiales (CRÍTICO PARA EL SRI)
        // El signo & (ampersand) debe codificarse como la entidad XML "&amp;"[cite: 1297].
        // Si no se hace, el SRI rechazará el comprobante por mal estructurado.
        // Se debe ser cuidadoso de no recodificar entidades XML ya válidas (como &lt;).
        
        // Asumiendo que JAXB maneja correctamente las entidades ya válidas, 
        // solo se realiza la sustitución si es estrictamente necesario o 
        // si se sabe que el input del usuario contiene un '&' literal.
        // Si bien JAXB debería hacerlo, se incluye un ejemplo para la sustitución explícita:
        String cleanedXml = xmlString.replace("&", "&amp;"); 

        // 2. Conversión a bytes con codificación UTF-8
        // La codificación exigida para el estándar de firma es UTF-8.
        return cleanedXml.getBytes(StandardCharsets.UTF_8); 
    }
}

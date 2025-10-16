/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package General;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Proporciona métodos utilitarios para encriptar contraseñas y otros textos 
 * sensibles usando el algoritmo SHA-256.
 * @author Alejandro
 */
public class Seguridad {
    
    /**
     * Encripta un texto usando el algoritmo SHA-256.
     * @param texto : Texto en claro que se desea encriptar.
     * @return Cadena hexadecimal que representa el hash SHA-256 del texto.
     * @throws RuntimeException : Si el algoritmo SHA-256 no está disponible.
     */
    public static String encriptarSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }
}

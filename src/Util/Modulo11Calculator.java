/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author Yandris Rivera
 */
public class Modulo11Calculator {
    /**
     * Calcula el dígito verificador aplicando el algoritmo Módulo 11.
     * * @param clave48 La cadena numérica de los primeros 48 dígitos de la Clave de Acceso.
     * @return El dígito verificador resultante (0 o 1).
     * @throws IllegalArgumentException Si la cadena de entrada no es numérica o tiene longitud incorrecta.
     */
    public static String calcularDigitoVerificador(String clave48) {
        
        if (clave48 == null || clave48.length() != 48 || !clave48.matches("\\d+")) {
            throw new IllegalArgumentException("La entrada debe ser una cadena de 48 dígitos numéricos.");
        }
        
        int total = 0;
        int factor = 2; // Inicia el factor de ponderación en 2

        // Iterar la cadena de atrás hacia adelante
        for (int i = clave48.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(clave48.charAt(i));
            total += digito * factor;

            // Aumentar el factor y reiniciar a 2 si llega a 8 (secuencia 2, 3, 4, 5, 6, 7)
            factor++;
            if (factor > 7) {
                factor = 2;
            }
        }

        int residuo = total % 11;
        int digitoVerificador = 11 - residuo;

        // Aplicar las reglas especiales del SRI (Sección 5.2 de la Ficha Técnica):
        // 1. Si el resultado es 11, el dígito verificador es 0.
        if (digitoVerificador == 11) {
            return "0";
        } 
        // 2. Si el resultado es 10, el dígito verificador es 1.
        else if (digitoVerificador == 10) {
            return "1";
        } 
        // 3. Cualquier otro resultado es el dígito verificador.
        else {
            return String.valueOf(digitoVerificador);
        }
    }
}

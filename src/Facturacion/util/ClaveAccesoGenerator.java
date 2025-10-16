/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facturacion.util;
import Facturacion.model.InfoTributaria;
import Facturacion.model.InfoFactura;
import Facturacion.util.Modulo11Calculator;
/**
 *
 * @author Yandris Rivera
 */
public class ClaveAccesoGenerator {
    /**
     * Genera la Clave de Acceso de 49 dígitos basándose en los datos tributarios.
     * @param info El objeto InfoTributaria con los datos del emisor.
     * @param codigoNumerico El código numérico aleatorio o secuencial de 8 dígitos del emisor.
     * @return La cadena de 49 dígitos de la Clave de Acceso.
     */
    public static String generClaveAcceso(
            InfoTributaria infoTributaria, 
            InfoFactura infoFactura,
            String codigoNumerico
    ){// --- 1. Obtener la Fecha de Emisión desde InfoFactura ---
        // Se asume que getFechaEmision() devuelve "dd/mm/aaaa" y se quitan los separadores.
        String fechaEmision = infoFactura.getFechaEmision().replaceAll("/", ""); 
        
        // --- 2. Obtener datos desde InfoTributaria ---
        String codDoc = infoTributaria.getCodDoc();
        String ruc = infoTributaria.getRuc();
        String ambiente = infoTributaria.getAmbiente();
        String serie = infoTributaria.getEstab() + infoTributaria.getPtoEmi();
        String secuencial = infoTributaria.getSecuencial();
        String tipoEmision = infoTributaria.getTipoEmision(); 

        // ----------------------------------------------------
        // CONCATENACIÓN DE LOS PRIMEROS 48 DÍGITOS
        // ----------------------------------------------------
        StringBuilder claveParcial = new StringBuilder();
        claveParcial.append(fechaEmision);      // 1-8
        claveParcial.append(codDoc);            // 9-10
        claveParcial.append(ruc);               // 11-23
        claveParcial.append(ambiente);          // 24
        claveParcial.append(serie);             // 25-30
        claveParcial.append(secuencial);        // 31-39
        claveParcial.append(codigoNumerico);    // 40-47
        claveParcial.append(tipoEmision);       // 48

        String clave48 = claveParcial.toString();

        // ----------------------------------------------------
        // CÁLCULO DEL DÍGITO VERIFICADOR
        // ----------------------------------------------------
        String digitoVerificador = Modulo11Calculator.calcularDigitoVerificador(clave48);

        // Clave final de 49 dígitos
        String claveAccesoFinal = clave48 + digitoVerificador;

        // Opcional pero recomendado: Asignar el valor generado a InfoTributaria
        // para que se serialice correctamente en el XML.
        infoTributaria.setClaveAcceso(claveAccesoFinal);
        
        return claveAccesoFinal;
    }


    
}
        
 
    
        
   
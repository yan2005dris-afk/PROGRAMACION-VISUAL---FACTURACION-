/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author Yandris Rivera
 */
public class DateFormatter {
    /**
     * Formato requerido por el SRI para la etiqueta <fechaEmision> (dd/mm/aaaa).
     * Se usa para la impresión en el RIDE y la validación en el XML.
     */
    public static final DateTimeFormatter XML_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Formato requerido para construir la Clave de Acceso (ddmmaaaa).
     * Nota: Este formato es solo una parte de la clave, no se incluyen separadores.
     */
    public static final DateTimeFormatter CLAVE_ACCESS_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy");

    /**
     * Formato para el Periodo Fiscal en Comprobantes de Retención (mm/aaaa).
     */
    public static final DateTimeFormatter PERIODO_FISCAL_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");

    /**
     * Convierte una fecha en el formato "dd/mm/aaaa" para la etiqueta del XML.
     * @param date La fecha a formatear.
     * @return La fecha como String en formato "dd/MM/yyyy".
     */
    public static String formatToXMLDate(java.time.LocalDate date) {
        if (date == null) return null;
        return date.format(XML_DATE_FORMAT);
    }

    /**
     * Convierte una fecha en el formato "ddmmaaaa" para la Clave de Acceso.
     * @param date La fecha a formatear.
     * @return La fecha como String en formato "ddMMyyyy".
     */
    public static String formatToClaveAccessDate(java.time.LocalDate date) {
        if (date == null) return null;
        return date.format(CLAVE_ACCESS_FORMAT);
    }

    /**
     * Convierte una fecha en el formato "mm/aaaa" para el Período Fiscal.
     * @param date La fecha a formatear.
     * @return La fecha como String en formato "MM/yyyy".
     */
    public static String formatToPeriodoFiscal(java.time.LocalDate date) {
        if (date == null) return null;
        return date.format(PERIODO_FISCAL_FORMAT);
    }

    /**
     * Método de conveniencia para parsear una fecha string a LocalDate usando el formato XML.
     * @param dateString La fecha como string ("dd/MM/yyyy").
     * @return El objeto LocalDate.
     */
    public static java.time.LocalDate parseXMLDate(String dateString) {
        if (dateString == null) return null;
        return java.time.LocalDate.parse(dateString, XML_DATE_FORMAT);
    }
    
    
}

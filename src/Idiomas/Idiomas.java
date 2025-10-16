/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Idiomas;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Centraliza la gestión de internacionalización (i18n) de la aplicación.
 * @author Alejandro
 */

public class Idiomas {
    
    // Idioma actual (por defecto español)
    private static Locale currentLocale = new Locale("es");

    // Nombre base del archivo de propiedades
    private static final String BASE_NAME = "Idiomas.mensajes";

    /**
     * Cambia el idioma actual de la aplicación.
     * @param idioma : Código o nombre del idioma ("es", "español", "spanish", 
     * "en", "ingles", "english").
     */
    public static void cambiarIdioma(String idioma) {
        switch (idioma.toLowerCase()) {
            case "es":
            case "español":
            case "spanish":
                currentLocale = new Locale("es");
                break;
            case "en":
            case "ingles":
            case "english":
                currentLocale = new Locale("en");
                break;
            default:
                // Si no coincide, dejamos español por defecto
                currentLocale = new Locale("es");
                break;
        }
    }

    /**
     * Obtiene el Locale actual de la aplicación.
     * @return Objeto Locale que representa el idioma actual.
     */
    public static Locale getLocale() {
        return currentLocale;
    }

    /**
     * Obtiene el ResourceBundle correspondiente al idioma actual.
     * @return ResourceBundle con los textos traducidos.
     */
    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(BASE_NAME, currentLocale);
    }

    /**
     * Obtiene el texto traducido para una clave específica.
     * @param clave : Clave definida en el archivo de propiedades.
     * @return Texto traducido correspondiente a la clave.
     */
    public static String getTexto(String clave) {
        return getBundle().getString(clave);
    }
}


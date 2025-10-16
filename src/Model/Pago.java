/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;


/**
 *
 * @author Yandris Rivera
 */

/**
 * Representa la etiqueta XML <pago> dentro del bloque <pagos> 
 * Contiene información sobre la forma de pago, el monto, el plazo y la unidad de tiempo.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Pago {

    @XmlElement(name = "formaPago", required = true)
    private String formaPago; 

    @XmlElement(name = "total", required = true)
    private float total; // El monto total pagado con esta forma. [cite_start]Máximo 14 dígitos. [cite: 447]

    @XmlElement(name = "plazo")
    private String plazo; 
    
    @XmlElement(name = "unidadTiempo")
    private String unidadTiempo; 

    
    // --- Constructor ---
    public Pago() {
    }

    // Constructor con parámetros comunes
    public Pago(String formaPago, float total, String plazo, String unidadTiempo) {
        this.formaPago = formaPago;
        this.total = total;
        this.plazo = plazo;
        this.unidadTiempo = unidadTiempo;
    }

    // --- Getters y Setters (Omitidos por brevedad, pero necesarios) ---
    
    // Ejemplo de Getter para JAXB:
    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }


}
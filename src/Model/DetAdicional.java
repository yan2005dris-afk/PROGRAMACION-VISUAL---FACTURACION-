/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Yandris Rivera
 */
 //conceptual de DetAdicional.java
@XmlAccessorType(XmlAccessType.FIELD)
public class DetAdicional {
    @XmlAttribute(name = "nombre")
    private String nombre; // e.g., "Email"
    
    @XmlAttribute(name = "valor")
    private String valor;  // e.g., "info@empresa.com"

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
   
    
    
}
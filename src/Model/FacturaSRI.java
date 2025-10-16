/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author Yandris Rivera
 */
@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)

public class FacturaSRI {
    @XmlAttribute(name = "id")
    private String id = "comprobante";
    
    @XmlAttribute(name = "version")
    private String version = "1.1.0";
    
    @XmlElement(name = "infoTributaria")
    private InfoTributaria infoFactura;
    
    @XmlElementWrapper(name = "detalles")
    @XmlElement(name="detalle")
    private List<Detalle> detalles;
    
}

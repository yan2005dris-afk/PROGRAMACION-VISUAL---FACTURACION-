/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Yandris Rivera
 */
public class TotalImpuesto {
    //codigo del impuesto
    private String codigo;
    //codigoPorcentaje 2 para IVA 12%, 0 para 0%
    private String codigoPorcentaje;
    //baseImponible - suma de las baseas imponibles de este tipo / porcentaje
    private float baseImponible;
    //suma total del valor del impuesto para eete tipo/porcentaje
    private float valor;
    // valor del iva devuelto, obligatorio para facturas de adultos mayores
    private float valorDevolucionIva;
    
}

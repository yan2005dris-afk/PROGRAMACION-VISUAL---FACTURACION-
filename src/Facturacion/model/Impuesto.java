/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facturacion.model;

/**
 *
 * @author Yandris Rivera
 */
public class Impuesto {
    //codigo del impuesto 2 (iva), 3(ICE), 5(IRBPNR)
    private String codigo; 
    //codigo de la tarifa 2 para iva 12%, 4 para iva 15%.
    private String codigoPorcentaje;
    //tarifa aplicable 12 o 15
    private float tarifa;
    //baseImponible - valor subtotal sujeto al impuesto.
    private float baseImponible;
    //valor - monto calculado del impuesto
    private float valor;
    
}

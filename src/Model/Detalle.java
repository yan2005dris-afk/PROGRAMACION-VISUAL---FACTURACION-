/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author Yandris Rivera
 */
public class Detalle {
    
    //Codigo del producto o servicio - longitud: 25
    private String codigoPrincipal;
    //codigo auxiliar opcional o requerido para materiales de construccion - longitud:25
    private String codigoAuxiliar;
    //descripcion o nombre del producto
    private String descripcion;
    //cantidad - productos
    private int cantidad;
    //precio unitario
    private float precioUnitario;
    //descuento - descuento aplicado
    private float descuento;
    //precioTotalSinImpuesto (cantidad * PrecioUnitario)
    private float precioTotalSinImpuesto;
    //detallesAdicionales - información adicional del detalle
    private List<DetAdicional> informacionAdicional;
    //impuestos lista de objetos, impuestos aplicables de un item
    private List<Impuesto> impuestos;
    
    
}

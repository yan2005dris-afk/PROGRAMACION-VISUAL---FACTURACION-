/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 * Representa una línea o ítem dentro de una factura. 
 * Cada instancia almacena la información de un producto facturado, incluyendo 
 * su cantidad, precio unitario, impuestos y total.
 * @author Alejandro
 */
public class DetalleFactura {
    
    private int det_id;
    private int prod_id;
    private String prod_cod;        // Código del producto
    private String prod_nombre;     // Nombre o descripción del producto
    private float det_cantidad;
    private float prod_pvp;        // Precio venta al publico ya sea menor o mayor
    private boolean prod_aplicaIVA;
    private float det_iva;          // Valor del iva
    private float det_total;        // Total de esta línea (cantidad * pvp)
    private float det_descuento;  // Descuento aplicado en esta línea (en valor)

    public DetalleFactura() {
    }

    public DetalleFactura(String prod_cod, String prod_nombre, float det_cantidad, 
            float prod_pvp, boolean prod_aplicaIVA) {
        this.prod_cod = prod_cod;
        this.prod_nombre = prod_nombre;
        this.det_cantidad = det_cantidad;
        this.prod_pvp = prod_pvp;
        this.prod_aplicaIVA = prod_aplicaIVA;
        recalcularTotales();
    }
    
    //GETTERS

    public int getDet_id() {
        return det_id;
    }

    public int getProd_id() {
        return prod_id;
    }

    public String getProd_cod() {
        return prod_cod;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public float getDet_cantidad() {
        return det_cantidad;
    }

    public float getProd_pvp() {
        return prod_pvp;
    }

    public float getDet_iva() {
        return det_iva;
    }

    public float getDet_total() {
        return det_total;
    }
    
    public boolean isProd_aplicaIVA() { 
        return prod_aplicaIVA; 
    }
    
    public float getDet_descuento() {
        return det_descuento;
    }
    
    //SETTERS

    public void setDet_id(int det_id) {
        this.det_id = det_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public void setProd_cod(String prod_cod) {
        this.prod_cod = prod_cod;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public void setDet_cantidad(float det_cantidad) {
        this.det_cantidad = det_cantidad;
        recalcularTotales();
    }

    public void setProd_pvp(float prod_pvp) {
        this.prod_pvp = prod_pvp;
        recalcularTotales();
    }

    public void setProd_aplicaIVA(boolean prod_aplicaIVA) {
        this.prod_aplicaIVA = prod_aplicaIVA;
        recalcularTotales();
    }

    public void setDet_iva(float det_iva) {
        this.det_iva = det_iva;
    }

    public void setDet_total(float det_total) {
        this.det_total = det_total;
    }
    
    /**
     * Recalcula el total y el IVA de la línea.
     * Aplica el 10% de descuento si la cantidad es mayor a 6.
     */
    public void recalcularTotales() {
        double precioUnitario = prod_pvp;
        det_descuento = 0;

        // Descuento por mayor (10%)
        if (det_cantidad > 6) {
            det_descuento = (float) (precioUnitario * 0.10 * det_cantidad);
            precioUnitario = precioUnitario * 0.90; // 10% descuento
        }

        // Total de la línea (cantidad * precio unitario con descuento)
        this.det_total = (float) (precioUnitario * det_cantidad);

        // IVA de la línea
        if (prod_aplicaIVA) {
            this.det_iva = (float) (this.det_total * 0.12); // IVA 12%
        } else {
            this.det_iva = 0;
        }
    }
    
    public void setDet_descuento(float det_descuento) {
        this.det_descuento = det_descuento;
    }
    
}

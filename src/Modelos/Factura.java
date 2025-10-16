/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.util.ArrayList;

/**
 * Representa el documento de facturación dentro del sistema.
 * Contiene identificación de la factura, usuario que la emitió, cliente 
 * asociado, detalle de productos/servicios facturados, totales y valores 
 * calculados (subtotal, IVA, descuento, total), estado de la factura.
 * @author Alejandro
 */
public class Factura {
    
    private int fac_id;
    private int usr_id_crea;
    private String fac_numero;
    private String fac_fecha;
    private Clientes fac_cliente;
    private ArrayList<DetalleFactura> fac_detalle;
    private float fac_subtotal;
    private float fac_subtotalcero;
    private float fac_iva;
    private float fac_descuento;
    private float fac_total;
    private String fe_clave; // Clave de la factura electrónica
    private String fe_fecha_aut;
    // Auditoria
    private String mac_crea;
    private String fecha_creacion;
    private String hora_creacion;
    private int usr_id_modifica;
    private String fecha_modificacion;
    private String hora_modificacion;
    private String mac_modifica;
    
    private String fa_estado; // A=activa - N=anulada

    public Factura() {
        this.fac_detalle = new ArrayList<>();
    }

    public Factura(String fac_numero, String fac_fecha, Clientes fac_cliente, ArrayList<DetalleFactura> fac_detalle, String fa_estado) {
        this.fac_numero = fac_numero;
        this.fac_fecha = fac_fecha;
        this.fac_cliente = fac_cliente;
        this.fac_detalle = fac_detalle;
        this.fa_estado = fa_estado;
        calcularTotales();
    }
    
    // Método para calcular totales a partir del detalle
    public void calcularTotales() {
        float subtotal = 0;
        float subtotal0 = 0;
        float iva = 0;
        float descuento = 0; // Si tienes lógica de descuento, aplícala aquí

        if (fac_detalle != null) {
            for (DetalleFactura d : fac_detalle) {
                if (d.isProd_aplicaIVA()) {
                    subtotal += d.getDet_total();
                    iva += d.getDet_iva();
                } else {
                    subtotal0 += d.getDet_total();
                }
            }
        }

        this.fac_subtotal = subtotal;
        this.fac_subtotalcero = subtotal0;
        this.fac_iva = iva;
        this.fac_descuento = descuento;
        this.fac_total = subtotal + subtotal0 + iva - descuento;
    }
    
    // GETTERS
    public int getFac_id() {
        return fac_id;
    }

    public int getUsr_id_crea() {
        return usr_id_crea;
    }

    public String getFac_numero() {
        return fac_numero;
    }

    public String getFac_fecha() {
        return fac_fecha;
    }

    public Clientes getFac_cliente() {
        return fac_cliente;
    }

    public ArrayList<DetalleFactura> getFac_detalle() {
        return fac_detalle;
    }

    public float getFac_subtotal() {
        return fac_subtotal;
    }

    public float getFac_subtotalcero() {
        return fac_subtotalcero;
    }

    public float getFac_iva() {
        return fac_iva;
    }

    public float getFac_descuento() {
        return fac_descuento;
    }

    public float getFac_total() {
        return fac_total;
    }

    public String getFa_estado() {
        return fa_estado;
    }
    
    //SETTERS

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public void setUsr_id_crea(int usr_id_crea) {
        this.usr_id_crea = usr_id_crea;
    }

    public void setFac_numero(String fac_numero) {
        this.fac_numero = fac_numero;
    }

    public void setFac_fecha(String fac_fecha) {
        this.fac_fecha = fac_fecha;
    }

    public void setFac_cliente(Clientes fac_cliente) {
        this.fac_cliente = fac_cliente;
    }

    public void setFac_detalle(ArrayList<DetalleFactura> fac_detalle) {
        this.fac_detalle = fac_detalle;
    }

    public void setFac_subtotal(float fac_subtotal) {
        this.fac_subtotal = fac_subtotal;
    }

    public void setFac_subtotalcero(float fac_subtotalcero) {
        this.fac_subtotalcero = fac_subtotalcero;
    }

    public void setFac_iva(float fac_iva) {
        this.fac_iva = fac_iva;
    }

    public void setFac_descuento(float fac_descuento) {
        this.fac_descuento = fac_descuento;
    }

    public void setFac_total(float fac_total) {
        this.fac_total = fac_total;
    }

    public void setFa_estado(String fa_estado) {
        this.fa_estado = fa_estado;
    }

    @Override
    public String toString() {
        return "Factura{" + "fac_numero=" + fac_numero + ", fac_cliente=" + fac_cliente + '}';
    }
    
}

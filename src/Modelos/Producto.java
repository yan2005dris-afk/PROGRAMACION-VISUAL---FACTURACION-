/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import javafx.collections.ObservableList;

/**
 * Representa un artículo o bien dentro del sistema. 
 * Almacena información clave como código, nombre, precios, stock, si aplica 
 * IVA y su estado.
 * @author Alejandro
 */
public class Producto {
    
    private int prod_id;
    private String prod_cod;
    private String prod_nombre;
    private float prod_precioCompra;
    private float prod_pvp_menor; // x menor
    private float prod_pvp_mayor; // x mayor
    private float prod_stock;
    private boolean prod_aplicaIVA;
    private String prod_imagen; // Imagen del producto (ojo con el tipo de dato)
    private String prod_estado;

    // Constructor vacío (necesario para frameworks y JavaFX)
    public Producto() {
    }

    // Constructor con parámetros

    public Producto(int prod_id, String prod_cod, String prod_nombre, float prod_precioCompra, float prod_pvp_menor, float prod_pvp_mayor, float prod_stock, boolean prod_aplicaIVA, String prod_imagen, String prod_estado) {
        this.prod_id = prod_id;
        this.prod_cod = prod_cod;
        this.prod_nombre = prod_nombre;
        this.prod_precioCompra = prod_precioCompra;
        this.prod_pvp_menor = prod_pvp_menor;
        this.prod_pvp_mayor = prod_pvp_mayor;
        this.prod_stock = prod_stock;
        this.prod_aplicaIVA = prod_aplicaIVA;
        this.prod_imagen = prod_imagen;
        this.prod_estado = prod_estado;
    }
    

    // GETTERS
    public int getProd_id() {
        return prod_id;
    }

    public String getProd_cod() {
        return prod_cod;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public float getProd_precioCompra() {
        return prod_precioCompra;
    }

    public float getProd_pvp_menor() {
        return prod_pvp_menor;
    }

    public float getProd_pvp_mayor() {
        return prod_pvp_mayor;
    }
    
    public float getProd_stock() {
        return prod_stock;
    }

    public boolean isProd_aplicaIVA() {
        return prod_aplicaIVA;
    }

    public String getProd_imagen() {
        return prod_imagen;
    }
    
    public String getProd_estado() {
        return prod_estado;
    }
    
    // Método para obtener el PVP_menor por defecto (menor)
    public float getPvp() {
        return prod_pvp_menor;
    }
    
    // Método para obtener el PVP_mayor por defecto (mayor)
    public float getPrecioPorCantidad(int cantidad) {
        float precioBase = prod_pvp_menor;
        if (cantidad > 6) {
            // Aplica 10% de descuento
            precioBase = precioBase * 0.90f;
        }
        return precioBase;
    }
    
    // Método opcional para calcular precio con IVA
    public float getPrecioConIVA(float porcentajeIVA) {
        if (prod_aplicaIVA) {
            return prod_pvp_menor * (1 + porcentajeIVA / 100);
        }
        return prod_pvp_menor;
    }
    
    // SETTERS
    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public void setProd_cod(String prod_cod) {
        this.prod_cod = prod_cod;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public void setProd_precioCompra(float prod_precioCompra) {
        this.prod_precioCompra = prod_precioCompra;
    }

    public void setProd_pvp_menor(float prod_pvp_menor) {
        this.prod_pvp_menor = prod_pvp_menor;
    }

    public void setProd_pvp_mayor(float prod_pvp_mayor) {
        this.prod_pvp_mayor = prod_pvp_mayor;
    }
    
    public void setProd_stock(float prod_stock) {
        this.prod_stock = prod_stock;
    }

    public void setProd_aplicaIVA(boolean prod_aplicaIVA) {
        this.prod_aplicaIVA = prod_aplicaIVA;
    }

    public void setProd_imagen(String prod_imagen) {
        this.prod_imagen = prod_imagen;
    }
    
    public void setProd_estado(String prod_estado) {
        this.prod_estado = prod_estado;
    }

    @Override
    public String toString() {
        return prod_nombre + " (" + prod_cod + ")";
    }
    
    public ObservableList<Producto> getListaProductos(String ConsultaSQL){
        General.BD accBD= new General.BD();
        ObservableList<Producto> listaAux=accBD.getListaConsulta(ConsultaSQL, 
            rs->{
                try {
                    return new Producto(
                            rs.getInt("prod_id"),
                            rs.getString("prod_cod"),
                            rs.getString("prod_nombre"),
                            
                            rs.getFloat("prod_precioCompra"),
                            rs.getFloat("prod_pvpxmenor"),
                            rs.getFloat("prod_pvpxmayor"),
                            rs.getFloat("prod_stock"),
                            rs.getBoolean("prod_aplicaIva"),
                            
                            rs.getString("pod_imagen"),
                            rs.getString("prod_estado")
                            
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                
            }
        );
        return listaAux;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 *
 * @author Yandris Rivera
 */
public class InfoFactura {
    //fecha de emision formato -- ddmmaaaa
    private String fechaEmision;
    //direccion del establecimiento emisor
    private String dirEstablecimiento;
    //Obligado a Contabilidad SI / NO 
    private String obligadoContabilidad;
    //Cod 04: ruc 05: cedula 07: consumidor final
    private String tipoIdentificacionComprador;
    //razon social del comprobador - nombres apellidos del comprador
    private String razonSocialComprador;
    //identificacion del comprador
    private String identificacionComprador;
    //suma de precios sin impuestos ni descuentos.
    private float totalSinImpuestos;
    //suma total de descuento
    private float totalDescuento;
    //Total de impuestos
    private float totalConImpuestos;
    //valor de propina
    private float propina;
    //importe total valor final a pagar, subtotal + impuestos + propina - valor devolucion iva
    private float importeTotal;
    //moneda dolar
    private String moneda;
    //pagos, lista de objetos <List>
    @XmlElementWrapper(name = "pagos", required = true) // El contenedor principal <pagos>
    @XmlElement(name = "pago") // Cada elemento de la lista será el tag <pago>
    private List<Pago> formasPago;
    //placa -- obligatorio en venta de combustibles
    private String placa;

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }

    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador;
    }

    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador;
    }

    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        this.identificacionComprador = identificacionComprador;
    }

    public float getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(float totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public float getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(float totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public float getTotalConImpuestos() {
        return totalConImpuestos;
    }

    public void setTotalConImpuestos(float totalConImpuestos) {
        this.totalConImpuestos = totalConImpuestos;
    }

    public float getPropina() {
        return propina;
    }

    public void setPropina(float propina) {
        this.propina = propina;
    }

    public float getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(float importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public List<Pago> getFormasPago() {
        return formasPago;
    }

    public void setFormasPago(List<Pago> formasPago) {
        this.formasPago = formasPago;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
 
    
    
}

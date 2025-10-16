/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Yandris Rivera
 */

public class InfoTributaria {
    //ambiente es 1 (pruebas) o 2(emision) 
    private String ambiente; 
    //codigo 1 emision normal
    private String tipoEmision;
    //razon social - nombres o identidad de la empresa, hacia el sri
    //ejemplo Distribuidora de suministros nacional S.A.
    private String razonSocial; 
    //nombre comercial de la empresa
    private String nombreComercial;
    //ruc - 13 digitos
    private String ruc;
    //claveAcceso
    private String claveAcceso;
    //codDoc
    private String codDoc;
    //codigo del establecimiento
    private String estab;
    //codigo del punto de emision;
    private String ptoEmi;
    //Número secuencial - debe tener ceros a la izquierda
    private String secuencial;
    //direccion Matriz
    private String dirMatriz;
    //Leyenda contribuyente regimen rimpe
    private String contribuyenteRimpe;
    //Número de resoluación 
    private String agenteRetencion;

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getCodDoc() {
        return codDoc;
    }

    public void setCodDoc(String codDoc) {
        this.codDoc = codDoc;
    }

    public String getEstab() {
        return estab;
    }

    public void setEstab(String estab) {
        this.estab = estab;
    }

    public String getPtoEmi() {
        return ptoEmi;
    }

    public void setPtoEmi(String ptoEmi) {
        this.ptoEmi = ptoEmi;
    }

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    public String getDirMatriz() {
        return dirMatriz;
    }

    public void setDirMatriz(String dirMatriz) {
        this.dirMatriz = dirMatriz;
    }

    public String getContribuyenteRimpe() {
        return contribuyenteRimpe;
    }

    public void setContribuyenteRimpe(String contribuyenteRimpe) {
        this.contribuyenteRimpe = contribuyenteRimpe;
    }

    public String getAgenteRetencion() {
        return agenteRetencion;
    }

    public void setAgenteRetencion(String agenteRetencion) {
        this.agenteRetencion = agenteRetencion;
    }
         
    
    
    
    
}

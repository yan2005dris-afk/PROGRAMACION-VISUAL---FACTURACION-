/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sign;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 *
 * @author Yandris Rivera
 */
public class CertificateLoader {
    private PrivateKey privateKey;
    private X509Certificate certificate;

    /**
     * Constructor que carga el archivo P12, extrae la clave y el certificado.
     * @param p12Path Ruta completa al archivo .p12 (ej. "/ruta/a/mi_firma.p12")
     * @param password La contraseña del archivo P12.
     * @throws Exception Si el archivo no se encuentra, la contraseña es incorrecta, 
     * o si no se puede inicializar el KeyStore.
     */
    public CertificateLoader(String p12Path, String password) throws Exception {
        
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] passwordChars = password.toCharArray();

        try (FileInputStream fis = new FileInputStream(p12Path)) {
            // Cargar el KeyStore con el archivo y la contraseña
            keyStore.load(fis, passwordChars);
        }

        // En PKCS12, generalmente solo hay una entrada (alias)
        Enumeration<String> aliases = keyStore.aliases();
        if (!aliases.hasMoreElements()) {
            throw new IllegalStateException("El archivo P12 no contiene entradas.");
        }

        String alias = aliases.nextElement();

        // 1. Obtener la clave privada
        if (!keyStore.isKeyEntry(alias)) {
             throw new IllegalStateException("El alias no apunta a una entrada de clave privada.");
        }
        this.privateKey = (PrivateKey) keyStore.getKey(alias, passwordChars);

        // 2. Obtener el certificado X.509
        this.certificate = (X509Certificate) keyStore.getCertificate(alias);

        if (this.privateKey == null || this.certificate == null) {
            throw new IllegalStateException("No se pudo extraer la clave o el certificado.");
        }
        
        // Opcional: Validación adicional del RUC en el certificado
        // Esta lógica es más compleja y se omite por simplicidad, pero es buena práctica 
        // verificar que el RUC del emisor esté en el campo SubjectDN del certificado.
    }

    /**
     * @return La clave privada (PrivateKey) necesaria para firmar.
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * @return El certificado X509 del emisor.
     */
    public X509Certificate getCertificate() {
        return certificate;
    }
}

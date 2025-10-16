/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import General.Seguridad;
import Idiomas.Idiomas;
import Modelos.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controlador para la ventana de Login.
 * Valida credenciales encriptadas y abre la ventana principal.
 * 
 * @author Alejandro
 */
public class FXMLLoginController implements Initializable {

    private Main mainApp;
    
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lbl_usuario;
    @FXML
    private Label lbl_contraseña;
    @FXML
    private Button btn_login;
    @FXML
    private AnchorPane fondoLogin;
    @FXML
    private Pane pane;
    @FXML
    private Label lbl_login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Estilos
        fondoLogin.getStyleClass().add("fondo");
        pane.getStyleClass().add("fondoPrincipal");
        txtUsuario.getStyleClass().add("text-field");
        txtPassword.getStyleClass().add("text-field");
        btn_login.getStyleClass().add("btnLogin");
        
        // Textos traducidos
        lbl_usuario.setText(Idiomas.getTexto("label.usuario"));
        lbl_contraseña.setText(Idiomas.getTexto("label.contrasenia"));
        btn_login.setText(Idiomas.getTexto("boton.login"));
        lbl_login.setText(Idiomas.getTexto("label.login"));

        // Usuario por defecto para pruebas (si la lista está vacía)
        /*if (BDUsuarios.listaUsuarios.isEmpty()) {
            String claveEncriptada = Seguridad.encriptarSHA256("1234");
            BDUsuarios.listaUsuarios.add(new Usuario(1,1,"Andy", "admin", claveEncriptada, "A"));
        }*/
    }
    
    /**
     * Establece la referencia a la clase principal de la aplicación.
     * @param main : Instancia de la clase Main que controla el flujo principal
     * de la aplicación.
     */
    public void setMainApp(Main main) {
        this.mainApp = main;
    }
    
    /**
     * Maneja el evento de clic en el botón de login.
     * Valida las credenciales ingresadas y, si son correctas, abre la ventana 
     * principal.
     * @param event : Evento de acción generado al hacer clic en el botón "Login".
     */
    @FXML
    private void acc_btnLogin(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        // Validar campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, ingrese usuario y contraseña.");
            return;
        }
        
        Usuario usuarioEncontrado = autenticar(usuario, password);
        
        if (usuarioEncontrado != null) {
            mainApp.abrirVentanaPrincipal();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de autenticación", "Usuario o contraseña incorrecta");
        }
    }
    
    /**
     * Autentica un usuario comparando el nombre de usuario y la contraseña encriptada.
     * @param usuario : Nombre de usuario ingresado
     * @param password : Contraseña en texto plano
     * @return Objeto Usuario : si las credenciales son correctas, o null si no coincide
     */
    private Usuario autenticar(String usuario, String password) {
        try {
            String claveEncriptada = Seguridad.encriptarSHA256(password);
        
            //Consulta SQL
            String CadenaSQL="";
            CadenaSQL=CadenaSQL+"SELECT U.usr_id, U.per_id, U.usr_nombres, U.usr_usuario, ";
            CadenaSQL=CadenaSQL+"U.usr_clave,U.usr_estado FROM Usuario U ";
            CadenaSQL=CadenaSQL+"WHERE U.usr_usuario = '"+usuario+"' ";
            CadenaSQL=CadenaSQL+"AND U.usr_clave = '"+claveEncriptada+"' ";
            CadenaSQL=CadenaSQL+"AND U.usr_estado = 'A'";

            //Ejecutar consulta
            Usuario objUsuario = new Usuario();
            ObservableList<Usuario> encontrado = objUsuario.getListaUsuarios(CadenaSQL);

            if(encontrado != null && !encontrado.isEmpty()){
                return encontrado.get(0); //Usuario válido
            }else{
                return null; //No se encontró
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al autenticar: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Muestra una alerta modal con el tipo, título y mensaje especificados.
     * @param tipo : Tipo de alerta (INFORMATION, WARNING, ERROR, etc.).
     * @param titulo : Texto que aparecerá como título de la ventana de alerta.
     * @param mensaje : Contenido principal del mensaje que se mostrará al 
     * usuario.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
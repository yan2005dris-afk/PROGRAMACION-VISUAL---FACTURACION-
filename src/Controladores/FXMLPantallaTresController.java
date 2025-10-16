/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import Modelos.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLPantallaTresController implements Initializable {

    @FXML
    private Pane pane_inferior;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_grabar;
    @FXML
    private Text text_encabezadoUsr;
    @FXML
    private Text text_nombres;
    @FXML
    private TextField txt_nombres;
    @FXML
    private Text text_usuario;
    @FXML
    private TextField txt_usuario;
    @FXML
    private Text text_estado;
    @FXML
    private TextField txt_clave;
    @FXML
    private Text text_perfil;
    @FXML
    private TextField txt_estado;
    @FXML
    private AnchorPane ap_pantallaTres;
    @FXML
    private Text text_contrasenia;
    @FXML
    private ComboBox<String> cbo_perfiles;

    int miId;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // Estilos        
            this.txt_nombres.requestFocus();
            ap_pantallaTres.getStyleClass().add("fondo");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_grabar.getStyleClass().add("btnGrabar");
            btn_cancelar.getStyleClass().add("btnCancelar");
            txt_nombres.getStyleClass().add("text-field");
            txt_usuario.getStyleClass().add("text-field");
            txt_clave.getStyleClass().add("text-field");
            txt_estado.getStyleClass().add("text-field");

            // EVENTOS
            General.Mod_general.fun_detectarTecla(txt_nombres, KeyCode.ENTER, this.txt_usuario);
            General.Mod_general.fun_detectarTecla(txt_usuario, KeyCode.ENTER, this.txt_clave);
            General.Mod_general.fun_detectarTecla(txt_clave, KeyCode.ENTER, this.txt_estado);
            General.Mod_general.fun_detectarTecla(txt_estado, KeyCode.ENTER, this.cbo_perfiles);
            General.Mod_general.fun_detectarTecla(cbo_perfiles, KeyCode.ENTER, this.btn_grabar);
            
            // TRADUCCIÓN
            text_encabezadoUsr.setText(Idiomas.getTexto("texto.encabezadoUsuario"));
            text_nombres.setText(Idiomas.getTexto("texto.nombresU"));
            text_usuario.setText(Idiomas.getTexto("label.usuario"));
            text_contrasenia.setText(Idiomas.getTexto("label.contrasenia"));
            text_estado.setText(Idiomas.getTexto("texto.estado"));
            text_perfil.setText(Idiomas.getTexto("texto.perfil"));
            btn_grabar.setText(Idiomas.getTexto("boton.grabar"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));

            // Cuando pierde el focus
            this.txt_nombres.focusedProperty().addListener((abs,oldVal,newVal) -> {
                if(!newVal){
                    System.out.println("Validar desde el focus");
                }
            });

            // Esperar a que la escena esté disponible antes de configurar el atajo
            // Esto evita NullPointerException si la escena no está cargada aún
            ap_pantallaTres.sceneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // addShortcut: Agregar un acceso directo o combinación de teclas
                    General.Mod_general.addShortcut(newValue, KeyCode.G, KeyCombination.CONTROL_DOWN, () -> 
                        acc_btngrabar(new ActionEvent()));
                }
            });
            
            cargarPerfiles();
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Pantalla Tres Controler" + e.getMessage());
        }
    }    
    
    /**
     * Cierra la ventana actual de la Pantalla Tres.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.cerrarFormulario();
    }
    
    /**
     * Valida los datos ingresados, encripta la contraseña y registra un nuevo 
     * usuario en la lista.
     * @param event : Evento de acción generado al hacer clic en el "Grabar" o 
     * al usar el atajo Ctrl+G
     */
    @FXML
    private void acc_btngrabar(ActionEvent event) {
        String cadenaSQL="";
        String perfilSeleccionado = cbo_perfiles.getValue(); // Ejemplo: "2 - Administrador"
        int per_id = Integer.parseInt(perfilSeleccionado.split(" - ")[0]);

        try {
            if(fun_validar()){
                if(this.miId==0){
                    //Insert
                    cadenaSQL=cadenaSQL+" INSERT INTO Usuario(per_id,usr_nombres,usr_usuario";
                    cadenaSQL=cadenaSQL+",usr_clave,usr_estado)";
                    cadenaSQL=cadenaSQL+" VALUES ";
                    cadenaSQL=cadenaSQL+"("+per_id;
                    cadenaSQL=cadenaSQL+",'"+this.txt_nombres.getText()+"'";
                    cadenaSQL=cadenaSQL+",'"+this.txt_usuario.getText()+"'";
                    cadenaSQL=cadenaSQL+",'"+General.Seguridad.encriptarSHA256(this.txt_clave.getText())+"'";
                    cadenaSQL=cadenaSQL+",'"+this.txt_estado.getText()+"')";
                }else{
                    //Update
                    cadenaSQL=cadenaSQL+" UPDATE Usuario ";
                    cadenaSQL=cadenaSQL+"SET per_id="+per_id+",";
                    cadenaSQL=cadenaSQL+"usr_nombres='"+this.txt_nombres.getText()+"',";
                    cadenaSQL=cadenaSQL+"usr_usuario='"+this.txt_usuario.getText()+"',";
                    cadenaSQL=cadenaSQL+"usr_clave='"+General.Seguridad.encriptarSHA256(this.txt_clave.getText())+"',";
                    cadenaSQL=cadenaSQL+"usr_estado='"+this.txt_estado.getText()+"'";
                    cadenaSQL=cadenaSQL+"WHERE usr_id="+this.miId;
                }
                General.BD accBD=new General.BD();
                if(accBD.fun_ejecutar(cadenaSQL)){
                    General.Mod_general.fun_mensajeInformacion("Se registro con éxito.");
                    cerrarFormulario();
                }else{
                    General.Mod_general.fun_mensajeError("Error al grabar el cliente.");
                }
            }else{
                System.out.println("Datos sin validar");
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Verifica que el campo de nombres no esté vacío.
     * @return true si la validación es correcta, false en caso contrario.
     */
    private boolean fun_validar(){
        try {
            // trim para comprimir los espacios y el isEmpty es para borrar espacios vacios
            if(this.txt_nombres.getText().trim().isEmpty()){
                General.Mod_general.fun_mensajeInformacion("***Registre el Nombre***");
                this.txt_nombres.requestFocus();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Limpia todos los campos del formulario y coloca el foco en el campo de nombres.
     */
    public void fun_limpiar(){
        this.txt_nombres.clear();
        this.txt_usuario.clear();
        this.txt_clave.clear();
        this.txt_estado.clear();
        this.txt_nombres.requestFocus();
    }
    
    /**
     * Carga los datos de un usuario existente en los campos del formulario.
     * @param u : Objeto Usuario cuyos datos se mostrarán en la pantalla.
     */
    public void fun_recuperarUsuarioID(int id) {
        try {
            miId=id;
            if(miId>0){
                //recuperar la información
                String cadenaSQL="SELECT * FROM Usuario WHERE usr_id="+id;
                Usuario objUsuario=new Usuario();
                ObservableList<Usuario> usuarios = objUsuario.getListaUsuarios(cadenaSQL);
                if(usuarios.size()>0){
                    this.txt_nombres.setText(usuarios.get(0).getUsr_nombres());
                    this.txt_usuario.setText(usuarios.get(0).getUsr_usuario());
                    this.txt_clave.setText(usuarios.get(0).getUsr_clave());
                    this.txt_estado.setText(usuarios.get(0).getUsr_estado());
                    
                    int per_id = usuarios.get(0).getPer_id();
                    for (String item : cbo_perfiles.getItems()) {
                        if (item.startsWith(String.valueOf(per_id) + " -")) {
                            cbo_perfiles.setValue(item);
                            break;
                        }
                    }
                    
                }
            }else{
                this.fun_limpiar();
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    public void cerrarFormulario(){
        try {
            Stage stage=(Stage) this.btn_cancelar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    private void cargarPerfiles() {
        try {
            String sql = "SELECT per_id, per_descripcion FROM Perfil WHERE per_estado = 'A'";
            General.BD accBD = new General.BD();

            if (accBD.conectarBD()) {
                java.sql.ResultSet rs = accBD.getConexion().createStatement().executeQuery(sql);
                javafx.collections.ObservableList<String> perfiles = javafx.collections.FXCollections.observableArrayList();

                while (rs.next()) {
                    perfiles.add(rs.getInt("per_id") + " - " + rs.getString("per_descripcion"));
                }

                cbo_perfiles.setItems(perfiles);
                rs.close();
                accBD.desconectarBD();
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Error al cargar perfiles: " + e.getMessage());
        }
    }
}

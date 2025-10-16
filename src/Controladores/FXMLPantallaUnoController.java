/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import Modelos.Clientes;
import Modelos.ReporteClientes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLPantallaUnoController implements Initializable {

    @FXML
    private Button btn_cancelar;
    @FXML
    private AnchorPane ap_pantallaUno;
    @FXML
    private Button btn_grabar;
    @FXML
    private TextField txt_cedula;
    @FXML
    private TextField txt_apellidos;
    @FXML
    private TextField txt_nombres;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextArea txta_direccion;
    @FXML
    private TextField txt_correo;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Text text_encabezado;
    @FXML
    private Text text_cedula;
    @FXML
    private Text text_apellido;
    @FXML
    private Text text_nombre;
    @FXML
    private Text text_direccion;
    @FXML
    private Text text_telefono;
    @FXML
    private Text text_correo;
    
    int miId;
    @FXML
    private CheckBox chk_estado;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // Estilos
            ap_pantallaUno.getStyleClass().add("fondo");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_grabar.getStyleClass().add("btnGrabar");
            btn_cancelar.getStyleClass().add("btnCancelar");
            txt_cedula.getStyleClass().add("text-field");
            txt_apellidos.getStyleClass().add("text-field");
            txt_nombres.getStyleClass().add("text-field");
            txta_direccion.getStyleClass().add("text-area");
            txt_telefono.getStyleClass().add("text-field");
            txt_correo.getStyleClass().add("text-field");

            // Traducción
            text_encabezado.setText(Idiomas.getTexto("texto.encabezadoCliente"));
            text_cedula.setText(Idiomas.getTexto("texto.cedula"));
            text_apellido.setText(Idiomas.getTexto("texto.apellido"));
            text_nombre.setText(Idiomas.getTexto("texto.nombre"));
            text_direccion.setText(Idiomas.getTexto("texto.direccion"));
            text_telefono.setText(Idiomas.getTexto("texto.telefono"));
            text_correo.setText(Idiomas.getTexto("texto.correo"));
            btn_grabar.setText(Idiomas.getTexto("boton.grabar"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));

            // EVENTOS        
            this.txt_cedula.requestFocus();
            General.Mod_general.fun_detectarTecla(txt_cedula, KeyCode.ENTER, this.txt_apellidos);
            General.Mod_general.fun_detectarTecla(txt_apellidos, KeyCode.ENTER, this.txt_nombres);
            General.Mod_general.fun_detectarTecla(txt_nombres, KeyCode.ENTER, this.txta_direccion);
            General.Mod_general.fun_detectarTecla(txta_direccion, KeyCode.ENTER, this.txt_telefono);
            General.Mod_general.fun_detectarTecla(txt_telefono, KeyCode.ENTER, this.txt_correo);
            General.Mod_general.fun_detectarTecla(txt_correo, KeyCode.ENTER, this.btn_grabar);

            // Cuando pierde el focus
            this.txt_cedula.focusedProperty().addListener((abs,oldVal,newVal) -> {
                if(!newVal){
                    System.out.println("Validar desde el focus");
                }
            });

            // Esperar a que la escena esté disponible antes de configurar el atajo
            // Esto evita NullPointerException si la escena no está cargada aún
            ap_pantallaUno.sceneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // addShortcut: Agregar un acceso directo o combinación de teclas
                    General.Mod_general.addShortcut(newValue, KeyCode.G, KeyCombination.CONTROL_DOWN, () -> 
                        acc_btngrabar(new ActionEvent()));
                }
            });
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Pantalla Uno Controller" + e.getMessage());
        }
    }
    
    /**
     * Cierra la ventana actual de la Pantalla Uno.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.cerrarFormulario();
    }
    
    /**
     * Valida los datos ingresados y registra un nuevo cliente en la lista de 
     * clientes y en la lista de reportes.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Grabar" o al usar el atajo Ctrl+G
     */
    @FXML
    private void acc_btngrabar(ActionEvent event) {
        String cadenaSQL="";
        try {
            if(fun_validar()){
                String estado;
                if(this.chk_estado.isSelected()){
                    estado="A";
                }else{
                    estado="E";
                }
                if(this.miId==0){
                    //Insert
                    cadenaSQL = cadenaSQL+" INSERT INTO Cliente(cli_cedula,cli_nombres,cli_apellidos"
                            + ",cli_direccion,cli_telefono,cli_correo,cli_estado)"
                            + " VALUES "
                            + "('" + this.txt_cedula.getText()+"'" + ",'" + this.txt_nombres.getText() + "'"
                            + ",'" + this.txt_apellidos.getText() + "'" + ",'" + this.txta_direccion.getText() + "'"
                            + ",'" + this.txt_telefono.getText() + "'" + ",'" + this.txt_correo.getText() + "'"
                            + ",'" + estado + "')";
                }else{
                    //Update
                    cadenaSQL=cadenaSQL+" UPDATE Cliente "
                            + "SET cli_cedula='" + this.txt_cedula.getText() + "',"
                            + "cli_nombres='" + this.txt_nombres.getText() + "',"
                            + "cli_apellidos='" + this.txt_apellidos.getText() + "',"
                            + "cli_direccion='" + this.txta_direccion.getText() + "',"
                            + "cli_telefono='" + this.txt_telefono.getText() + "',"
                            + "cli_correo='" + this.txt_correo.getText() + "',"
                            + "cli_estado='" + estado + "'"
                            + "WHERE cli_id = " + this.miId;
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
     * Verifica que el campo de cédula no esté vacío.
     * @return true si la validación es correcto, false en caso contrario.
     */
    private boolean fun_validar(){
        try {
            // trim para comprimir los espacios y el isEmpty es para borrar espacios vacios
            if(this.txt_cedula.getText().trim().isEmpty()){
                General.Mod_general.fun_mensajeInformacion("***Registre la Cédula***");
                this.txt_cedula.requestFocus();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Limpia todos los campos del formulario y coloca el foco en el campo de 
     * cédula.
     */
    public void fun_limpiar(){
        this.txt_cedula.clear();
        this.txt_apellidos.clear();
        this.txt_nombres.clear();
        this.txta_direccion.clear();
        this.txt_telefono.clear();
        this.txt_correo.clear();
        this.txt_cedula.requestFocus();
    }
    
    /**
     * Carga los datos de un cliente existente en los campos del formulario.
     * @param id : Cédula del cliente cuyos datos se mostrarán en la pantalla.
     */
    public void fun_recuperarClientexID(String id) {
        try {
            miId = Integer.parseInt(id);
            if(miId > 0){
                //recuperar la información
                String cadenaSQL = "SELECT * FROM Cliente WHERE cli_id = " + miId;
                Clientes objCliente = new Clientes();
                ObservableList<Clientes> clientes = objCliente.getListaClientes(cadenaSQL);
                if(clientes.size() > 0){
                    this.txt_cedula.setText(clientes.get(0).getCli_cedula());
                    this.txt_apellidos.setText(clientes.get(0).getCli_apellidos());
                    this.txt_nombres.setText(clientes.get(0).getCli_nombres());
                    this.txta_direccion.setText(clientes.get(0).getCli_direccion());
                    this.txt_telefono.setText(clientes.get(0).getCli_telefono());
                    this.txt_correo.setText(clientes.get(0).getCli_correo());
                    if(clientes.get(0).getCli_estado().equals("A")){
                        this.chk_estado.setSelected(true);
                    }else{
                        this.chk_estado.setSelected(false);
                    }
                }
            }else{
                //limpiar campos
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
}

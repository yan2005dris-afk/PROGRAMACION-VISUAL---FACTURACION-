/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import Modelos.Empresa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
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
public class FXMLPantallaDosController implements Initializable {

    @FXML
    private Button btn_cancelar;
    @FXML
    private AnchorPane ap_pantallaDos;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Button btn_grabar;
    @FXML
    private Text text_encabezadoEmp;
    @FXML
    private Text text_ruc;
    @FXML
    private TextField txt_ruc;
    @FXML
    private Text text_nombre;
    @FXML
    private TextField txt_nombre;
    @FXML
    private Text text_direccion;
    @FXML
    private Text text_telefono;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextArea txta_direccion;
    @FXML
    private Text text_sucursal;
    @FXML
    private TextField txt_sucursal;
    
    String miId;
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
            this.txt_ruc.requestFocus();
            ap_pantallaDos.getStyleClass().add("fondo");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_grabar.getStyleClass().add("btnGrabar");
            btn_cancelar.getStyleClass().add("btnCancelar");
            txt_ruc.getStyleClass().add("text-field");
            txt_nombre.getStyleClass().add("text-field");
            txta_direccion.getStyleClass().add("text-area");
            txt_telefono.getStyleClass().add("text-field");
            txt_sucursal.getStyleClass().add("text-field");

            // EVENTOS
            General.Mod_general.fun_detectarTecla(txt_ruc, KeyCode.ENTER, this.txt_nombre);
            General.Mod_general.fun_detectarTecla(txt_nombre, KeyCode.ENTER, this.txta_direccion);
            General.Mod_general.fun_detectarTecla(txta_direccion, KeyCode.ENTER, this.txt_telefono);
            General.Mod_general.fun_detectarTecla(txt_telefono, KeyCode.ENTER, this.txt_sucursal);
            General.Mod_general.fun_detectarTecla(txt_sucursal, KeyCode.ENTER, this.btn_grabar);
            
            // TRADUCCIÓN
            text_encabezadoEmp.setText(Idiomas.getTexto("texto.encabezadoEmpresa"));
            text_ruc.setText(Idiomas.getTexto("texto.ruc"));
            text_nombre.setText(Idiomas.getTexto("texto.nombre"));
            text_direccion.setText(Idiomas.getTexto("texto.direccion"));
            text_telefono.setText(Idiomas.getTexto("texto.telefono"));
            text_sucursal.setText(Idiomas.getTexto("texto.sucursal"));
            btn_grabar.setText(Idiomas.getTexto("boton.grabar"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));

            // Cuando pierde el focus
            this.txt_ruc.focusedProperty().addListener((abs,oldVal,newVal) -> {
                if(!newVal){
                    System.out.println("Validar desde el focus");
                }
            });

            // Esperar a que la escena esté disponible antes de configurar el atajo
            // Esto evita NullPointerException si la escena no está cargada aún
            ap_pantallaDos.sceneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // addShortcut: Agregar un acceso directo o combinación de teclas
                    General.Mod_general.addShortcut(newValue, KeyCode.G, KeyCombination.CONTROL_DOWN, () -> 
                        acc_btngrabar(new ActionEvent()));
                }
            });
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Pantalla Dos Controler" + e.getMessage());
        }
    }    
    
    /**
     * Cierra la ventana actual de la Pantalla Dos.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        try {
            // this.ap_pantallaDos.setVisible(false);
            Stage stage = (Stage) this.btn_grabar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            // System.out.println(""+e.getMessage());
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Valida los datos ingresados y registra una nueva empresa en la lista.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Grabar" o al usar el atajo Ctrl+G
     */
    @FXML
    private void acc_btngrabar(ActionEvent event) {
        String cadenaSQL="";
        try {
            if(fun_validar()){
                // Validar si la empresa ya existe
                Empresa objEmpresa = new Empresa();
                String consulta = "SELECT * FROM Empresa WHERE emp_ruc='" + this.txt_ruc.getText() + "'";
                ObservableList<Empresa> listaEmpresas = objEmpresa.getListaEmpresas(consulta);
                String estado="I";
                if(chk_estado.isSelected()){
                    estado="A";
                    inactivarEmpresas("I");
                }
                
                if (listaEmpresas.size() == 0) {
                    // INSERTAR
                    cadenaSQL = cadenaSQL + "INSERT INTO Empresa(emp_ruc, emp_nombre, emp_direccion, emp_telefono, emp_estado) ";
                    cadenaSQL = cadenaSQL + "VALUES ('" + this.txt_ruc.getText() + "',";
                    cadenaSQL = cadenaSQL + "'" + this.txt_nombre.getText() + "',";
                    cadenaSQL = cadenaSQL + "'" + this.txta_direccion.getText()+ " - " +this.txt_sucursal+ "',";
                    cadenaSQL = cadenaSQL + "'" + this.txt_telefono.getText() + "',";
                    cadenaSQL = cadenaSQL + "'" + estado + "')";
                } else {
                    // ACTUALIZAR
                    cadenaSQL = cadenaSQL + "UPDATE Empresa SET ";
                    cadenaSQL = cadenaSQL + "emp_nombre='" + this.txt_nombre.getText() + "', ";
                    cadenaSQL = cadenaSQL + "emp_direccion='" + this.txta_direccion.getText()+ " - " +this.txt_sucursal + "', ";
                    cadenaSQL = cadenaSQL + "emp_telefono='" + this.txt_telefono.getText() + "', ";
                    cadenaSQL = cadenaSQL + "emp_estado='" + estado + "' ";
                    cadenaSQL = cadenaSQL + "WHERE emp_ruc='" + this.txt_ruc.getText() + "'";
                }
                
                 // Ejecutar SQL
                General.BD accBD = new General.BD();
                if (accBD.fun_ejecutar(cadenaSQL)) {
                    General.Mod_general.fun_mensajeInformacion("Empresa registrada correctamente.");
                    
                    this.fun_limpiar();
                } else {
                    General.Mod_general.fun_mensajeError("Error al grabar la empresa.");
                }
                
            }else{
                System.out.println("Datos sin validar");
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Verifica que el campo RUC no esté vacío.
     * @return true si la validación es correcta, false en caso contrario.
     */
    private boolean fun_validar(){
        try {
            // trim para comprimir los espacios y el isEmpty es para borrar espacios vacios
            if(this.txt_ruc.getText().trim().isEmpty()){
                General.Mod_general.fun_mensajeInformacion("***Registre la RUC***");
                this.txt_ruc.requestFocus();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Limpia todos los campos del formulario y coloca el foco en el campo RUC.
     */
    public void fun_limpiar(){
        this.txt_ruc.clear();
        this.txt_nombre.clear();
        this.txta_direccion.clear();
        this.txt_telefono.clear();
        this.txt_sucursal.clear();
        this.txt_ruc.requestFocus();
    }
    
    private void inactivarEmpresas(String inactivo) {
       try {
            String sql = "UPDATE Empresa SET emp_estado='" + inactivo + "'";
            General.BD accBD = new General.BD();
            accBD.fun_ejecutar(sql);
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Error al inactivar empresas: " + e.getMessage());
        } 
    }
    
    /**
     * Carga los datos de una empresa existente en los campos del formulario.
     * @param id : Objeto Empresa cuyos datos se mostrarán en la pantalla.
     */
    public void fun_recuperarEmpresaID(String RUC) {
        try {
            miId=RUC;
            if(miId!=""){
                //recuperar la información
                String cadenaSQL="SELECT * FROM Empresa WHERE emp_ruc='"+miId+"'";
                Empresa objEmpresa=new Empresa();
                ObservableList<Empresa> empresas = objEmpresa.getListaEmpresas(cadenaSQL);
                if(empresas.size()>0){
                    this.txt_ruc.setText(empresas.get(0).getEmp_ruc());
                    this.txt_nombre.setText(empresas.get(0).getEmp_nombre());
                    //this.txtA_direccion.setText(empresas.get(0).getEmp_direccion());
                    this.txt_telefono.setText(empresas.get(0).getEmp_telefono());
                    if(empresas.get(0).getEmp_estado().equals("A")){
                        this.chk_estado.setSelected(true);
                    }else{
                        this.chk_estado.setSelected(false);
                    }
                    
                    // 🔹 Separar dirección y sucursal
                    String direccionCompleta = empresas.get(0).getEmp_direccion();
                    if (direccionCompleta != null && direccionCompleta.contains(" - ")) {
                        String[] partes = direccionCompleta.split(" - ", 2);
                        this.txta_direccion.setText(partes[0]);  // dirección principal
                        this.txt_sucursal.setText(partes[1]); // sucursal
                    } else {
                        // Si no hay separador, asumir todo como dirección principal
                        this.txta_direccion.setText(direccionCompleta);
                        this.txt_sucursal.clear();
                    }
                }
            }else{
                this.fun_limpiar();
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
}

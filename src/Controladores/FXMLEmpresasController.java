/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import Modelos.Empresa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLEmpresasController implements Initializable {

    @FXML
    private AnchorPane ap_empresas;
    @FXML
    private Pane pane_superior;
    @FXML
    private Label lb_buscarEmpresa;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_nuevo;
    @FXML
    private TableColumn<?, ?> col_ruc;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_direccion;
    @FXML
    private TableColumn<?, ?> col_telefono;
    @FXML
    private TableView<Empresa> tbl_empresa;
    @FXML
    private TableColumn<?, ?> col_estado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // ESTILOS
            ap_empresas.getStyleClass().add("fondo");
            pane_superior.getStyleClass().add("anchorPaneSuperior");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_nuevo.getStyleClass().add("btnNuevo");
            btn_cancelar.getStyleClass().add("btnCancelar");
            
            tbl_empresa.getStyleClass().add("table-view");
            
            this.col_ruc.setCellValueFactory(new PropertyValueFactory<>("emp_ruc"));
            this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("emp_nombre"));
            this.col_direccion.setCellValueFactory(new PropertyValueFactory<>("emp_direccion"));
            this.col_telefono.setCellValueFactory(new PropertyValueFactory<>("emp_telefono"));
            this.col_estado.setCellValueFactory(new PropertyValueFactory<>("emp_estado"));
            
            //this.tbl_empresa.setItems(General.BDEmpresas.listaEmpresas);
            cargarEmpresas();
            
            // TRADUCCIÓN
            lb_buscarEmpresa.setText(Idiomas.getTexto("label.buscarEmpresa"));
            col_ruc.setText(Idiomas.getTexto("columna.ruc"));
            col_nombre.setText(Idiomas.getTexto("columna.nombre"));
            col_direccion.setText(Idiomas.getTexto("columna.direccion"));
            col_telefono.setText(Idiomas.getTexto("columna.telefono"));
            tbl_empresa.setPlaceholder(new Label(Idiomas.getTexto("tabla.registros")));
            btn_nuevo.setText(Idiomas.getTexto("boton.nuevo"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));
            
        } catch (Exception e) {
            System.out.println("Error en initialize Empresas" + e.getMessage());
        }
    }
    
    /**
     * Oculta el panel principal de empresas, simulando el cierre de la vista.
     * @param event 
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.ap_empresas.setVisible(false);
    }
    
    /**
     * Carga  el formulario en una ventana modal para crear una nueva empresa.
     * Aplica el idioma actual y estilos .CSS
     * @param event 
     */
    @FXML
    private void acc_btnnuevo(ActionEvent event) {
        try {
            String formulario = "/Vistas/FXMLPantallaDos.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());
            
            Parent root = loader.load();
            
            // Asociar con la pantallaDos
            FXMLPantallaDosController controlador = loader.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error al abrir PantallaDos: " + e.getMessage());
        }
    }
    
    /**
     * Detecta doble clic izquierdo sobre una fila de la tabla.
     * Si hay una empresa seleccionada, se abre el formulario de edición con sus
     * datos.
     * @param event 
     */
    @FXML
    private void acc_clickedMouse(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Empresa objSeleccionado = this.tbl_empresa.getSelectionModel().getSelectedItem();
                if (objSeleccionado != null) {
                    // System.out.println(objSeleccionado.getCedula());
                    fun_abrirModal(objSeleccionado.getEmp_ruc());
                }
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Carga el formulario y llama al método del controlador asociado para 
     * mostrar los datos de la empresa seleccionada
     * @param id 
     */
    public void fun_abrirModal(String RUC) {
        try {
            String formulario = "/Vistas/FXMLPantallaDos.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());

            Parent root = loader.load();
            
            // Asociar con la pantalla Personas
            FXMLPantallaDosController controlador = loader.getController();
            controlador.fun_recuperarEmpresaID(RUC);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            Stage newStage = new Stage();
            // Agregar icono al formulario
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error al abrir modal EmpCtr: " + e.getMessage());
        }
    }
    
    public void cargarEmpresas(){
        try {
            this.tbl_empresa.getItems().clear();
            String cadenaSQL="";
            cadenaSQL=cadenaSQL+"SELECT E.emp_ruc, E.emp_nombre, ";
            cadenaSQL=cadenaSQL+"E.emp_direccion,E.emp_telefono, ";
            cadenaSQL=cadenaSQL+"E.emp_estado FROM Empresa E";
            Empresa objEmpresa=new Empresa();
            this.tbl_empresa.setItems(objEmpresa.getListaEmpresas(cadenaSQL));
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
        
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import Modelos.Clientes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;  // Importación agregada
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
public class FXMLClientesController implements Initializable {

    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_nuevo;
    @FXML
    private TableView<Clientes> tbl_cliente;
    @FXML
    private TableColumn<?, ?> col_cedula;
    @FXML
    private TableColumn<?, ?> col_nombres;
    @FXML
    private TableColumn<?, ?> col_telefono;
    @FXML
    private TableColumn<?, ?> col_apellidos;
    @FXML
    private AnchorPane ap_clientes;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Pane pane_superior;
    @FXML
    private Label lb_buscarClientes;
    @FXML
    private TextField txt_buscar;
    @FXML
    private RadioButton rb_apellido;
    @FXML
    private RadioButton rb_cedula;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // 🔹 Asignar clases CSS desde Java
            ap_clientes.getStyleClass().add("fondo");
            pane_superior.getStyleClass().add("anchorPaneSuperior");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_nuevo.getStyleClass().add("btnNuevo");
            btn_cancelar.getStyleClass().add("btnCancelar");
            
            tbl_cliente.getStyleClass().add("table-view");
            
            // Traducción
            lb_buscarClientes.setText(Idiomas.getTexto("label.buscarCliente"));
            col_cedula.setText(Idiomas.getTexto("columna.cedula"));
            col_nombres.setText(Idiomas.getTexto("columna.nombreApellido"));
            col_telefono.setText(Idiomas.getTexto("columna.telefono"));
            tbl_cliente.setPlaceholder(new Label(Idiomas.getTexto("tabla.registros")));
            btn_nuevo.setText(Idiomas.getTexto("boton.nuevo"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));
            
            this.col_cedula.setCellValueFactory(new PropertyValueFactory<>("cli_cedula"));
            this.col_apellidos.setCellValueFactory(new PropertyValueFactory<>("cli_apellidos"));
            this.col_nombres.setCellValueFactory(new PropertyValueFactory<>("cli_nombres"));
            this.col_telefono.setCellValueFactory(new PropertyValueFactory<>("cli_telefono"));
            
            this.cargarClientes();
            
            // Agregar ToggleGroup para los RadioButtons
            ToggleGroup toggleGroup = new ToggleGroup();  // Crear el grupo
            rb_apellido.setToggleGroup(toggleGroup);     // Asignar rb_apellido al grupo
            rb_cedula.setToggleGroup(toggleGroup);       // Asignar rb_cedula al grupo
            rb_apellido.setSelected(true);               // Seleccionar rb_apellido por defecto
        } catch (Exception e) {
            System.out.println("Error en initialize Clientes" + e.getMessage());
        }
    }    
    
    /**
     * Oculta el panel de clientes al hacer clic en el botón "Cancelar"
     * @param event 
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.ap_clientes.setVisible(false);
    }
    
    /**
     * Carga el formulario en una nueva ventana modal para crear un nuevo cliente.
     * Usa el idioma actual y aplica estilos .CSS
     * @param event 
     */
    @FXML
    private void acc_btnnuevo(ActionEvent event) {
        try {
            String formulario = "/Vistas/FXMLPantallaUno.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());
            
            Parent root = loader.load();
            
            // Asociar con la pantallaUno
            FXMLPantallaUnoController controlador = loader.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error al abrir Pantalla Uno (CC): " + e.getMessage());
        }
    }
    
    /**
     * Detecta doble clic izquierdo sobre una fila de la tabla.
     * Si hay un cliente seleccionado, abre el formulario de edición son los datos del cliente.
     * @param event 
     */
    @FXML
    private void acc_clickedMouse(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Clientes objSeleccionado = this.tbl_cliente.getSelectionModel().getSelectedItem();
                if (objSeleccionado != null) {
                    fun_abrirModal(Integer.toString(objSeleccionado.getCli_id()));
                }
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Carga el formulario y llama al método del controlador asociado para mostrar los datos del 
     * cliente con la cédula especificada.
     * @param id 
     */
    public void fun_abrirModal(String id) {
        try {
            String formulario = "/Vistas/FXMLPantallaUno.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());

            Parent root = loader.load();
            
            // Asociar con la pantalla Personas
            FXMLPantallaUnoController controlador = loader.getController();
            controlador.fun_recuperarClientexID(id);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            Stage newStage = new Stage();
            // Agregar icono al formulario
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error al abrir modal: " + e.getMessage());
        }
    }
    
    public void cargarClientes(){
        try {
            this.tbl_cliente.getItems().clear();
            String cadenaSQL = "";
            cadenaSQL = cadenaSQL + "SELECT C.cli_id, C.cli_cedula, "
                    + "C.cli_nombres, C.cli_apellidos, C.cli_direccion, C.cli_telefono,"
                    + "C.cli_correo, C.cli_estado FROM Cliente C ";
            Clientes objCliente = new Clientes();
            this.tbl_cliente.setItems(objCliente.getListaClientes(cadenaSQL));
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_buscar(KeyEvent event) {
        String textoBusqueda = this.txt_buscar.getText().trim();
        System.out.println("Buscando" + textoBusqueda);
        
        try {
            this.tbl_cliente.getItems().clear();
           
            if (textoBusqueda.isEmpty()){
                this.cargarClientes();
                return;
            }
            
            String cadenaSQL = "";
            cadenaSQL = cadenaSQL + "SELECT C.cli_id, C.cli_cedula, "
                    + "C.cli_nombres, C.cli_apellidos, "
                    + "C.cli_direccion, C.cli_telefono, "
                    + "C.cli_correo, C.cli_estado FROM Cliente C ";
            
            String whereClause = "";
            if(rb_cedula.isSelected()) {
                // Búsqueda por cédula (parcial, con LIKE %texto%)
                whereClause = "WHERE C.cli_cedula LIKE '" + textoBusqueda + "%'";
                System.out.println("Buscando por cédula");
            } else if (rb_apellido.isSelected()){
                whereClause = "WHERE C.cli_apellidos LIKE '" + textoBusqueda + "%'";
                System.out.println("Busacando por apellido");                     
            } else {
                // Por defecto, busca por apellido si ninguno está seleccionado
                whereClause = "WHERE C.cli_apellidos LIKE '" + textoBusqueda + "%'";
                System.out.println("Ningún radio seleccionado, usando apellido por defecto");
            }
            
            cadenaSQL += whereClause;  // Agrega la cláusula WHERE
            
            Clientes objCliente = new Clientes();
            this.tbl_cliente.setItems(objCliente.getListaClientes(cadenaSQL));
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
            System.out.println("Error en búsqueda: " + e.getMessage());
        }
    }

    @FXML
    private void acc_rbApellido(ActionEvent event) {
        // Opcional: Limpia el campo de búsqueda al cambiar radio
        this.txt_buscar.clear();
        this.cargarClientes();  // Recarga todos al cambiar criterio
        System.out.println("Radio apellido seleccionado");
    }

    @FXML
    private void acc_rbCedula(ActionEvent event) {
        // Opcional: Igual que arriba
        this.txt_buscar.clear();
        this.cargarClientes();  // Recarga todos al cambiar criterio
        System.out.println("Radio cédula seleccionado");
    }
}

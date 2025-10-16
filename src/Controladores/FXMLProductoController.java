/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import Modelos.Producto;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class FXMLProductoController implements Initializable {

    @FXML
    private AnchorPane ap_productos;
    @FXML
    private Pane pane_superior;
    @FXML
    private Label lb_buscarProducto;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_nuevo;
    @FXML
    private TableView<Producto> tbl_producto;
    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_pvp;
    @FXML
    private TableColumn<?, ?> col_stock;
    @FXML
    private TableColumn<?, ?> col_estado;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TextField txt_buscar;
    @FXML
    private RadioButton rb_codigo;
    @FXML
    private RadioButton rb_nombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // ESTILOS
            ap_productos.getStyleClass().add("fondo");
            pane_superior.getStyleClass().add("anchorPaneSuperior");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_nuevo.getStyleClass().add("btnNuevo");
            btn_cancelar.getStyleClass().add("btnCancelar");
            
            tbl_producto.getStyleClass().add("table-view");
            
            this.col_codigo.setCellValueFactory(new PropertyValueFactory<>("prod_cod"));
            this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("prod_nombre"));
            this.col_pvp.setCellValueFactory(new PropertyValueFactory<>("prod_pvp_menor"));
            this.col_stock.setCellValueFactory(new PropertyValueFactory<>("prod_stock"));
            this.col_estado.setCellValueFactory(new PropertyValueFactory<>("prod_estado"));
            
            
            // TRADUCCIÓN
            lb_buscarProducto.setText(Idiomas.getTexto("label.buscarProducto"));
            col_codigo.setText(Idiomas.getTexto("columna.codigo"));
            col_nombre.setText(Idiomas.getTexto("columna.nombre"));
            col_pvp.setText(Idiomas.getTexto("columna.pvp"));
            col_stock.setText(Idiomas.getTexto("columna.stock"));
            col_estado.setText(Idiomas.getTexto("columna.estado"));
            tbl_producto.setPlaceholder(new Label(Idiomas.getTexto("tabla.registros")));
            btn_nuevo.setText(Idiomas.getTexto("boton.nuevo"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));
            
            // Carga los productos de prueba
            //General.BDProductos.cargarDatosPrueba();
            
            this.cargarProductos();
            
            // Agregar ToggleGroup para los RadioButtons
            ToggleGroup toggleGroup = new ToggleGroup();  // Crear el grupo
            rb_codigo.setToggleGroup(toggleGroup);     // Asignar rb_apellido al grupo
            rb_nombre.setToggleGroup(toggleGroup);       // Asignar rb_cedula al grupo
            rb_codigo.setSelected(true);               // Seleccionar rb_apellido por defecto
            
        } catch (Exception e) {
            System.out.println("Error en initialize Usuario" + e.getMessage());
        }
    }    
    
    /**
     * Oculta el panel de productos.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.ap_productos.setVisible(false);
    }
    
    /**
     * Abre la ventana modal para registrar un nuevo producto.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Nuevo".
     */
    @FXML
    private void acc_btnnuevo(ActionEvent event) {
        try {
            String formulario = "/Vistas/FXMLPantallaCuatro.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());
            
            Parent root = loader.load();
            
            // Asociar con la pantallaUno
            FXMLPantallaCuatroController controlador = loader.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error al abrir PantallaTres: " + e.getMessage());
        }
    }
    
    /**
     * Detecta doble clic izquierdo sobre un producto y abre la ventana de 
     * edición.
     * @param event : Evento de ratón generado al hacer doble clic en una fila 
     * de la tabla.
     */
    @FXML
    private void acc_clickedMouse(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Producto objSeleccionado = this.tbl_producto.getSelectionModel().getSelectedItem();
                if (objSeleccionado != null) {
                    // System.out.println(objSeleccionado.getCedula());
                    //fun_abrirModal(objSeleccionado
                    this.fun_abrirModal(objSeleccionado.getProd_id());
                }
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Abre la ventana modal de edición de un producto existente.
     * @param id : Objeto Producto cuyos datos se cargarán en el formulario.
     */
    public void fun_abrirModal(int id) {
        try {
            String formulario = "/Vistas/FXMLPantallaCuatro.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());

            Parent root = loader.load();
            
            // Asociar con la pantalla Personas
            FXMLPantallaCuatroController controlador = loader.getController();
            controlador.fun_recuperarProductoID(id);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            Stage newStage = new Stage();
            // Agregar icono al formulario
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error al abrir modal ProdCtr: " + e.getMessage());
        }
    }
    
    public void cargarProductos(){
        try {
            this.tbl_producto.getItems().clear();
            String cadenaSQL="";
            cadenaSQL = cadenaSQL + "SELECT P.prod_id, P.prod_cod, P.prod_nombre, "
                    + "P.prod_precioCompra, P.prod_pvpxmenor,"
                    + "P.prod_pvpxmayor, P.prod_stock, P.prod_aplicaiva,"
                    + "P.pod_imagen, P.prod_estado FROM Producto P ";
            Producto objProducto = new Producto();
            this.tbl_producto.setItems(objProducto.getListaProductos(cadenaSQL));
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_buscar(KeyEvent event) {
        String textoBusqueda = this.txt_buscar.getText().trim();
        System.out.println("Buscando: " + textoBusqueda);
        
        try {
            this.tbl_producto.getItems().clear();
           
            if (textoBusqueda.isEmpty()){
                this.cargarProductos();
                return;
            }
            
            String cadenaSQL = "";
            cadenaSQL = cadenaSQL + "SELECT P.prod_id, P.prod_cod, P.prod_nombre, "
                    + "P.prod_precioCompra, P.prod_pvpxmenor,"
                    + "P.prod_pvpxmayor, P.prod_stock, P.prod_aplicaiva,"
                    + "P.pod_imagen, P.prod_estado FROM Producto P ";
            
            String whereClause = "";
            if(rb_codigo.isSelected()) {
                // Búsqueda por cédula (parcial, con LIKE %texto%)
                whereClause = "WHERE P.prod_cod LIKE '" + textoBusqueda + "%'";
                System.out.println("Buscando por Código");
            } else if (rb_nombre.isSelected()){
                whereClause = "WHERE P.prod_nombre LIKE '" + textoBusqueda + "%'";
                System.out.println("Busacando por Nombre");                     
            } else {
                // Por defecto, busca por código si ninguno está seleccionado
                whereClause = "WHERE P.prod_cod LIKE '" + textoBusqueda + "%'";
                System.out.println("Ningún radio seleccionado, usando código por defecto");
            }
            
            cadenaSQL += whereClause;  // Agrega la cláusula WHERE
            
            Producto objProducto = new Producto();
            this.tbl_producto.setItems(objProducto.getListaProductos(cadenaSQL));
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
            System.out.println("Error en búsqueda: " + e.getMessage());
        }
    }

    @FXML
    private void acc_rbCodigo(ActionEvent event) {
        // Opcional: Limpia el campo de búsqueda al cambiar radio
        this.txt_buscar.clear();
        this.cargarProductos();// Recarga todos al cambiar criterio
        System.out.println("Radio código seleccionado");
    }

    @FXML
    private void acc_rbNombre(ActionEvent event) {
        // Opcional: Limpia el campo de búsqueda al cambiar radio
        this.txt_buscar.clear();
        this.cargarProductos();// Recarga todos al cambiar criterio
        System.out.println("Radio nombre seleccionado");
    }
}
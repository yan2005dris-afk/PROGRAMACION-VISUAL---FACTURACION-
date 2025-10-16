/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import General.Mod_general;
import Idiomas.Idiomas;
import Modelos.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * Controlador para la gestión de usuarios.
 * Muestra la lista, permite crear y editar usuarios.
 * 
 * @author Alejandro
 */
public class FXMLUsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tbl_usuario;
    @FXML
    private TableColumn<?, ?> col_usuario;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_estado;
    @FXML
    private Pane pane_superior;
    @FXML
    private Label lb_buscarUsuario;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_nuevo;
    @FXML
    private AnchorPane ap_usuarios;
    @FXML
    private TableColumn<?, ?> col_contrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Estilos
            ap_usuarios.getStyleClass().add("fondo");
            pane_superior.getStyleClass().add("anchorPaneSuperior");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_nuevo.getStyleClass().add("btnNuevo");
            btn_cancelar.getStyleClass().add("btnCancelar");
            tbl_usuario.getStyleClass().add("table-view");

            // Configuración de columnas
            this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("usr_nombres"));
            this.col_usuario.setCellValueFactory(new PropertyValueFactory<>("usr_usuario"));
            this.col_estado.setCellValueFactory(new PropertyValueFactory<>("usr_estado"));
            this.col_contrasenia.setCellValueFactory(new PropertyValueFactory<>("usr_clave"));

            // Cargar datos
            //this.tbl_usuario.setItems(General.BDUsuarios.listaUsuarios);
            cargarUsuarios();
            
            // TRADUCCIÓN
            lb_buscarUsuario.setText(Idiomas.getTexto("label.buscarUsuario"));
            col_nombre.setText(Idiomas.getTexto("columna.nombres"));
            col_usuario.setText(Idiomas.getTexto("columna.usuario"));
            col_estado.setText(Idiomas.getTexto("columna.estado"));
            //col_perfil.setText(Idiomas.getTexto("columna.perfil"));
            tbl_usuario.setPlaceholder(new Label(Idiomas.getTexto("tabla.registros")));
            btn_nuevo.setText(Idiomas.getTexto("boton.nuevo"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));

        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error en initialize Usuario: " + e.getMessage());
        }
    }
    
    /**
     * Oculta el panel de usuarios.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        ap_usuarios.setVisible(false);
    }
    
    /**
     * Abre la ventana modal para registrar un nuevo usuario.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Nuevo".
     */
    @FXML
    private void acc_btnnuevo(ActionEvent event) {
        try {
            String formulario = "/Vistas/FXMLPantallaTres.fxml";

            // 🔹 Usar el idioma actual desde la clase Idiomas
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());
            
            Parent root = loader.load();
            
            // Asociar con la pantallaDos
            FXMLPantallaTresController controlador = loader.getController();
            
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
     * Detecta doble clic izquierdo sobre un usuario y abre la ventana de 
     * edición.
     * @param event : Evento de ratón generado al hacer doble clic en una fila 
     * de la tabla.
     */
    @FXML
    private void acc_clickedMouse(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Usuario objSeleccionado = this.tbl_usuario.getSelectionModel().getSelectedItem();
                if (objSeleccionado != null) {
                    abrirFormularioUsuario(objSeleccionado.getUsr_id());
                }
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    /**
     * Abre el formulario de creación/edición de usuario.
     * @param seleccionado : Objeto Usuario cuyos datos se cargarán en el 
     * formulario.
     */
    private void abrirFormularioUsuario(int id) {
        try {
            String formulario = "/Vistas/FXMLPantallaTres.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario), Idiomas.getBundle());
            Parent root = loader.load();

            // Pasar datos si es edición
            FXMLPantallaTresController controlador = loader.getController();
            controlador.fun_recuperarUsuarioID(id);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.showAndWait();

        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error al abrir formulario de usuario: " + e.getMessage());
        }
    }
    
    public void cargarUsuarios(){
        try {
            this.tbl_usuario.getItems().clear();
            String cadenaSQL="";
            cadenaSQL=cadenaSQL+"SELECT U.usr_id, U.per_id, ";
            cadenaSQL=cadenaSQL+"U.usr_nombres,U.usr_usuario,";
            cadenaSQL=cadenaSQL+"U.usr_clave,U.usr_estado FROM Usuario U";
            Usuario objUsuario=new Usuario();
            this.tbl_usuario.setItems(objUsuario.getListaUsuarios(cadenaSQL));
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
        
    }
}

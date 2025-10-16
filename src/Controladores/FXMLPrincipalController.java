/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Idiomas.Idiomas;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLPrincipalController implements Initializable {
    
    private Main mainApp; // referencia a la instancia real de Main
    
    @FXML
    private MenuItem menu_pantallaUno;
    @FXML
    private MenuItem menu_pantallaDos;
    @FXML
    private Button btn_pantallaUno;
    @FXML
    private Button btn_pantallaDos;
    @FXML
    private VBox dataPane;
    @FXML
    private Accordion lateralAcordion;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TitledPane titledAdministracion;
    @FXML
    private TitledPane titledProcesos;
    @FXML
    private TitledPane titledReportes;
    @FXML
    private MenuItem menu_esp;
    @FXML
    private MenuItem menu_eng;
    @FXML
    private Label lb_usuario;
    @FXML
    private Label lb_nombreUsuario;
    @FXML
    private Label lb_fecha;
    @FXML
    private Label lb_fechaActual;
    @FXML
    private Label lb_version;
    @FXML
    private Label lb_versionActual;
    @FXML
    private Label lb_hora;
    @FXML
    private Label lb_horaActual;
    @FXML
    private Menu menu_administrador;
    @FXML
    private Menu menu_procesos;
    @FXML
    private Menu menu_reportes;
    @FXML
    private Button btn_pantallaTres;
    @FXML
    private Button btn_pantallaCuarto;
    @FXML
    private Button btn_pantallaCinco;
    @FXML
    private MenuItem menu_pantallaTres;
    @FXML
    private MenuItem menu_pantallaCuatro;
    @FXML
    private MenuItem menu_pantallaCinco;
    @FXML
    private Menu menu_opciones;
    @FXML
    private MenuItem menu_sesion;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // 🔹 Asignar clases CSS a elementos clave
            dataPane.getStyleClass().add("fondoPrincipal");
            menuBar.getStyleClass().add("menu-bar");
            lateralAcordion.getStyleClass().add("accordion");

            titledAdministracion.getStyleClass().add("titled-pane");
            titledProcesos.getStyleClass().add("titled-pane");
            titledReportes.getStyleClass().add("titled-pane");

            btn_pantallaUno.getStyleClass().add("btnCliente");
            btn_pantallaDos.getStyleClass().add("btnEmpresa");
            btn_pantallaTres.getStyleClass().add("btnUsuario");
            btn_pantallaCuarto.getStyleClass().add("btnProducto");
            btn_pantallaCinco.getStyleClass().add("btnFactura");
            
            // TRADUCCIÓN
            menu_pantallaUno.setText(Idiomas.getTexto("menu.pantallaUno"));
            menu_pantallaDos.setText(Idiomas.getTexto("menu.pantallaDos"));
            menu_pantallaTres.setText(Idiomas.getTexto("menu.pantallaTres"));
            menu_pantallaCuatro.setText(Idiomas.getTexto("menu.pantallaCuatro"));
            menu_pantallaCinco.setText(Idiomas.getTexto("menu.pantallaCinco"));
            menu_administrador.setText(Idiomas.getTexto("menu.administrador"));
            menu_procesos.setText(Idiomas.getTexto("menu.procesos"));
            menu_reportes.setText(Idiomas.getTexto("menu.reportes"));
            menu_opciones.setText(Idiomas.getTexto("menu.opciones"));
            menu_sesion.setText(Idiomas.getTexto("menu.sesion"));
            menu_esp.setText(Idiomas.getTexto("menu.espaniol"));
            menu_eng.setText(Idiomas.getTexto("menu.ingles"));
            titledAdministracion.setText(Idiomas.getTexto("menu.administrador"));
            titledProcesos.setText(Idiomas.getTexto("menu.procesos"));
            titledReportes.setText(Idiomas.getTexto("menu.reportes"));
            btn_pantallaUno.setText(Idiomas.getTexto("boton.cliente"));
            btn_pantallaDos.setText(Idiomas.getTexto("boton.empresa"));
            btn_pantallaTres.setText(Idiomas.getTexto("boton.usuario"));
            btn_pantallaCuarto.setText(Idiomas.getTexto("boton.producto"));
            btn_pantallaCinco.setText(Idiomas.getTexto("boton.factura"));
            lb_usuario.setText(Idiomas.getTexto("label.usuario"));
            lb_fecha.setText(Idiomas.getTexto("label.fecha"));
            lb_version.setText(Idiomas.getTexto("label.version"));
            lb_hora.setText(Idiomas.getTexto("label.hora"));
                            
        } catch (Exception e) {
            System.out.println("Error en initialize Principal Controler: " + e.getMessage());
        }
    }
    
    /** 
     * Recibe la instancia real de Main desde Main.abrirVentanaPrincipal() 
     */
    public void setMainApp(Main main) {
        this.mainApp = main;
    }
    
    /**
     * Carga la pantalla de Clientes en el panel central.
     * @param event : Evento de acción generado al seleccionar el menú 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_menupantallaUno(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLClientes.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Empresas en el panel central.
     * @param event : Evento de acción generado al seleccionar el menú 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_menupantallaDos(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLEmpresas.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Usuarios en el panel central.
     * @param event : Evento de acción generado al seleccionar el menú 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_menupantallaTres(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLUsuarios.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Producto en el panel central.
     * @param event : Evento de acción generado al seleccionar el menú 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_menupantallaCuatro(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLProducto.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Facrurar en el panel central.
     * @param event : Evento de acción generado al seleccionar el menú 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_menupantallaCinco(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLFacturar.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Clientes en el panel central.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_btnpantallaUno(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLClientes.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Empresas en el panel central.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * correspondiente.
     * @throws IOException
     */
    @FXML
    private void acc_btnpantallaDos(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLEmpresas.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Usuarios en el panel central.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_btnpantallaTres(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLUsuarios.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Productos en el panel central.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_btnpantallaCuatro(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLProducto.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga la pantalla de Factura en el panel central.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * correspondiente.
     * @throws IOException 
     */
    @FXML
    private void acc_btnpantallaCinco(ActionEvent event) throws IOException {
        String pantalla = "/Vistas/FXMLFacturar.fxml";
        setDataPane(fun_Animacion(pantalla));
    }
    
    /**
     * Carga un archivo FXML y aplica una animación de desvanecimiento.
     * @param url : Ruta del archivo FXML a cargar.
     * @return AnchorPane con el contenido cargado y animado.
     * @throws IOException : Si el archivo FXML no se encuentra o no se puede 
     * cargar.
     */
    public AnchorPane fun_Animacion(String url) throws IOException {
        // 🔹 Usar siempre el idioma actual desde la clase Idiomas
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url), Idiomas.getBundle());
        AnchorPane anchorPane = loader.load();
        
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(anchorPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setAutoReverse(false);
        ft.play();
        return anchorPane;
    }
    
    /**
     * Inserta un nodo en el panel central y lo centra horizontal y verticalmente.
     * @param node : Nodo JavaFX que se mostrará en el panel central.
     */
    public void setDataPane(Node node) {
        VBox wrapper = new VBox(node);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setFillWidth(false); // evita que se estire horizontalmente
        dataPane.getChildren().setAll(wrapper);
    }

    /**
     * Recarga la ventana principal aplicando el idioma actual.
     */
    private void recargarVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/FXMLPrincipal.fxml"),Idiomas.getBundle());
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());
            
            // Pasar referencia de Main al nuevo controlador
            FXMLPrincipalController controlador = loader.getController();
            controlador.setMainApp(mainApp);
            
            stage.setScene(scene);
            stage.setMaximized(true); // fuerza a pantalla completa
            stage.show();
        } catch (IOException e) {
            System.out.println("Error recargando ventana: " + e.getMessage());
        }
    }
    
    /**
     * Cambia el idioma de la interfaz a español y recarga la ventana.
     * @param event : Evento de acción generado al seleccionar el menú de 
     * idioma español.
     */
    @FXML
    private void acc_menuEsp(ActionEvent event) {
        Idiomas.cambiarIdioma("es");
        recargarVentana();
    }
    
    /**
     * Cambia el idioma de la interfaz a inglés y recarga la ventana.
     * @param event : Evento de acción generado al seleccionar el menú de 
     * idioma inglés.
     */
    @FXML
    private void acc_menuIng(ActionEvent event) {
        Idiomas.cambiarIdioma("en");
        recargarVentana();
    }
    
    /**
     * Cierra la sesión actual y vuelve a mostrar la ventana de login.
     * @param event : Evento de acción generado al seleccionar la opción de 
     * cerrar sesión.
     */
    @FXML
    private void acc_menuSesion(ActionEvent event) {
        try {
            if (mainApp != null) {
                mainApp.mostrarLogin(); // volver al login en el mismo Stage
            } else {
                System.out.println("mainApp es null, no se puede cerrar sesión correctamente.");
            }
        } catch (Exception e) {
            System.out.println("Error menuSesion Principal Controler: " + e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controladores;

import General.Mod_general;
import Idiomas.Idiomas;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 * Controla el flujo entre login y ventana principal.
 * 
 * @author Alejandro
 */
public class Main extends Application {

    // Idioma actual de la aplicación (por defecto español)
    public static Locale currentLocale = new Locale("es");

    private Stage primaryStage; // Stage principal
    private boolean principalAbierta = false; // Controla si la ventana principal ya está abierta

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Principal proporcionado por JavaFX al iniciar la aplicación.
     * @param stage 
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        try {
            General.BD accBD=new General.BD();
            //Verificar si podemos conectar con la BD
            try {
                if(accBD.conectarBD()){
                    System.out.println("Se conectó a la base de datos");
                }else{
                    System.out.println("No se conectó a la base de datos");
                    return;
                }
            } catch (Exception e) {
                General.Mod_general.fun_mensajeError("Error al conectar" + e.getMessage());
                return;
            }finally{
                    accBD.desconectarBD();
            }
            //Fin conectar con la BD
            primaryStage.getIcons().add(new Image("/Iconos/rias.png"));
        } catch (Exception e) {
            System.out.println("No se encontró el icono: " + e.getMessage());
        }
        
        mostrarLogin();
        
        primaryStage.show();
    }
    
    /**
     * Carga y muestra la pantalla de login en el Stage principal.
     * Nota: El formateo de decimales/fechas se aplica en el controlador FXMLLoginController.initialize()
     * (e.g., Mod_general.agregarFormateoDecimal(txt_campo); si hay campos numéricos).
     */
    public void mostrarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/FXMLLogin.fxml"));
            Pane loginPane = loader.load();

            Scene loginScene = new Scene(loginPane);
            loginScene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());

            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);   
            
            // Inicializa formateo global para esta escena (soporte para coma decimal y fechas dd-MM-yy)
            Mod_general.inicializarFormateoGlobal(loginScene);
            
            // Pasar referencia de Main al controlador de login
            FXMLLoginController controlador = loader.getController();
            controlador.setMainApp(this);

            principalAbierta = false; // al volver al login, marcamos que la principal no está abierta
            
        } catch (Exception e) {
            System.out.println("Error al mostrar login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana principal en el Stage principal.
     * Nota: El formateo se aplica en controladores como FXMLPrincipalController o FXMLFacturarController
     * (e.g., en initialize(): Mod_general.agregarFormateoDecimal(txt_precio); y cambiar fechas a "dd-MM-yy").
     */
    public void abrirVentanaPrincipal() {
        if (principalAbierta) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "La ventana principal ya está abierta.", ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Vistas/FXMLPrincipal.fxml"),
                    Idiomas.getBundle()
            );
            Pane root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Vistas/mystilos.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle(Idiomas.getTexto("titulo"));
            primaryStage.setMaximized(true);
            primaryStage.show();
            
            // Inicializa formateo global para esta escena (coma en decimales, fechas dd-MM-yy)
            Mod_general.inicializarFormateoGlobal(scene);
            
            // Pasar referencia de Main al controlador principal
            FXMLPrincipalController controlador = loader.getController();
            // Si quieres que desde principal se pueda cerrar sesión:
            controlador.setMainApp(this);

            // Confirmar antes de cerrar la aplicación
            primaryStage.setOnCloseRequest(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Confirmar salida");
                alerta.setHeaderText(null);
                alerta.setContentText("¿Seguro que deseas cerrar la aplicación?");
                if (alerta.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    event.consume(); // Cancela el cierre
                } else {
                    principalAbierta = false; // Permitir abrir de nuevo si se cierra
                }
            });

            principalAbierta = true;

        } catch (Exception e) {
            System.out.println("Error al abrir ventana principal Main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


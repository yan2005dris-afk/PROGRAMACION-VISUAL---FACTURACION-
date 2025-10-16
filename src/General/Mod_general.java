/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package General;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.TAB;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Es una clase utilitaria que centraliza funciones comunes para la interacción 
 * con la interfaz JavaFX.
 * @author Alejandro
 */
public class Mod_general {
    
public static int gestorBD = 2;// 1: se conecta con MySql - 2: SQLServer
    
    /**
     * Detectar una tecla específica en un nodo y mueve el foco a otro nodo.
     * @param nodoOrigen : El nodo actual donde se detecta la tecla.
     * @param tecla : Tecla a detectar (ejemplo: KeyCode.ENTER).
     * @param nodoAFocus : Nodo al que se moverá el foco si se detecta la tecla.
     */
    public static void fun_detectarTecla(Node nodoOrigen, KeyCode tecla, Node nodoAFocus) {
        nodoOrigen.setOnKeyPressed(evento ->{
            if(evento.getCode() == tecla){
                nodoAFocus.requestFocus();
                evento.consume(); // Prevenir comportamiento por defecto
            }
            if(evento.getCode() == TAB) {
                evento.consume(); // Prevenir tabulación por defecto
            }
        });
    }
    
    /**
     * Función para agregar un atajo de teclado global en una escena.
     * @param scene : Escena donde se aplicará el atajo.
     * @param keyCode : La tecla principal del atajo.
     * @param modifiers : El modificador (ejemplo: KeyCombination.CONTROL_DOWN)
     * @param action : La acción a ejecutar cuando se presione el atajo.
     */
    public static void addShortcut(Scene scene, KeyCode keyCode,Modifier modifiers, Runnable action) {
        KeyCombination combination = new KeyCodeCombination(keyCode, modifiers);
        
        EventHandler<KeyEvent> handler = event -> {
            if (combination.match(event)){
                action.run();
                event.consume();
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, handler);
    }
    
    /**
     * Función que muestra un mensaje de información en una alerta modal.
     * @param mensaje : Texto que se mostrará en la alerta.
     */
    public static void fun_mensajeInformacion(String mensaje){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Mensaje del sistema");
        alertInfo.setContentText(mensaje);
        alertInfo.showAndWait();
    }
    
    /**
     * Muestra un mensaje de error en una alerta modal.
     * @param mensaje : Texto que se mostrará en la alerta.
     */
    public static void fun_mensajeError(String mensaje){
        Alert alertInfo = new Alert(Alert.AlertType.ERROR);
        alertInfo.setTitle("Mensaje del sistema");
        alertInfo.setContentText(mensaje);
        alertInfo.showAndWait();
    }
    
    /**
     * 
     * @param numero
     * @return 
     */
    public static boolean esNumerico(String numero){
        if(numero==null || numero.isEmpty()){
            return false;
        }
        for(int i=0;i<numero.length();i++){
            //isDigit es un método de la clase Character que retorna true si el digito es decimal
            //.charAt(i) toma el caracter en la posicion i de numero
            if(!Character.isDigit(numero.charAt(i))){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @param numero
     * @return 
     */
    public static boolean esDecimal(String numero){
        boolean puntoEncontrado=false;
        if(numero==null || numero.isEmpty()){
            return false;
        }
        for(int i=0;i<numero.length();i++){
            char c=numero.charAt(i);
            if(c == '.'){
                //Para evaluar que exista solo un punto
                if(puntoEncontrado){
                    return false;
                }
                puntoEncontrado=true;
            }else if(!Character.isDigit(c)){
                //No es dígito ni punto
                return false;
            }
        }
        return !(numero.equals(".")||numero.equals(""));
    }
    
    /**
     * Formatea un número decimal reemplazando "." por "," (para locales con coma decimal).
     * Valida primero con esDecimal. Si no es válido, retorna "0,00".
     * @param numero String del número (e.g., "12.34" -> "12,34")
     * @return String formateado o "0,00" si inválido.
     */
    public static String formatearDecimal(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            return "0,00";
        }
        // Valida si es decimal (acepta "." temporalmente)
        if (!esDecimal(numero)) {
            fun_mensajeError("Número decimal inválido: " + numero);
            return "0,00";
        }
        // Reemplaza "." por ","
        String formateado = numero.replace(".", ",");
        // Opcional: Agrega 2 decimales si no los tiene (e.g., "12" -> "12,00")
        if (!formateado.contains(",")) {
            formateado += ",00";
        }
        return formateado;
    }
    
    /**
     * Convierte un string con coma decimal a Float (para setters en modelo).
     * Invierte "," a "." para parseFloat.
     * @param numeroFormateado String con coma (e.g., "12,34" -> 12.34f)
     * @return Float del número, o 0.0f si inválido.
     */
    public static Float parseDecimal(String numeroFormateado) {
        if (numeroFormateado == null || numeroFormateado.trim().isEmpty()) {
            return 0.0f;
        }
        try {
            // Invierte "," a "." para parse
            String paraParse = numeroFormateado.replace(",", ".");
            return Float.parseFloat(paraParse);
        } catch (NumberFormatException e) {
            fun_mensajeError("Error parseando decimal: " + numeroFormateado);
            return 0.0f;
        }
    }
    
    /**
     * Agrega un listener a un TextField para formatear decimales en tiempo real.
     * - Al escribir: Reemplaza "." por "," inmediatamente.
     * - Al perder foco: Valida y formatea completo (agrega ",00" si necesario).
     * @param campo TextField para números decimales (e.g., cantidad, PVP).
     */
    public static void agregarFormateoDecimal(TextField campo) {
        // Listener para reemplazar "." por "," al escribir
        campo.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                return;  // No formatear en borrado
            }
            String texto = campo.getText();
            if (texto.contains(".")) {
                String nuevoTexto = texto.replace(".", ",");
                campo.setText(nuevoTexto);
                // Mueve cursor al final
                campo.positionCaret(nuevoTexto.length());
            }
        });
        
        // Listener al perder foco: Valida y formatea
        campo.focusedProperty().addListener((obs, viejo, nuevo) -> {
            if (!nuevo) {  // Pierde foco
                String texto = campo.getText().trim();
                if (!texto.isEmpty()) {
                    String formateado = formatearDecimal(texto);
                    campo.setText(formateado);
                }
            }
        });
    }
    
    // FUNCIONES PARA FORMATEO DE FECHAS
    
    /**
     * Formatea una fecha de "yyyy-MM-dd" a "dd-MM-yy".
     * @param fecha String en formato "yyyy-MM-dd" (e.g., "2024-10-15" -> "15-10-24")
     * @return String formateado, o la original si inválida.
     */
    public static String formatearFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty() || fecha.length() != 10) {
            return fecha;  // Retorna original si inválida
        }
        try {
            // Parsea con patrón yyyy-MM-dd
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(fecha, inputFormatter);
            
            // Formatea a dd-MM-yy
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            return localDate.format(outputFormatter);
        } catch (DateTimeParseException e) {
            fun_mensajeError("Fecha inválida: " + fecha + ". Use yyyy-MM-dd.");
            return fecha;  // Retorna original
        }
    }
    
    /**
     * Parsea una fecha formateada "dd-MM-yy" de vuelta a "yyyy-MM-dd" (para BD o modelo).
     * @param fechaFormateada String en "dd-MM-yy" (e.g., "15-10-24" -> "2024-10-15")
     * @return String en "yyyy-MM-dd", o null si inválida.
     */
    public static String parseFecha(String fechaFormateada) {
        if (fechaFormateada == null || fechaFormateada.trim().isEmpty() || fechaFormateada.length() != 8) {
            return null;
        }
        try {
            // Parsea con patrón dd-MM-yy (asumiendo siglo actual; ajusta si necesitas lógica de siglo)
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            LocalDate localDate = LocalDate.parse(fechaFormateada, inputFormatter);
            
            // Formatea a yyyy-MM-dd
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(outputFormatter);
        } catch (DateTimeParseException e) {
            fun_mensajeError("Error parseando fecha: " + fechaFormateada + ". Use dd-MM-yy.");
            return null;
        }
    }
    
    /**
     * Agrega un listener a un TextField para formatear fechas en tiempo real.
     * - Al escribir: Valida formato parcial.
     * - Al perder foco: Convierte "yyyy-MM-dd" a "dd-MM-yy" si válido.
     * Nota: Asume input inicial en "yyyy-MM-dd"; ajusta si el usuario ingresa directamente "dd-MM-yy".
     * @param campo TextField para fechas (e.g., txt_fecha).
     */
    public static void agregarFormateoFecha(TextField campo) {
        // Listener al perder foco: Formatea si es yyyy-MM-dd
        campo.focusedProperty().addListener((obs, viejo, nuevo) -> {
            if (!nuevo) {  // Pierde foco
                String texto = campo.getText().trim();
                if (!texto.isEmpty() && texto.length() == 10 && texto.contains("-")) {
                    String formateado = formatearFecha(texto);
                    if (!formateado.equals(texto)) {  // Solo si cambió
                        campo.setText(formateado);
                    }
                }
            }
        });
        
        // Opcional: Listener para input en tiempo real (valida formato dd-MM-yy si el usuario escribe así)
        campo.textProperty().addListener((obs, viejo, nuevo) -> {
            String texto = nuevo.trim();
            if (texto.length() == 8 && texto.matches("\\d{2}-\\d{2}-\\d{2}")) {
                // Si parece dd-MM-yy, no interfiere; el blur lo manejará
            } else if (texto.length() > 10) {
                campo.setText(viejo);  // Previene input demasiado largo
            }
        });
    }
        
    /**
     * Función sobrecargada para aceptar múltiples modificadores
     * @param scene : La escena donde se aplicará el atajo
     * @param keyCode : La tecla principal del atajo
     * @param modifiers : Los modificadores (ejemplo: KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)
     * @param action : La acción a ejecutar cuando se presione el atajo
     */
    /*public static void addShorcut(Scene scene, KeyCode keyCode, Modifier[] modifiers, Runnable action) {
        KeyCombination combination = new KeyCodeCombination(keyCode, modifiers);
        
        EventHandler<KeyEvent> handler = event -> {
            if (combination.match(event)) {
                action.run();
                event.consume();
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, handler);
    }*/
           
    /**
     * Función para validar campos de texto cuando pierden el foco
     * @param campo El campo de texto a validar
     * @param validador Función que realiza la validación y devuelve un mensaje de error (vacío si es válido)
     */
    /*public static void validarAlPerderFoco(TextInputControl campo, java.util.function.Function<String, String> validador) {
        campo.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando pierde el foco
                String error = validador.apply(campo.getText());
                if (!error.isEmpty()) {
                    // Mostrar el error
                    System.out.println("Error en campo: " + error);
                    // Cambiar el estilo del campo para indicar error
                    resaltarError(campo, true);
                } else {
                    resaltarError(campo, false);
                }
            }
        });
    }*/
    
    /**
     * Función para resaltar visualmente un error en un campo
     * @param nodo El nodo a resaltar
     * @param tieneError true para resaltar error, false para quitar resaltado
     */
    /*public static void resaltarError(Node nodo, boolean tieneError) {
        if (tieneError) {
            nodo.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            nodo.setStyle("");
        }
    }*/
    
    /**
     * Función para navegar entre pantallas
     * @param stage El stage actual
     * @param rutaFXML La ruta al archivo FXML de la nueva pantalla
     */
    /*public static void cambiarPantalla(javafx.stage.Stage stage, String rutaFXML) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(Mod_general.class.getResource(rutaFXML));
            javafx.scene.Parent root = loader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (java.io.IOException e) {
            System.out.println("Error al cargar la pantalla: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
    
    /**
     * Función para mostrar alertas de manera sencilla
     * @param tipo Tipo de alerta (INFO, WARNING, ERROR, CONFIRMATION)
     * @param titulo Título de la alerta
     * @param mensaje Mensaje de la alerta
     */
    /*public static void mostrarAlerta(String tipo, String titulo, String mensaje) {
        javafx.scene.control.Alert.AlertType alertType;
        
        switch (tipo.toUpperCase()) {
            case "INFO":
                alertType = javafx.scene.control.Alert.AlertType.INFORMATION;
                break;
            case "WARNING":
                alertType = javafx.scene.control.Alert.AlertType.WARNING;
                break;
            case "ERROR":
                alertType = javafx.scene.control.Alert.AlertType.ERROR;
                break;
            case "CONFIRMATION":
                alertType = javafx.scene.control.Alert.AlertType.CONFIRMATION;
                break;
            default:
                alertType = javafx.scene.control.Alert.AlertType.INFORMATION;
        }
        
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }*/
    
    /**
     * Inicializa formateo global para una escena (e.g., aplica estilos o listeners base para decimales/fechas).
     * Llama esto desde Main.java al cargar escenas para consistencia.
     * @param scene La escena a inicializar.
     */
    public static void inicializarFormateoGlobal(Scene scene) {
        if (scene == null) return;
        
        // Opcional: Agrega estilos CSS globales para inputs decimales (e.g., hint para coma)
        // scene.getStylesheets().add(getClass().getResource("/Vistas/estilos-formateo.css").toExternalForm());  // Si tienes CSS extra
        
        // Opcional: Listener global para toda la escena (e.g., Ctrl+F para formatear todos los campos)
        // Por ahora, solo log o placeholder; expándelo si necesitas
        String titulo = (scene.getWindow() != null && scene.getWindow() instanceof Stage) 
                ? ((Stage) scene.getWindow()).getTitle() : "Escena sin título";
        System.out.println("Formateo global inicializado para escena: " + titulo);
        
        // Futuro: Recorre todos los TextFields en la escena y aplica agregarFormateoDecimal si tienen class CSS específica
        // Ejemplo: for (Node node : scene.getRoot().getChildrenUnmodifiable()) { 
        //     if (node instanceof TextField && node.getStyleClass().contains("decimal")) { 
        //         agregarFormateoDecimal((TextField) node); 
        //     } 
        // }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import General.BD;
import Idiomas.Idiomas;
import Modelos.Producto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLPantallaCuatroController implements Initializable {

    @FXML
    private AnchorPane ap_pantallaCuatro;
    @FXML
    private Pane pane_inferior;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_grabar;
    @FXML
    private Button btn_cargarImagen;
    @FXML
    private Text text_encabezadoProd;
    @FXML
    private Text text_codigo;
    @FXML
    private Text text_nombre;
    @FXML
    private Text text_precioCompra;
    @FXML
    private Text text_pvp;
    @FXML
    private Text text_stock;
    @FXML
    private Text text_aplicarIVA;
    @FXML
    private Text text_estado;
    @FXML
    private Text text_pvp2;
    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_precioCompra;
    @FXML
    private TextField txt_stock;
    @FXML
    private TextField txt_pvp;
    @FXML
    private TextField txt_estado;
    @FXML
    private CheckBox check_aplicarIva;
    @FXML
    private TextField txt_precioMayorista;
    @FXML
    private ImageView img_producto;
    
    int miId;
    int aplicaIva;
    
    // Nueva variable para almacenar los bytes de la imagen
    private byte[] imagenBytes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // Estilos        
            this.txt_codigo.requestFocus();
            ap_pantallaCuatro.getStyleClass().add("fondo");
            pane_inferior.getStyleClass().add("anchorPaneInferior");
            btn_grabar.getStyleClass().add("btnGrabar");
            btn_cancelar.getStyleClass().add("btnCancelar");
            btn_cargarImagen.getStyleClass().add("btnCargar");
            txt_codigo.getStyleClass().add("text-field");
            txt_nombre.getStyleClass().add("text-field");
            txt_precioCompra.getStyleClass().add("text-field");
            txt_pvp.getStyleClass().add("text-field");
            txt_stock.getStyleClass().add("text-field");
            txt_estado.getStyleClass().add("text-field");

            // EVENTOS
            General.Mod_general.fun_detectarTecla(txt_codigo, KeyCode.ENTER, this.txt_nombre);
            General.Mod_general.fun_detectarTecla(txt_nombre, KeyCode.ENTER, this.txt_precioCompra);
            General.Mod_general.fun_detectarTecla(txt_precioCompra, KeyCode.ENTER, this.txt_pvp);
            General.Mod_general.fun_detectarTecla(txt_pvp, KeyCode.ENTER, this.txt_stock);
            General.Mod_general.fun_detectarTecla(txt_stock, KeyCode.ENTER, txt_estado);
            General.Mod_general.fun_detectarTecla(txt_estado, KeyCode.ENTER, this.btn_grabar);
            
            // Manejar CheckBox por separado (simular foco con tecla)
            txt_stock.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    check_aplicarIva.setSelected(!check_aplicarIva.isSelected()); // Toggle al presionar Enter en stock
                    txt_estado.requestFocus(); // Luego ir a estado
                }
            });
            
            // TRADUCCIÓN
            text_encabezadoProd.setText(Idiomas.getTexto("texto.encabezadoProducto"));
            text_codigo.setText(Idiomas.getTexto("texto.codigo"));
            text_nombre.setText(Idiomas.getTexto("texto.nombre"));
            text_precioCompra.setText(Idiomas.getTexto("texto.precioCompra"));
            text_pvp.setText(Idiomas.getTexto("texto.precioVenta"));
            text_stock.setText(Idiomas.getTexto("texto.stock"));
            text_aplicarIVA.setText(Idiomas.getTexto("texto.aplicarIVA"));
            text_estado.setText(Idiomas.getTexto("texto.estado"));
            btn_grabar.setText(Idiomas.getTexto("boton.grabar"));
            btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));
            btn_cargarImagen.setText(Idiomas.getTexto("boton.cargar"));
            
            // Cuando pierde el focus
            this.txt_codigo.focusedProperty().addListener((abs,oldVal,newVal) -> {
                if(!newVal){
                    System.out.println("Validar desde el focus");
                }
            });

            // Esperar a que la escena esté disponible antes de configurar el atajo
            // Esto evita NullPointerException si la escena no está cargada aún
            ap_pantallaCuatro.sceneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // addShortcut: Agregar un acceso directo o combinación de teclas
                    General.Mod_general.addShortcut(newValue, KeyCode.G, KeyCombination.CONTROL_DOWN, () -> 
                        acc_btngrabar(new ActionEvent()));
                }
            });
        } catch (Exception e) {
            System.out.println("Error en initialize de FXMLPantallaCuatroController: " + e.getMessage());
            e.printStackTrace(); // Para depuración
        }
    }    
    
    /**
     * Cierra la ventana actual de la Pantalla Cuatro.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.cerrarFormulario();
    }
    
    /**
     * Valida los datos ingresados y registra un nuevo producto en la lista.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Grabar" o al usar el atajo Ctrl + G.
     */
    @FXML
    private void acc_btngrabar(ActionEvent event) {
        try {
            if (fun_validar()) {
                if (this.check_aplicarIva.isSelected()) {
                    aplicaIva = 1;
                } else {
                    aplicaIva = 0;
                }

                BD bdInstance = new BD();  // Crear una instancia de BD
                if (bdInstance.conectarBD()) {  // Intentar conectar
                    Connection connection = bdInstance.getConexion();  // Obtener la conexión
                    String sql = "";
                    PreparedStatement preparedStatement = null;

                    if (this.miId == 0) {
                        // Insert
                        sql = "INSERT INTO Producto (prod_cod, prod_nombre, prod_precioCompra, prod_pvpxmenor, " +
                              "prod_pvpxmayor, prod_stock, prod_aplicaIva, pod_imagen, prod_estado) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, this.txt_codigo.getText());
                        preparedStatement.setString(2, this.txt_nombre.getText());
                        preparedStatement.setFloat(3, Float.parseFloat(this.txt_precioCompra.getText()));
                        preparedStatement.setFloat(4, Float.parseFloat(this.txt_pvp.getText()));
                        preparedStatement.setFloat(5, Float.parseFloat(this.txt_precioMayorista.getText()));
                        preparedStatement.setFloat(6, Float.parseFloat(this.txt_stock.getText()));
                        preparedStatement.setInt(7, this.aplicaIva);
                        preparedStatement.setBytes(8, imagenBytes);  // Insertar datos binarios
                        preparedStatement.setString(9, this.txt_estado.getText());
                    } else {
                        // Update
                        sql = "UPDATE Producto SET prod_cod = ?, prod_nombre = ?, prod_precioCompra = ?, " +
                              "prod_pvpxmenor = ?, prod_pvpxmayor = ?, prod_stock = ?, prod_aplicaIva = ?, " +
                              "pod_imagen = ?, prod_estado = ? WHERE prod_id = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, this.txt_codigo.getText());
                        preparedStatement.setString(2, this.txt_nombre.getText());
                        preparedStatement.setFloat(3, Float.parseFloat(this.txt_precioCompra.getText()));
                        preparedStatement.setFloat(4, Float.parseFloat(this.txt_pvp.getText()));
                        preparedStatement.setFloat(5, Float.parseFloat(this.txt_precioMayorista.getText()));
                        preparedStatement.setFloat(6, Float.parseFloat(this.txt_stock.getText()));
                        preparedStatement.setInt(7, this.aplicaIva);
                        preparedStatement.setBytes(8, imagenBytes);  // Actualizar datos binarios
                        preparedStatement.setString(9, this.txt_estado.getText());
                        preparedStatement.setInt(10, this.miId);
                    }

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        General.Mod_general.fun_mensajeInformacion("Se registró con éxito.");
                        cerrarFormulario();
                    } else {
                        General.Mod_general.fun_mensajeError("Error al grabar el producto.");
                    }

                    preparedStatement.close();  // Cerrar PreparedStatement
                    bdInstance.desconectarBD();  // Cerrar la conexión
                } else {
                    General.Mod_general.fun_mensajeError("No se pudo conectar a la base de datos.");
                }
            } else {
                System.out.println("Datos sin validar");
            }
        } catch (SQLException | NumberFormatException e) {
            General.Mod_general.fun_mensajeError("Error al grabar: " + e.getMessage());
        }
    }
    
    /**
     * Verifica que el campo de código no esté vacío.
     * @return true si la validación es correcta, false en caso contrario.
     */
    private boolean fun_validar(){
        try {
            // trim para comprimir los espacios y el isEmpty es para borrar espacios vacios
            if(this.txt_codigo.getText().trim().isEmpty()){
                General.Mod_general.fun_mensajeInformacion("***Registre el Código***");
                this.txt_codigo.requestFocus();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Limpia todos los campos del formulario y coloca el foco en el campo de 
     * código.
     */
    public void fun_limpiar(){
        this.txt_codigo.clear();
        this.txt_nombre.clear();
        this.txt_precioCompra.clear();
        this.txt_pvp.clear();
        this.txt_stock.clear();
        check_aplicarIva.setSelected(false);
        this.txt_estado.clear();
        this.txt_codigo.requestFocus();
    }
    
    /**
     * Carga los datos de un producto existente en los campos del formulario.
     * @param id 
     */
    public void fun_recuperarProductoID(int id) {
        try {
            miId=id;
            if(miId>0){
                //recuperar la información
                String cadenaSQL = "SELECT * FROM Producto WHERE prod_id = " + id;
                Producto objProducto = new Producto();
                ObservableList<Producto> productos = objProducto.getListaProductos(cadenaSQL);
                if(productos.size()>0){
                    this.txt_codigo.setText(productos.get(0).getProd_cod());
                    this.txt_nombre.setText(productos.get(0).getProd_nombre());
                    this.txt_precioCompra.setText(Float.toString(productos.get(0).getProd_precioCompra()));
                    this.txt_pvp.setText(Float.toString(productos.get(0).getProd_pvp_menor()));
                    this.txt_precioMayorista.setText(Float.toString(productos.get(0).getProd_pvp_mayor()));
                    this.txt_stock.setText(Float.toString(productos.get(0).getProd_stock()));
                    if(productos.get(0).isProd_aplicaIVA()){
                        this.check_aplicarIva.setSelected(true);
                    }else{
                        this.check_aplicarIva.setSelected(false);
                    }
                    this.txt_estado.setText(productos.get(0).getProd_estado());
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

    @FXML
    private void acc_btnCargarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        File selectedFile = fileChooser.showOpenDialog((Stage) btn_cargarImagen.getScene().getWindow());
        
        if (selectedFile != null) {
            try {
                // Leer el archivo en un byte array
                imagenBytes = new byte[(int) selectedFile.length()];
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                fileInputStream.read(imagenBytes);
                fileInputStream.close();
                
                // Previsualizar la imagen en el ImageView
                Image image = new Image(selectedFile.toURI().toString());
                img_producto.setImage(image);
                
                System.out.println("Imagen cargada exitosamente.");
            } catch (IOException e) {
                General.Mod_general.fun_mensajeError("Error al cargar la imagen: " + e.getMessage());
            }
        }
    }
}

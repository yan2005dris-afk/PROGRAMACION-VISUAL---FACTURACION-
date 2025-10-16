/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import General.BD;
import General.Mod_general;  // Agregado para mensajes de error/información
import Idiomas.Idiomas;
import Modelos.Clientes;  // Solo una vez, sin duplicado
import Modelos.DetalleFactura;
import Modelos.Factura;
import Modelos.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLFacturarController implements Initializable {

    @FXML
    private AnchorPane ap_facturar;
    @FXML
    private TextField txt_factura;
    @FXML
    private TextField txt_fecha;
    @FXML
    private TextField txt_documento;
    @FXML
    private TextField txt_nombres;
    @FXML
    private TextField txt_apellidos;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_correo;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TableView<DetalleFactura> tbl_detalleFactura;
    @FXML
    private TableColumn<DetalleFactura, String> col_codigo;
    @FXML
    private TableColumn<DetalleFactura, String> col_descripcion;
    @FXML
    private TableColumn<DetalleFactura, Float> col_cantidad;
    @FXML
    private TableColumn<DetalleFactura, Float> col_pvp;
    @FXML
    private TableColumn<DetalleFactura, Boolean> col_aplicaIVA;
    @FXML
    private TableColumn<DetalleFactura, Float> col_iva;
    @FXML
    private TableColumn<DetalleFactura, Float> col_total;
    @FXML
    private TableColumn<DetalleFactura, String> col_buscar;
    @FXML
    private TextField txt_subTotal;
    @FXML
    private TextField txt_subTotalCero;
    @FXML
    private TextField txt_iva;
    @FXML
    private TextField txt_descuento;
    @FXML
    private TextField txt_total;
    @FXML
    private Button btn_anular;
    @FXML
    private Button btn_grabar;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_imprimir;
    @FXML
    private Pane pane_superior;
    @FXML
    private Pane pane_medio;
    @FXML
    private Label text_factura;
    @FXML
    private Label text_fecha;
    @FXML
    private Label text_ciruc;
    @FXML
    private Label text_nombres;
    @FXML
    private Label text_apellidos;
    @FXML
    private Label text_telefono;
    @FXML
    private Label text_correo;
    @FXML
    private Label text_direccion;
    @FXML
    private Label text_subTotal;
    @FXML
    private Label text_subTotalCero;
    @FXML
    private Label text_iva;
    @FXML
    private Label text_descuento;
    @FXML
    private Label text_total;
    
    private Factura facturaActual = new Factura();
    private ObservableList<DetalleFactura> detallesObservable;
    private static final float IVA_PORC = 0.15f;
    private BD bdInstance;  // Instancia de BD para consultas
    String cadenaSQL="";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            bdInstance = new BD();  // Inicializa BD
            
            // ESTILOS
            this.aplicarEstilos();

            // TRADUCCIÓN
            this.aplicarTraduccion();

            // Configurar las columnas de la tabla (con try-catch para crashes tempranos)
            try {
                this.configurarColumnas();
            } catch (Exception e) {
                System.err.println("Error configurando tabla: " + e.getMessage());
                e.printStackTrace();
                Mod_general.fun_mensajeError("Error en tabla de productos: " + e.getMessage() + ". Verifique tipos de datos en DetalleFactura.");
                tbl_detalleFactura.setEditable(false);  // Fallback: deshabilita edición
            }
            
            // Inicializar la factura y su lista observable
            facturaActual.setFac_detalle(new ArrayList<>());
            detallesObservable = FXCollections.observableList(facturaActual.getFac_detalle());
            tbl_detalleFactura.setItems(detallesObservable);
            
            // Configurar número y fecha automáticos
            //int numeroFactura = generarNumeroFactura();  // Usa BD para obtener el último número
            //txt_factura.setText(String.valueOf(numeroFactura));
            txt_fecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            // Bloquear campos de número y fecha
            txt_factura.setDisable(true);
            txt_fecha.setDisable(true);
            
            // Agregar una fila nueva vacía para comenzar
            //Platform.runLater(() -> this.agregarFilaNueva());
            Platform.runLater(this::agregarFilaNueva);

            // Colocar el foco inicial en el campo de documento
            txt_documento.requestFocus();

            // Evento de búsqueda de cliente por C.I./RUC al presionar ENTER
            this.txt_documento.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    this.fun_buscarCrearCliente();
                    //this.recuperarCliente(this.txt_documento.getText());
                    event.consume();
                }
            });
            
        } catch (Exception e) {
            System.out.println("Error en initialize Facturar:" + e.getMessage());
        }
    }    
    
    /**
     * Genera un nuevo número de factura basado en la lista existente.
     * @param lista
     * @return 
     */
    private int generarNumeroFactura(List<Factura> lista) {
        return lista.isEmpty() ? 1 : lista.size() + 1;
    }

    /**
     * Recupera un cliente de la base de datos basado en la cédula.
     * @param cedula El ID del cliente (cédula).
     * @return El objeto Clientes si se encuentra, o null si no.
     */
    private void recuperarCliente(String cedula) {
        try {
            if (!cedula.trim().isEmpty()) {  // Si no es vacío
                String cadenaSQL = "SELECT * FROM Cliente WHERE cli_cedula = '" + cedula + "'";  // Asegúrate de usar comillas para varchar
                Clientes objCliente = new Clientes();  // Asumiendo que Clientes tiene un constructor vacío
                ObservableList<Clientes> clientes = objCliente.getListaClientes(cadenaSQL);
                if (clientes.size() > 0) {
                    this.txt_documento.setText(clientes.get(0).getCli_cedula());
                    this.txt_nombres.setText(clientes.get(0).getCli_nombres());
                    this.txt_apellidos.setText(clientes.get(0).getCli_apellidos());
                    this.txt_telefono.setText(clientes.get(0).getCli_telefono());
                    this.txt_correo.setText(clientes.get(0).getCli_correo());
                    this.txt_direccion.setText(clientes.get(0).getCli_direccion());
                    //return clientes.get(0);  // Devuelve el cliente encontrado
                }
            } else {
                this.fun_limpiar();
            }
            //return null;  // No encontrado
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Error al recuperar cliente: " + e.getMessage());
            //return null;
        }
    }

    /**
     * Busca o crea un cliente. Si no existe, guía al usuario para llenar los datos.
     */
    public void fun_buscarCrearCliente() {
        try {
            String documento = txt_documento.getText().trim();
            if (documento.isEmpty()) {
                // Limpiar solo campos de cliente
                txt_nombres.clear();
                txt_apellidos.clear();
                txt_telefono.clear();
                txt_correo.clear();
                txt_direccion.clear();
                General.Mod_general.fun_mensajeInformacion("Ingrese un documento para buscar.");
                txt_documento.requestFocus();
            } 
            // Llama a recuperarCliente (void, carga o limpia campos)
            recuperarCliente(documento);
            // Verifica si se cargaron datos (e.g., si txt_nombres está vacío, no se encontró)
        
            if (txt_nombres.getText().trim().isEmpty()) {
                // No se encontró: guía al usuario para crear nuevo
                Mod_general.fun_mensajeInformacion("Cliente no encontrado. Llene los campos y registre.");
                txt_nombres.requestFocus();
                // Establecer navegación por ENTER
                General.Mod_general.fun_detectarTecla(txt_nombres, KeyCode.ENTER, txt_apellidos);
                General.Mod_general.fun_detectarTecla(txt_apellidos, KeyCode.ENTER, txt_telefono);
                General.Mod_general.fun_detectarTecla(txt_telefono, KeyCode.ENTER, txt_correo);
                General.Mod_general.fun_detectarTecla(txt_correo, KeyCode.ENTER, txt_direccion);

                txt_direccion.setOnKeyPressed(ev -> {
                    if (ev.getCode() == KeyCode.ENTER) {
                        if (this.fun_validar()) {
                            String estado = "A";
                            // Crear y agregar nuevo cliente a la base de datos
                            String cadenaSQL = "INSERT INTO Cliente(cli_id, cli_nombres, cli_apellidos, " +  // Usa cli_id
                                    "cli_direccion, cli_telefono, cli_correo, cli_estado) " +
                                    "VALUES ('" + this.txt_documento.getText() + "', '" + this.txt_nombres.getText() + "', " +
                                    "'" + this.txt_apellidos.getText() + "', '" + this.txt_direccion.getText() + "', " +
                                    "'" + this.txt_telefono.getText() + "', '" + this.txt_correo.getText() + "', '" + estado + "')";
                            // Ejecuta el INSERT usando BD
                            BD bd = new BD();
                            try {
                                bd.conectarBD();
                                int filas = bd.ejecutarSQL(cadenaSQL);
                                bd.desconectarBD();
                                if (filas > 0) {
                                    Mod_general.fun_mensajeInformacion("Cliente creado exitosamente.");
                                    // Opcional: Recarga los campos con el nuevo cliente
                                    recuperarCliente(this.txt_documento.getText());
                                } else {
                                    Mod_general.fun_mensajeError("Error al crear cliente.");
                                }
                            } catch (Exception ex) {
                                Mod_general.fun_mensajeError("Error al guardar cliente: " + ex.getMessage());
                            }
                            // Saltar a la tabla
                            if (!tbl_detalleFactura.getItems().isEmpty()) {
                                tbl_detalleFactura.requestFocus();
                                tbl_detalleFactura.edit(0, col_codigo);
                            }
                        } else {
                            General.Mod_general.fun_mensajeInformacion("Asegúrese de no dejar campos vacíos");
                        }
                    }
                });
            } else {
                // Se encontró y cargó: salta directamente a la tabla
                Mod_general.fun_mensajeInformacion("Cliente cargado exitosamente.");
                if (!tbl_detalleFactura.getItems().isEmpty()) {
                    tbl_detalleFactura.requestFocus();
                    tbl_detalleFactura.edit(0, col_codigo);
                }
            }
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Configura todas las columnas de la TableView con su lógica específica.
     */
    public void configurarColumnas() {
        /*tbl_detalleFactura.setEditable(true);

        // ====== CÓDIGO ======
        col_codigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProd_cod()));
        col_codigo.setCellFactory(param -> {
            TextFieldTableCell<DetalleFactura, String> cell = new TextFieldTableCell<>(new DefaultStringConverter()) {
                @Override
                public void startEdit() {
                    super.startEdit();
                    if (getGraphic() instanceof TextField) {
                        TextField textField = (TextField) getGraphic();
                        Platform.runLater(() -> {
                            textField.selectAll();
                            textField.requestFocus();
                        });
                    }
                }
            };
            return cell;
        });
        col_codigo.setOnEditCommit(event -> {
            String codigo = event.getNewValue();
            DetalleFactura det = event.getRowValue();
            Producto p = recuperarProducto(codigo);
            if (p == null) {
                General.Mod_general.fun_mensajeInformacion(Idiomas.getTexto("mensaje.productoNoEncontrado"));
                Platform.runLater(() -> tbl_detalleFactura.edit(event.getTablePosition().getRow(), col_codigo));
            } else {
                this.aplicarProductoAFila(det, p);
                tbl_detalleFactura.refresh();
                // Saltar a la columna de cantidad
                Platform.runLater(() -> tbl_detalleFactura.edit(event.getTablePosition().getRow(), col_cantidad));
            }
            this.actualizarTotales();
        });

        // ====== BOTÓN BUSCAR (Nueva funcionalidad) ======
        col_buscar.setCellFactory(col -> new TableCell<DetalleFactura, String>() {
            private final Button btn = new Button("Buscar");
            {
                btn.setOnAction(event -> {
                    Producto seleccionado = abrirProductosController();
                    if (seleccionado != null) {
                        int index = getIndex();
                        if (index >= 0 && index < getTableView().getItems().size()) {
                            DetalleFactura det = getTableView().getItems().get(index);
                            aplicarProductoAFila(det, seleccionado);
                            tbl_detalleFactura.refresh();
                            actualizarTotales();
                            tbl_detalleFactura.edit(index, col_cantidad);
                        }
                    }
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // ====== DESCRIPCIÓN ======
        col_descripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProd_nombre()));
        col_descripcion.setCellFactory(tc -> new TableCell<DetalleFactura, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setGraphic(null);
            }
        });
        col_descripcion.setEditable(false);

        // ====== CANTIDAD ======
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("det_cantidad"));  // Retorna Float
        col_cantidad.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter(1)));  // 1 decimal para cantidad
        col_cantidad.setOnEditCommit(event -> {
            try {
                DetalleFactura detalle = event.getRowValue();
                Float nuevaCantidad = event.getNewValue();  // Float directo
                if (nuevaCantidad == null || nuevaCantidad < 0) {
                    nuevaCantidad = 1.0f;
                }
                // Si quieres forzar entero en cantidad (opcional): nuevaCantidad = (float) Math.round(nuevaCantidad);
                Producto p = recuperarProducto(detalle.getProd_cod());
                if (p != null && nuevaCantidad > p.getProd_stock()) {  // Asume stock es float o int; ajusta si necesario
                    Mod_general.fun_mensajeInformacion("No hay suficiente stock. Stock disponible: " + p.getProd_stock());
                    // Revertir a old value
                    Float oldValue = event.getOldValue();
                    detalle.setDet_cantidad(oldValue != null ? oldValue : 1.0f);
                } else {
                    detalle.setDet_cantidad(nuevaCantidad);
                    this.recalcularFila(detalle);
                }
                tbl_detalleFactura.refresh();
                actualizarTotales();
                // Saltar a PVP
                Platform.runLater(() -> tbl_detalleFactura.edit(event.getTablePosition().getRow(), col_pvp));
            } catch (Exception e) {
                Mod_general.fun_mensajeError("Error editando cantidad: " + e.getMessage());
                tbl_detalleFactura.refresh();
            }
        });

        // ====== PVP ======
        col_pvp.setCellValueFactory(new PropertyValueFactory<>("prod_pvp"));  // Retorna Float
        col_pvp.setCellFactory(col -> new TableCell<DetalleFactura, Float>() {  // Tipado como Float
            private ComboBox<Float> combo;  // Combo usa Float para consistencia
            @Override
            protected void updateItem(Float item, boolean empty) {  // item como Float
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                DetalleFactura det = getTableView().getItems().get(getIndex());
                if (det == null) {
                    setGraphic(null);
                    return;
                }
                Producto p = recuperarProducto(det.getProd_cod());
                if (p == null) {
                    setGraphic(null);
                    setText(new FloatStringConverter(2).toString(item));  // Fallback seguro
                    return;
                }
                try {
                    if (combo == null) {
                        combo = new ComboBox<>();
                        // Obtén PVP como Float (con null check)
                        Float pvpValue = p.getProd_pvp_menor();
                        if (pvpValue == null) pvpValue = 0.0f;
                        combo.getItems().add(pvpValue);
                        combo.setValue(pvpValue);
                        combo.setOnAction(e -> {
                            Float nuevoPvp = combo.getValue();
                            if (nuevoPvp != null) {
                                det.setProd_pvp(nuevoPvp);  // Setter Float
                                recalcularFila(det);
                                actualizarTotales();
                                tbl_detalleFactura.refresh();
                            }
                        });

                        // ENTER para avanzar
                        combo.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ENTER) {
                                Float nuevoPvp = combo.getValue();
                                if (nuevoPvp != null) {
                                    det.setProd_pvp(nuevoPvp);
                                    recalcularFila(det);
                                    actualizarTotales();
                                    tbl_detalleFactura.refresh();
                                    
                                    if (getIndex() == getTableView().getItems().size() - 1) {
                                        agregarFilaNueva();
                                        Platform.runLater(() -> {
                                            int ultimaFila = getTableView().getItems().size() - 1;
                                            tbl_detalleFactura.edit(ultimaFila, col_codigo);
                                        });
                                    } else {
                                        Platform.runLater(() -> tbl_detalleFactura.edit(getIndex() + 1, col_codigo));
                                    }
                                }
                            }
                        });
                    } else {
                        // Actualiza con item Float (null-safe)
                        combo.setValue(item != null ? item : 0.0f);
                    }
                    setGraphic(combo);
                    setText(null);  // Oculta texto cuando hay graphic
                } catch (Exception e) {  // Captura cualquier error
                    System.err.println("Error en PVP cell: " + e.getMessage());
                    setGraphic(null);
                    setText(item != null ? new FloatStringConverter(2).toString(item) : "0.00");  // Fallback seguro
                }
            }
        });

        // ====== APLICA IVA ======
        col_aplicaIVA.setCellValueFactory(new PropertyValueFactory<>("prod_aplicaIVA"));
        col_aplicaIVA.setCellFactory(tc -> new TableCell<DetalleFactura, Boolean>() {
            private final CheckBox cb = new CheckBox();
            {
                cb.setDisable(true);  // Deshabilitado para solo lectura (evita cambios accidentales)
                cb.setStyle("-fx-opacity: 1.0;");
            }
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : cb);
                if (!empty) cb.setSelected(item != null && item);
            }
        });
        col_aplicaIVA.setEditable(false);  // No editable

        // ====== IVA ======
        col_iva.setCellValueFactory(new PropertyValueFactory<>("det_iva"));
        col_iva.setCellFactory(tc -> new TableCell<DetalleFactura, Float>() {  // Cambiado a Float
            @Override
            protected void updateItem(Float item, boolean empty) {  // item como Float
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("%.2f", item.floatValue()));  // Formato con 2 decimales, null-safe
            }
        });
        col_iva.setEditable(false);

        // ====== TOTAL ======
        col_total.setCellValueFactory(new PropertyValueFactory<>("det_total"));
        col_total.setCellFactory(tc -> new TableCell<DetalleFactura, Float>() {  // Cambiado a Float
            @Override
            protected void updateItem(Float item, boolean empty) {  // item como Float
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("%.2f", item.floatValue()));  // Formato con 2 decimales, null-safe
            }
        });
        col_total.setEditable(false);

        // ====== Atajo Ctrl+B para abrir el selector de productos (simplificado) ======
        tbl_detalleFactura.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.B) {
                int selectedIndex = tbl_detalleFactura.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    // Seleccionar fila sin editar primero
                    Producto seleccionado = abrirProductosController();
                    if (seleccionado != null) {
                        DetalleFactura det = tbl_detalleFactura.getItems().get(selectedIndex);
                        aplicarProductoAFila(det, seleccionado);
                        tbl_detalleFactura.refresh();
                        actualizarTotales();
                        tbl_detalleFactura.edit(selectedIndex, col_cantidad);  // Editar después del modal
                    }
                } else {
                    Mod_general.fun_mensajeInformacion("Seleccione una fila para buscar producto.");
                }
            }
        });*/
    }

    /**
     * Busca un producto por su código en la base de datos.
     * @param codigo El código del producto.
     * @return El objeto Producto si se encuentra, o null si no.
     */
    private Producto recuperarProducto(String codigo) {
        /*if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        BD bdInstance = new BD();  // Crea una instancia de BD
        try {
            bdInstance.conectarBD();  // Conecta a la base de datos
            String sql = "SELECT * FROM Producto WHERE pro_id = ?";  // Usa prepared statement
            bdInstance.ejecutarConsultaSql(sql, codigo);  // Asumiendo que has modificado BD para aceptar parámetros

            ResultSet rs = bdInstance.getResulSet();
            if (rs.next()) {  // Si hay resultados
                Producto producto = new Producto();
                producto.setPro_id(rs.getString("pro_id"));
                producto.setPro_nombre(rs.getString("pro_nombre"));
                producto.setPro_pecioU(rs.getFloat("pro_pecioU"));  // Corrige el nombre del campo si es necesario
                producto.setPro_pvp(rs.getFloat("pro_pvp"));
                producto.setPro_strok(rs.getFloat("pro_strok"));
                producto.setPro_aplicaIva(rs.getInt("pro_aplicaIva"));
                producto.setPro_estado(rs.getString("pro_estado"));
                return producto;  // Devuelve el producto
            }
        } catch (SQLException e) {
            General.Mod_general.fun_mensajeError("Error al buscar producto: " + e.getMessage());
        } catch (Exception e) {
            General.Mod_general.fun_mensajeError("Error general: " + e.getMessage());
        } finally {
            bdInstance.desconectarBD();  // Siempre desconecta
        }*/
        return null;  // No encontrado
    }
    
    /**
     * Aplica los datos de un producto a una fila de detalle de factura.
     * @param det : La fila de detalle
      * @param p : El producto
     */
    private void aplicarProductoAFila(DetalleFactura det, Producto p) {
        det.setProd_cod(p.getProd_cod());
        det.setProd_nombre(p.getProd_nombre());
        det.setProd_pvp(p.getProd_pvp_menor());
        det.setProd_aplicaIVA(p.isProd_aplicaIVA());
        if (det.getDet_cantidad() == 0.0f) {  // Null-safe
            det.setDet_cantidad(1.0f); // Valor por defecto como Float
        }
        this.recalcularFila(det);
    }
    
    /**
     * Recalcula el IVA y el total para una fila específica.
     * @param det : La fila de detalle
     */
    private void recalcularFila(DetalleFactura det) {
        Float cantidadF = det.getDet_cantidad();
        Float pvpF = det.getProd_pvp();
        float cantidad = cantidadF != null ? cantidadF : 0.0f;
        float pvp = pvpF != null ? pvpF : 0.0f;
        float subtotal = cantidad * pvp;
        float iva = det.isProd_aplicaIVA() ? subtotal * IVA_PORC : 0.0f;  // Sin cast innecesario
        det.setDet_iva(iva);
        det.setDet_total(subtotal + iva);
    }
    
    /**
     * Actualiza los totales de la factura en los campos de texto.
     */
    private void actualizarTotales() {
        float subtotal = 0.0f;
        float subtotal0 = 0.0f;
        float ivaTotal = 0.0f;
        float descuento = 0.0f; // Siempre 0 en esta implementación
        for (DetalleFactura d : facturaActual.getFac_detalle()) {
            // Ignorar filas vacías (sin código de producto)
            if (d.getProd_cod() == null || d.getProd_cod().trim().isEmpty()) {
                continue;
            }
            Float totalF = d.getDet_total();
            Float ivaF = d.getDet_iva();
            float total = totalF != null ? totalF : 0.0f;
            float iva = ivaF != null ? ivaF : 0.0f;
            if (d.isProd_aplicaIVA()) {
                subtotal += (total - iva); // Subtotal sin IVA
                ivaTotal += iva;
            } else {
                subtotal0 += total;
            }
        }
        float totalGeneral = subtotal + subtotal0 + ivaTotal - descuento;
        txt_subTotal.setText(String.format("%.2f", (double) subtotal));  // Cast a double solo para formato
        txt_subTotalCero.setText(String.format("%.2f", (double) subtotal0));
        txt_iva.setText(String.format("%.2f", (double) ivaTotal));
        txt_descuento.setText(String.format("%.2f", (double) descuento));
        txt_total.setText(String.format("%.2f", (double) totalGeneral));
        // Actualiza en el modelo si es necesario (asumiendo setter Float)
        facturaActual.setFac_total(totalGeneral);
    }
    
    /**
     * Agrega una nueva fila vacía al final de la tabla.
     */
    private void agregarFilaNueva() {
        DetalleFactura df = new DetalleFactura();
        df.setDet_cantidad(0.0f);  // Float explícito
        df.setProd_pvp(0.0f);
        df.setDet_iva(0.0f);
        df.setDet_total(0.0f);
        df.setProd_cod(null);  // Explícito null para evitar issues en render
        df.setProd_nombre(null);
        df.setProd_aplicaIVA(false);
        facturaActual.getFac_detalle().add(df);
        detallesObservable.add(df);
        tbl_detalleFactura.refresh();
        actualizarTotales();  // Actualizar tras agregar
    }
    
    /**
     * Abre una ventana modal para seleccionar un producto.
     * NOTA: Este método asume que existe un FXML llamado "FXMLProductos.fxml"
     * y un controlador "FXMLProductosController" con un método `getProductoSeleccionado()`.
     */
    private Producto abrirProductosController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/FXMLProductos.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane));
            stage.setTitle("Seleccionar Producto");
            stage.showAndWait();
             
            // Asumiendo controlador con getProductoSeleccionado() - placeholder
            // FXMLProductosController controller = loader.getController();
            // return controller.getProductoSeleccionado();
             
            Mod_general.fun_mensajeInformacion("Función 'Buscar Producto' no implementada completamente. Retornando null.");
            return null;
        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error abriendo selector de productos: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cierra la ventana actual.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Cancelar".
     */
    @FXML
    private void acc_btncancelar(ActionEvent event) {
        this.ap_facturar.setVisible(false);
    }
    
    /**
     * Valida los datos ingresados y registra una nueva factura en la lista.
     * @param event : Evento de acción generado al hacer clic en el botón 
     * "Grabar" o al usar el atajo Ctrl+G
     */
    @FXML
    private void acc_btngrabar(ActionEvent event) {
        try {
            if (fun_validar()) {
            // Crear cliente desde los campos
            Clientes cliente = new Clientes(
                txt_documento.getText(),
                txt_apellidos.getText(),
                txt_nombres.getText(),
                txt_direccion.getText(),
                txt_telefono.getText(),
                txt_correo.getText()
            );
            
            // Validar que hay items en tabla (no vacía)
            long itemsValidos = tbl_detalleFactura.getItems().stream()
                    .filter(d -> d.getProd_cod() != null && !d.getProd_cod().trim().isEmpty())
                    .count();
            if (itemsValidos == 0) {
                Mod_general.fun_mensajeInformacion("Agregue al menos un producto a la factura.");
                tbl_detalleFactura.requestFocus();
                return;
            }

            // Crear factura con el detalle de la tabla
            Factura factura = new Factura(
                txt_factura.getText(),
                txt_fecha.getText(),
                cliente,
                new ArrayList<>(detallesObservable),
                "A" // Activa
            );

            // Calcular totales
            factura.calcularTotales();

            // Guardar en tu repositorio o base de datos
            //BDFactura.listaFactura.add(factura);

            Mod_general.fun_mensajeInformacion("***Factura registrada con éxito***");
            fun_limpiar();
            }
        } catch (Exception e) {
            Mod_general.fun_mensajeError(e.getMessage());
        }
    }
    
    /**
     * Verifica que el campo RUC no esté vacío.
     * @return true si la validación es correcta, false en caso contrario.
     */
    private boolean fun_validar(){
        try {
            // trim para comprimir los espacios y el isEmpty es para borrar espacios vacios
            if(this.txt_documento.getText().trim().isEmpty()){
                General.Mod_general.fun_mensajeInformacion("***Registre la RUC***");
                this.txt_documento.requestFocus();
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
        try {
            // Limpiar campos de cliente (mantener documento si es necesario)
            txt_documento.clear();
            txt_nombres.clear();
            txt_apellidos.clear();
            txt_telefono.clear();
            txt_correo.clear();
            txt_direccion.clear();
             
            // Limpiar tabla (mantener una fila vacía)
            facturaActual.getFac_detalle().clear();
            detallesObservable.clear();
            agregarFilaNueva();
            // Limpiar totales
            txt_subTotal.setText("0.00");
            txt_subTotalCero.setText("0.00");
            txt_iva.setText("0.00");
            txt_descuento.setText("0.00");
            txt_total.setText("0.00");
            // Regenerar número de factura (sin cast riesgoso)
            /*int nuevoNumero = generarNumeroFactura(BDFactura.listaFactura);
            txt_factura.setText(String.valueOf(nuevoNumero));
            // Remover navegación dinámica (simplificado: se resetea al reiniciar listeners)
            txt_direccion.setOnKeyPressed(null);  // Limpiar listener temporal
            */ // Restablecer foco
            txt_documento.requestFocus();
            
            actualizarTotales();  // Asegurar totales en cero
        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error al limpiar formulario: " + e.getMessage());
        }
    }
    
    /**
     * Imprime la factura actual (placeholder para implementación).
     * @param event Evento de acción
     */
    @FXML
    private void acc_btnimprimir(ActionEvent event) {
        /*
        try {
            if (facturaActual.getFac_numero() == null || facturaActual.getFac_numero().trim().isEmpty()) {
                Mod_general.fun_mensajeInformacion("No hay factura para imprimir. Registre una primero.");
                return;
            }
            // TODO: Implementar impresión (e.g., usando JavaFX Print API o JasperReports)
            Mod_general.fun_mensajeInformacion("Función de impresión no implementada. Factura #" + facturaActual.getFac_numero());
        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error al imprimir: " + e.getMessage());
        }
        */
    }
    
    /**
     * Anula la factura actual (placeholder para implementación).
     * @param event Evento de acción
     */
    @FXML
    private void acc_btnanular(ActionEvent event) {
        /*
        try {
            if (facturaActual.getFac_numero() == null || facturaActual.getFac_numero().trim().isEmpty()) {
                Mod_general.fun_mensajeInformacion("No hay factura para anular. Registre una primero.");
                return;
            }
            // TODO: Implementar anulación (e.g., cambiar estado a "I" en BD y actualizar stock)
            facturaActual.setFac_estado("I");  // Asumiendo setter en Factura
            Mod_general.fun_mensajeInformacion("Factura #" + facturaActual.getFac_numero() + " anulada (placeholder).");
            fun_limpiar();
        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error al anular factura: " + e.getMessage());
        }
        */
    }
    
    /**
     * Aplica estilos CSS a los componentes del formulario.
     */
    private void aplicarEstilos(){
        // ESTILO
        ap_facturar.getStyleClass().add("fondo");
        pane_superior.getStyleClass().add("anchorPaneSuperior");
        pane_medio.getStyleClass().add("anchorPaneMedio");
        
        btn_grabar.getStyleClass().add("btnGrabar");
        btn_cancelar.getStyleClass().add("btnCancelar");
        btn_anular.getStyleClass().add("btnAnular");
        btn_imprimir.getStyleClass().add("btnImprimir");
        
        txt_factura.getStyleClass().add("text-field");
        txt_fecha.getStyleClass().add("text-field");
        txt_documento.getStyleClass().add("text-field");
        txt_nombres.getStyleClass().add("text-field");
        txt_apellidos.getStyleClass().add("text-field");
        txt_telefono.getStyleClass().add("text-field");
        txt_correo.getStyleClass().add("text-field");
        txt_direccion.getStyleClass().add("text-field");
        txt_subTotal.getStyleClass().add("text-field");
        txt_subTotalCero.getStyleClass().add("text-field");
        txt_iva.getStyleClass().add("text-field");
        txt_descuento.getStyleClass().add("text-field");
        txt_total.getStyleClass().add("text-field");
        
        tbl_detalleFactura.getStyleClass().add("table-view");
    }
    
    /**
     * Aplica traducciones a los componentes del formulario usando Idiomas.
     */
    private void aplicarTraduccion() {
        text_factura.setText(Idiomas.getTexto("texto.factura"));
        text_fecha.setText(Idiomas.getTexto("texto.fecha"));
        text_ciruc.setText(Idiomas.getTexto("texto.ciruc"));
        text_nombres.setText(Idiomas.getTexto("texto.nombres"));
        text_apellidos.setText(Idiomas.getTexto("texto.apellido"));
        text_telefono.setText(Idiomas.getTexto("texto.telefono"));
        text_correo.setText(Idiomas.getTexto("texto.correo"));
        text_direccion.setText(Idiomas.getTexto("texto.direccion"));
        text_subTotal.setText(Idiomas.getTexto("texto.subTotal"));
        text_subTotalCero.setText(Idiomas.getTexto("texto.SubTotalo"));
        text_iva.setText(Idiomas.getTexto("texto.iva"));
        text_descuento.setText(Idiomas.getTexto("texto.descuento"));
        text_total.setText(Idiomas.getTexto("texto.total"));
        
        col_codigo.setText(Idiomas.getTexto("columna.codigo"));
        col_descripcion.setText(Idiomas.getTexto("columna.descripcion"));
        col_cantidad.setText(Idiomas.getTexto("columna.cantidad"));
        col_pvp.setText(Idiomas.getTexto("columna.pvp"));
        col_aplicaIVA.setText(Idiomas.getTexto("columna.aplicaIVA"));
        col_iva.setText(Idiomas.getTexto("columna.iva"));
        col_total.setText(Idiomas.getTexto("columna.total"));
        tbl_detalleFactura.setPlaceholder(new Label(Idiomas.getTexto("tabla.registros")));
        
        btn_grabar.setText(Idiomas.getTexto("boton.grabar"));
        btn_cancelar.setText(Idiomas.getTexto("boton.cancelar"));
        btn_anular.setText(Idiomas.getTexto("boton.anular"));
        btn_imprimir.setText(Idiomas.getTexto("boton.imprimir"));
    }
    
    /**
      * Método auxiliar para obtener texto traducido con fallback (agregado para robustez).
      * @param clave La clave de traducción
      * @param fallback Texto por defecto si falla
      * @return El texto traducido o fallback
      */
     /*private String getTextoSeguro(String clave, String fallback) {
         try {
             String texto = Idiomas.getTexto(clave);
             return (texto != null && !texto.trim().isEmpty()) ? texto : fallback;
         } catch (Exception e) {
             return fallback;
         }
     }*/
    
    /**
     * Custom converter para campos Float (cantidad y PVP).
     * Maneja conversión a/desde String, con validación básica y null-safe.
     */
    private class FloatStringConverter extends StringConverter<Float> {
        private final int decimales;  // Para formato (e.g., 2 para PVP, 1 para cantidad)
    
        public FloatStringConverter(int decimales) {
           this.decimales = decimales;
        }
        @Override
        public String toString(Float value) {
            if (value == null) return String.format("%." + decimales + "f", 0.0f);  // Null-safe: retorna "0.0" o "0.00"
            try {
                // Formato con decimales (e.g., "%.2f" para PVP)
                return String.format("%." + decimales + "f", value.floatValue());
            } catch (Exception e) {
                System.err.println("Error convirtiendo Float a String: " + e.getMessage());
                return String.format("%." + decimales + "f", 0.0f);  // Fallback
            }
        }
        @Override
        public Float fromString(String string) {
            if (string == null || string.trim().isEmpty()) return 0.0f;
            try {
                float num = Float.parseFloat(string.trim());
                if (num < 0) {
                    Mod_general.fun_mensajeError("Valor no puede ser negativo.");
                    return 0.0f;
                }
                return num;
            } catch (NumberFormatException e) {
               Mod_general.fun_mensajeError("Valor inválido: Use números decimales (e.g., 1.5).");
               return 0.0f;  // Default
            }
        }
    }
}

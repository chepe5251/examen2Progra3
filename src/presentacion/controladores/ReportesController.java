package presentacion.controladores;

import entidades.Acceso;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logicaNegocio.AccesoService;
import presentacion.util.EstilosUI;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportesController {

    private final AccesoService service = new AccesoService();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

    private TextField campoBusqueda;
    private TableView<Acceso> tabla;
    private final ObservableList<Acceso> datos = FXCollections.observableArrayList();
    private Label labelTiempoTotal;
    private Label labelTotalRegistros;

    public Node getView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox contenido = new VBox(28);
        contenido.setPadding(new Insets(36));
        contenido.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        contenido.getChildren().addAll(
            construirEncabezado(),
            construirBusqueda(),
            construirResumen(),
            construirTabla()
        );

        scroll.setContent(contenido);
        return scroll;
    }

    private Node construirEncabezado() {
        VBox caja = new VBox(6);
        Label titulo = new Label("Reportes de acceso");
        titulo.setStyle(EstilosUI.titulo());
        Label subtitulo = new Label("Consulta historial por usuario y el tiempo acumulado dentro del laboratorio.");
        subtitulo.setStyle(EstilosUI.subtitulo());
        caja.getChildren().addAll(titulo, subtitulo);
        return caja;
    }

    private Node construirBusqueda() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(26));
        EstilosUI.aplicarHoverTarjeta(card);

        Label titulo = new Label("Buscar por usuario");
        titulo.setStyle(EstilosUI.tituloSeccion());

        HBox fila = new HBox(12);
        fila.setAlignment(Pos.CENTER_LEFT);

        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Ingresa el ID del usuario  (Ej: U001)");
        campoBusqueda.setPrefWidth(320);
        EstilosUI.aplicarFocus(campoBusqueda);
        campoBusqueda.setOnAction(e -> buscar());

        Button btnBuscar = new Button("Buscar historial");
        EstilosUI.aplicarHover(btnBuscar, EstilosUI.PRIMARIO, EstilosUI.PRIMARIO_DARK);
        btnBuscar.setOnAction(e -> buscar());

        Button btnLimpiar = new Button("Limpiar");
        EstilosUI.aplicarHoverOutline(btnLimpiar, EstilosUI.TEXTO_SUAVE, EstilosUI.PRIMARIO_DARK);
        btnLimpiar.setOnAction(e -> limpiar());

        fila.getChildren().addAll(campoBusqueda, btnBuscar, btnLimpiar);
        card.getChildren().addAll(titulo, fila);
        return card;
    }

    private Node construirResumen() {
        HBox fila = new HBox(18);
        fila.getChildren().addAll(
            crearResumen("Registros encontrados", "Resultado de la búsqueda", true),
            crearResumen("Tiempo acumulado", "Total con accesos cerrados", false)
        );
        return fila;
    }

    private Node crearResumen(String titulo, String detalle, boolean esTotal) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(22));
        EstilosUI.aplicarHoverTarjeta(card);

        Label tituloLabel = new Label(titulo.toUpperCase());
        tituloLabel.setStyle(EstilosUI.kpiEtiqueta());

        Label detalleLabel = new Label(detalle);
        detalleLabel.setStyle(EstilosUI.subtitulo());

        Label valor = new Label("—");
        valor.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        valor.setTextFill(javafx.scene.paint.Color.web(esTotal ? EstilosUI.PRIMARIO : EstilosUI.EXITO));

        if (esTotal) {
            labelTotalRegistros = valor;
        } else {
            labelTiempoTotal = valor;
        }

        card.getChildren().addAll(tituloLabel, valor, detalleLabel);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private Node construirTabla() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(26));
        EstilosUI.aplicarHoverTarjeta(card);

        Label titulo = new Label("Historial de accesos");
        titulo.setStyle(EstilosUI.tituloSeccion());

        tabla = new TableView<>(datos);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tabla.setPrefHeight(430);
        tabla.setPlaceholder(new Label("Busca un usuario para ver su historial de accesos."));
        EstilosUI.estilizarTabla(tabla);

        TableColumn<Acceso, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setPrefWidth(110);
        colUsuario.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdUsuario()));

        TableColumn<Acceso, String> colEntrada = new TableColumn<>("Entrada");
        colEntrada.setPrefWidth(185);
        colEntrada.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getFechaHoraEntrada().format(FMT))
        );

        TableColumn<Acceso, String> colSalida = new TableColumn<>("Salida");
        colSalida.setPrefWidth(185);
        colSalida.setCellValueFactory(d -> {
            var salida = d.getValue().getFechaHoraSalida();
            return new SimpleStringProperty(salida != null ? salida.format(FMT) : "Aún dentro");
        });

        TableColumn<Acceso, String> colDuracion = new TableColumn<>("Duración");
        colDuracion.setPrefWidth(140);
        colDuracion.setCellValueFactory(d -> {
            Acceso a = d.getValue();
            if (a.getFechaHoraSalida() == null) {
                return new SimpleStringProperty("En curso");
            }
            long mins = Duration.between(a.getFechaHoraEntrada(), a.getFechaHoraSalida()).toMinutes();
            return new SimpleStringProperty(mins / 60 + " h  " + mins % 60 + " min");
        });

        TableColumn<Acceso, String> colEstado = new TableColumn<>("Estado");
        colEstado.setPrefWidth(110);
        colEstado.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getFechaHoraSalida() == null ? "Activo" : "Completado")
        );
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setGraphic(null);
                    return;
                }
                Label badge = new Label(estado);
                badge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10.5));
                String color = estado.equals("Activo") ? EstilosUI.EXITO : EstilosUI.TEXTO_SUAVE;
                badge.setStyle(EstilosUI.badgeSuave(color));
                setGraphic(badge);
            }
        });

        tabla.getColumns().add(colUsuario);
        tabla.getColumns().add(colEntrada);
        tabla.getColumns().add(colSalida);
        tabla.getColumns().add(colDuracion);
        tabla.getColumns().add(colEstado);
        card.getChildren().addAll(titulo, tabla);
        return card;
    }

    private void buscar() {
        String id = campoBusqueda.getText().trim();
        if (id.isEmpty()) {
            EstilosUI.error("Campo vacío", "Ingresa el ID del usuario para buscar su historial.");
            return;
        }
        try {
            List<Acceso> historial = service.historialPorUsuario(id);
            datos.setAll(historial);

            labelTotalRegistros.setText(String.valueOf(historial.size()));

            long minutos = service.calcularTiempoTotalEnMinutos(id);
            long horas = minutos / 60;
            long mins = minutos % 60;
            labelTiempoTotal.setText(horas + " h  " + mins + " min");

        } catch (Exception ex) {
            datos.clear();
            resetResumen();
            EstilosUI.error("Error en la búsqueda", ex.getMessage());
        }
    }

    private void limpiar() {
        campoBusqueda.clear();
        datos.clear();
        resetResumen();
    }

    private void resetResumen() {
        labelTotalRegistros.setText("—");
        labelTiempoTotal.setText("—");
    }
}
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
import javafx.scene.paint.Color;
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

        VBox contenido = new VBox(24);
        contenido.setPadding(new Insets(32));
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

    // ── Encabezado ────────────────────────────────────────────────────────────

    private Node construirEncabezado() {
        VBox caja = new VBox(4);
        Label titulo    = new Label("📋  Reportes de Acceso");
        titulo.setStyle(EstilosUI.titulo());
        Label subtitulo = new Label("Consulta el historial y el tiempo acumulado de cada usuario en el laboratorio");
        subtitulo.setStyle(EstilosUI.subtitulo());
        caja.getChildren().addAll(titulo, subtitulo);
        return caja;
    }

    // ── Formulario de búsqueda ────────────────────────────────────────────────

    private Node construirBusqueda() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(EstilosUI.TARJETA);

        Label titulo = new Label("Buscar por Usuario");
        titulo.setStyle(EstilosUI.tituloSeccion());

        HBox fila = new HBox(12);
        fila.setAlignment(Pos.CENTER_LEFT);

        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Ingresa el ID del usuario  (Ej: U001)");
        campoBusqueda.setPrefWidth(300);
        campoBusqueda.setStyle(EstilosUI.campo());
        campoBusqueda.setOnAction(e -> buscar());

        Button btnBuscar = new Button("Buscar Historial");
        btnBuscar.setStyle(EstilosUI.boton(EstilosUI.PRIMARIO));
        EstilosUI.aplicarHover(btnBuscar, EstilosUI.PRIMARIO, "#2563eb");
        btnBuscar.setOnAction(e -> buscar());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle(EstilosUI.botonOutline(EstilosUI.TEXTO_SUAVE));
        btnLimpiar.setOnAction(e -> limpiar());

        fila.getChildren().addAll(campoBusqueda, btnBuscar, btnLimpiar);
        card.getChildren().addAll(titulo, fila);
        return card;
    }

    // ── Tarjetas de resumen ───────────────────────────────────────────────────

    private Node construirResumen() {
        HBox fila = new HBox(16);

        // Tarjeta: total registros
        VBox card1 = new VBox(8);
        card1.setPadding(new Insets(20));
        card1.setStyle(EstilosUI.TARJETA);
        Label etiq1 = new Label("Total de accesos encontrados");
        etiq1.setStyle(EstilosUI.subtitulo());
        labelTotalRegistros = new Label("—");
        labelTotalRegistros.setFont(Font.font("System", FontWeight.BOLD, 30));
        labelTotalRegistros.setTextFill(Color.web(EstilosUI.PRIMARIO));
        card1.getChildren().addAll(etiq1, labelTotalRegistros);

        // Tarjeta: tiempo total
        VBox card2 = new VBox(8);
        card2.setPadding(new Insets(20));
        card2.setStyle(EstilosUI.TARJETA);
        Label etiq2 = new Label("Tiempo total dentro del laboratorio");
        etiq2.setStyle(EstilosUI.subtitulo());
        labelTiempoTotal = new Label("—");
        labelTiempoTotal.setFont(Font.font("System", FontWeight.BOLD, 30));
        labelTiempoTotal.setTextFill(Color.web(EstilosUI.EXITO));
        card2.getChildren().addAll(etiq2, labelTiempoTotal);

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);

        fila.getChildren().addAll(card1, card2);
        return fila;
    }

    // ── Tabla de historial ────────────────────────────────────────────────────

    private Node construirTabla() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(EstilosUI.TARJETA);

        Label titulo = new Label("Historial de Accesos");
        titulo.setStyle(EstilosUI.tituloSeccion());

        tabla = new TableView<>(datos);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPrefHeight(380);
        tabla.setStyle("-fx-background-color: transparent;");
        tabla.setPlaceholder(new Label("Busca un usuario para ver su historial de accesos."));

        // Columna usuario
        TableColumn<Acceso, String> colUsuario = new TableColumn<>("#  Usuario");
        colUsuario.setPrefWidth(110);
        colUsuario.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdUsuario()));

        // Columna entrada
        TableColumn<Acceso, String> colEntrada = new TableColumn<>("Fecha de Entrada");
        colEntrada.setPrefWidth(185);
        colEntrada.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getFechaHoraEntrada().format(FMT))
        );

        // Columna salida
        TableColumn<Acceso, String> colSalida = new TableColumn<>("Fecha de Salida");
        colSalida.setPrefWidth(185);
        colSalida.setCellValueFactory(d -> {
            var salida = d.getValue().getFechaHoraSalida();
            return new SimpleStringProperty(salida != null ? salida.format(FMT) : "—  Aún dentro");
        });

        // Columna duración
        TableColumn<Acceso, String> colDuracion = new TableColumn<>("Duración");
        colDuracion.setPrefWidth(130);
        colDuracion.setCellValueFactory(d -> {
            Acceso a = d.getValue();
            if (a.getFechaHoraSalida() == null) return new SimpleStringProperty("En curso");
            long mins = Duration.between(a.getFechaHoraEntrada(), a.getFechaHoraSalida()).toMinutes();
            return new SimpleStringProperty(mins / 60 + " h  " + mins % 60 + " min");
        });

        // Columna estado con badge
        TableColumn<Acceso, String> colEstado = new TableColumn<>("Estado");
        colEstado.setPrefWidth(110);
        colEstado.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getFechaHoraSalida() == null ? "Activo" : "Completado")
        );
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) { setGraphic(null); return; }
                Label badge = new Label(estado);
                badge.setFont(Font.font("System", FontWeight.BOLD, 11));
                badge.setTextFill(Color.WHITE);
                String color = estado.equals("Activo") ? EstilosUI.EXITO : EstilosUI.TEXTO_SUAVE;
                badge.setStyle(
                    "-fx-background-color: " + color + ";" +
                    "-fx-background-radius: 20;" +
                    "-fx-padding: 3 10 3 10;"
                );
                setGraphic(badge);
            }
        });

        tabla.getColumns().addAll(colUsuario, colEntrada, colSalida, colDuracion, colEstado);
        card.getChildren().addAll(titulo, tabla);
        return card;
    }

    // ── Acciones ──────────────────────────────────────────────────────────────

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
            long horas   = minutos / 60;
            long mins    = minutos % 60;
            labelTiempoTotal.setText(horas + " h  " + mins + " min");

        } catch (Exception ex) {
            EstilosUI.error("Error en la búsqueda", ex.getMessage());
        }
    }

    private void limpiar() {
        campoBusqueda.clear();
        datos.clear();
        labelTotalRegistros.setText("—");
        labelTiempoTotal.setText("—");
    }
}

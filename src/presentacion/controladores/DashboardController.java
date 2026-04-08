package presentacion.controladores;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logicaNegocio.AccesoService;
import logicaNegocio.UsuarioService;
import presentacion.util.EstilosUI;

public class DashboardController {

    private final UsuarioService usuarioService = new UsuarioService();
    private final AccesoService accesoService = new AccesoService();

    public Node getView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox contenido = new VBox(28);
        contenido.setPadding(new Insets(36));
        contenido.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        contenido.getChildren().addAll(
            construirEncabezado(),
            construirTarjetasMetrica(),
            construirInfoSistema(),
            construirReglasDePolitica()
        );

        scroll.setContent(contenido);
        return scroll;
    }

    private Node construirEncabezado() {
        VBox caja = new VBox(6);
        Label titulo = new Label("Dashboard");
        titulo.setStyle(EstilosUI.titulo());

        Label subtitulo = new Label("Vista general del sistema y del estado actual del laboratorio.");
        subtitulo.setStyle(EstilosUI.subtitulo());
        caja.getChildren().addAll(titulo, subtitulo);
        return caja;
    }

    private Node construirTarjetasMetrica() {
        int totalUsuarios = 0;
        long dentroLab = 0;
        int totalAccesos = 0;

        try {
            totalUsuarios = usuarioService.listar().size();
        } catch (Exception ignored) {
        }
        try {
            dentroLab = accesoService.contarDentroDelLaboratorio();
        } catch (Exception ignored) {
        }
        try {
            totalAccesos = accesoService.listarTodosLosAccesos().size();
        } catch (Exception ignored) {
        }

        HBox fila = new HBox(18);
        fila.getChildren().addAll(
            crearTarjeta("Usuarios registrados", String.valueOf(totalUsuarios), "Base activa del sistema", EstilosUI.PRIMARIO),
            crearTarjeta("Dentro del laboratorio", String.valueOf(dentroLab), "Personas con entrada abierta", EstilosUI.EXITO),
            crearTarjeta("Accesos acumulados", String.valueOf(totalAccesos), "Historial total registrado", EstilosUI.ADVERTENCIA)
        );

        for (Node tarjeta : fila.getChildren()) {
            HBox.setHgrow(tarjeta, Priority.ALWAYS);
        }
        return fila;
    }

    private Node crearTarjeta(String etiqueta, String valor, String detalle, String color) {
        VBox tarjeta = new VBox(16);
        tarjeta.setPadding(new Insets(26));
        EstilosUI.aplicarHoverTarjeta(tarjeta);

        Rectangle acento = new Rectangle(54, 4);
        acento.setFill(Color.web(color));
        acento.setArcWidth(6);
        acento.setArcHeight(6);

        Label etiquetaLabel = new Label(etiqueta.toUpperCase());
        etiquetaLabel.setStyle(EstilosUI.kpiEtiqueta());

        Label valorLabel = new Label(valor);
        valorLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 34));
        valorLabel.setTextFill(Color.web(EstilosUI.TEXTO));

        Label detalleLabel = new Label(detalle);
        detalleLabel.setStyle(EstilosUI.subtitulo());
        detalleLabel.setWrapText(true);

        tarjeta.getChildren().addAll(acento, etiquetaLabel, valorLabel, detalleLabel);
        return tarjeta;
    }

    private Node construirInfoSistema() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(26));
        EstilosUI.aplicarHoverTarjeta(card);

        Label titulo = new Label("Información del sistema");
        titulo.setStyle(EstilosUI.tituloSeccion());

        String[][] filas = {
            {"Arquitectura", "Capas: Entidades · AccesoDatos · LogicaNegocio · Presentacion"},
            {"Persistencia", "Archivos locales: usuarios.txt y accesos.txt"},
            {"Roles", "Estudiante · Docente"},
            {"Tecnologia", "Java 17+ · JavaFX"},
            {"Repositorio", "github.com/chepe5251/examen2Progra3"}
        };

        GridPane grid = new GridPane();
        grid.setHgap(28);
        grid.setVgap(14);

        for (int i = 0; i < filas.length; i++) {
            Label clave = new Label(filas[i][0].toUpperCase());
            clave.setStyle(EstilosUI.kpiEtiqueta());

            Label valor = new Label(filas[i][1]);
            valor.setStyle(EstilosUI.subtitulo());
            valor.setWrapText(true);

            grid.add(clave, 0, i);
            grid.add(valor, 1, i);
        }

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(150);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        card.getChildren().addAll(titulo, grid);
        return card;
    }

    private Node construirReglasDePolitica() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(26));
        EstilosUI.aplicarHoverTarjeta(card);

        Label titulo = new Label("Reglas de acceso");
        titulo.setStyle(EstilosUI.tituloSeccion());

        String[] reglas = {
            "Un usuario solo puede registrar entrada si no tiene una entrada activa previa.",
            "No se puede registrar salida sin una entrada activa registrada.",
            "Los IDs de usuario son únicos y no se permiten duplicados.",
            "El tiempo en laboratorio solo considera accesos con entrada y salida registradas."
        };

        VBox lista = new VBox(12);
        for (String regla : reglas) {
            HBox fila = new HBox(10);
            fila.setAlignment(Pos.TOP_LEFT);

            Region punto = new Region();
            punto.setPrefSize(8, 8);
            punto.setMaxSize(8, 8);
            punto.setStyle(
                "-fx-background-color: " + EstilosUI.PRIMARIO + ";" +
                "-fx-background-radius: 999;"
            );
            VBox.setMargin(punto, new Insets(6, 0, 0, 0));

            Label texto = new Label(regla);
            texto.setWrapText(true);
            texto.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-text-fill: " + EstilosUI.TEXTO + ";");

            fila.getChildren().addAll(punto, texto);
            lista.getChildren().add(fila);
        }

        card.getChildren().addAll(titulo, lista);
        return card;
    }
}
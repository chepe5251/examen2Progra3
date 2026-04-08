package presentacion.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class EstilosUI {

    private static final String FUENTE = "'Segoe UI'";

    // ── Paleta ────────────────────────────────────────────────────────────────
    public static final String PRIMARIO      = "#4c6fb3";
    public static final String PRIMARIO_DARK = "#3c5a93";
    public static final String EXITO         = "#2f8f72";
    public static final String EXITO_DARK    = "#277560";
    public static final String PELIGRO       = "#c65c5c";
    public static final String PELIGRO_DARK  = "#a84b4b";
    public static final String ADVERTENCIA   = "#d28b2c";
    public static final String SIDEBAR       = "#111827";
    public static final String SIDEBAR_HOVER = "#1b2435";
    public static final String FONDO         = "#f6f7fb";
    public static final String SUPERFICIE    = "#ffffff";
    public static final String SUPERFICIE_ALT = "#f8fafc";
    public static final String FILA_ALT      = "#f3f6fb";
    public static final String HOVER_SUAVE   = "#eef3fb";
    public static final String TEXTO         = "#0f172a";
    public static final String TEXTO_SUAVE   = "#667085";
    public static final String TEXTO_CLARO   = "#e5edf8";
    public static final String BORDE         = "#d9e2ec";
    public static final String BORDE_FOCUS   = "#afc3e6";

    // ── Contenedores ──────────────────────────────────────────────────────────
    public static final String TARJETA =
        "-fx-background-color: " + SUPERFICIE + ";" +
        "-fx-border-color: " + BORDE + ";" +
        "-fx-border-width: 1;" +
        "-fx-border-radius: 18;" +
        "-fx-background-radius: 18;" +
        "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.05), 18, 0, 0, 6);";

    public static final String TARJETA_HOVER =
        "-fx-background-color: " + SUPERFICIE + ";" +
        "-fx-border-color: " + BORDE_FOCUS + ";" +
        "-fx-border-width: 1;" +
        "-fx-border-radius: 18;" +
        "-fx-background-radius: 18;" +
        "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.09), 22, 0, 0, 9);";

    // ── Botones ───────────────────────────────────────────────────────────────
    public static String boton(String hex) {
        return "-fx-background-color: " + hex + ";" +
               "-fx-text-fill: white;" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 12.5px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 10;" +
               "-fx-padding: 10 18 10 18;" +
               "-fx-cursor: hand;" +
               "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.10), 8, 0, 0, 2);";
    }

    public static String botonOutline(String hex) {
        return "-fx-background-color: " + SUPERFICIE + ";" +
               "-fx-border-color: " + hex + ";" +
               "-fx-border-width: 1.2;" +
               "-fx-border-radius: 10;" +
               "-fx-background-radius: 10;" +
               "-fx-text-fill: " + hex + ";" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 12.5px;" +
               "-fx-font-weight: bold;" +
               "-fx-padding: 9 16 9 16;" +
               "-fx-cursor: hand;";
    }

    public static void aplicarHover(Button btn, String normal, String hover) {
        btn.setStyle(boton(normal));
        btn.setOnMouseEntered(e -> btn.setStyle(boton(hover)));
        btn.setOnMouseExited (e -> btn.setStyle(boton(normal)));
    }

    public static void aplicarHoverOutline(Button btn, String normal, String hoverFill) {
        btn.setStyle(botonOutline(normal));
        btn.setOnMouseEntered(e -> btn.setStyle(boton(hoverFill)));
        btn.setOnMouseExited (e -> btn.setStyle(botonOutline(normal)));
    }

    // ── Inputs ────────────────────────────────────────────────────────────────
    public static String campo() {
        return "-fx-background-color: " + SUPERFICIE + ";" +
               "-fx-border-color: " + BORDE + ";" +
               "-fx-border-width: 1.2;" +
               "-fx-border-radius: 10;" +
               "-fx-background-radius: 10;" +
               "-fx-padding: 10 14 10 14;" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";" +
               "-fx-prompt-text-fill: " + TEXTO_SUAVE + ";";
    }

    public static String campoFocused() {
        return "-fx-background-color: " + SUPERFICIE + ";" +
               "-fx-border-color: " + BORDE_FOCUS + ";" +
               "-fx-border-width: 1.6;" +
               "-fx-border-radius: 10;" +
               "-fx-background-radius: 10;" +
               "-fx-padding: 10 14 10 14;" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";" +
               "-fx-prompt-text-fill: " + TEXTO_SUAVE + ";" +
               "-fx-effect: dropshadow(gaussian, rgba(76,111,179,0.14), 10, 0, 0, 0);";
    }

    public static String campoError() {
        return "-fx-background-color: #fff8f8;" +
               "-fx-border-color: " + PELIGRO + ";" +
               "-fx-border-width: 1.4;" +
               "-fx-border-radius: 10;" +
               "-fx-background-radius: 10;" +
               "-fx-padding: 10 14 10 14;" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";";
    }

    public static void aplicarFocus(TextField campo) {
        campo.setStyle(campo());
        campo.focusedProperty().addListener((obs, wasF, isF) ->
            campo.setStyle(isF ? campoFocused() : campo())
        );
    }

    // ── Textos ────────────────────────────────────────────────────────────────
    public static String titulo() {
        return "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 27px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + TEXTO + ";";
    }

    public static String subtitulo() {
        return "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 12.5px;" +
               "-fx-text-fill: " + TEXTO_SUAVE + ";";
    }

    public static String tituloSeccion() {
        return "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 17px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + TEXTO + ";";
    }

    public static String etiquetaForm() {
        return "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 11px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + TEXTO_SUAVE + ";";
    }

    public static String kpiEtiqueta() {
        return "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 11px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + TEXTO_SUAVE + ";";
    }

    public static String badgeSuave(String color) {
        return "-fx-background-color: " + color + "16;" +
               "-fx-border-color: " + color + "44;" +
               "-fx-border-width: 1;" +
               "-fx-background-radius: 999;" +
               "-fx-border-radius: 999;" +
               "-fx-padding: 3 9 3 9;" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 10.5px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + color + ";";
    }

    // ── Tarjetas y tablas ─────────────────────────────────────────────────────
    public static void aplicarHoverTarjeta(Region card) {
        card.setStyle(TARJETA);
        card.setOnMouseEntered(e -> card.setStyle(TARJETA_HOVER));
        card.setOnMouseExited (e -> card.setStyle(TARJETA));
    }

    public static <T> void estilizarTabla(TableView<T> tabla) {
        tabla.setStyle(
            "-fx-background-color: " + SUPERFICIE + ";" +
            "-fx-control-inner-background: " + SUPERFICIE + ";" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: " + BORDE + ";" +
            "-fx-border-radius: 18;" +
            "-fx-padding: 8;" +
            "-fx-table-cell-border-color: transparent;" +
            "-fx-selection-bar: " + PRIMARIO + "22;" +
            "-fx-selection-bar-non-focused: " + PRIMARIO + "14;" +
            "-fx-font-family: " + FUENTE + ";"
        );
        tabla.setFixedCellSize(46);
        tabla.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            Runnable actualizar = () -> aplicarEstiloFila(row);
            row.itemProperty().addListener((obs, oldItem, newItem) -> actualizar.run());
            row.selectedProperty().addListener((obs, oldSel, newSel) -> actualizar.run());
            row.hoverProperty().addListener((obs, oldHover, newHover) -> actualizar.run());
            row.indexProperty().addListener((obs, oldIndex, newIndex) -> actualizar.run());
            return row;
        });

        tabla.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> estilizarEncabezadosTabla(tabla));
            }
        });
        Platform.runLater(() -> estilizarEncabezadosTabla(tabla));
    }

    private static <T> void aplicarEstiloFila(TableRow<T> row) {
        if (row.isEmpty()) {
            row.setStyle("-fx-background-color: transparent;");
            return;
        }

        String base = row.getIndex() % 2 == 0 ? SUPERFICIE : FILA_ALT;
        if (row.isSelected()) {
            row.setStyle(
                "-fx-background-color: " + PRIMARIO + "16;" +
                "-fx-background-insets: 0 8 0 8;" +
                "-fx-background-radius: 10;"
            );
        } else if (row.isHover()) {
            row.setStyle(
                "-fx-background-color: " + HOVER_SUAVE + ";" +
                "-fx-background-insets: 0 8 0 8;" +
                "-fx-background-radius: 10;"
            );
        } else {
            row.setStyle(
                "-fx-background-color: " + base + ";" +
                "-fx-background-insets: 0 8 0 8;" +
                "-fx-background-radius: 10;"
            );
        }
    }

    private static void estilizarEncabezadosTabla(TableView<?> tabla) {
        Node fondoHeader = tabla.lookup(".column-header-background");
        if (fondoHeader != null) {
            fondoHeader.setStyle(
                "-fx-background-color: " + SUPERFICIE_ALT + ";" +
                "-fx-background-radius: 12 12 0 0;" +
                "-fx-background-insets: 0 8 0 8;"
            );
        }

        Node filler = tabla.lookup(".filler");
        if (filler != null) {
            filler.setStyle("-fx-background-color: " + SUPERFICIE_ALT + ";");
        }

        for (Node node : tabla.lookupAll(".column-header")) {
            node.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent transparent " + BORDE + " transparent;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-padding: 0 0 10 0;"
            );
        }

        for (Node node : tabla.lookupAll(".column-header .label")) {
            node.setStyle(
                "-fx-text-fill: " + TEXTO + ";" +
                "-fx-font-family: " + FUENTE + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
            );
        }
    }

    // ── Notificaciones inline ─────────────────────────────────────────────────
    public static String notaExito() {
        return notaBase("#effaf5", "#b7e6d4", "#1c6b54");
    }

    public static String notaError() {
        return notaBase("#fff7f7", "#efc2c2", "#934343");
    }

    public static String notaAdvertencia() {
        return notaBase("#fff8eb", "#edd3a3", "#8a6324");
    }

    private static String notaBase(String fondo, String borde, String texto) {
        return "-fx-background-color: " + fondo + ";" +
               "-fx-border-color: " + borde + ";" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 12;" +
               "-fx-background-radius: 12;" +
               "-fx-padding: 11 14 11 14;" +
               "-fx-font-family: " + FUENTE + ";" +
               "-fx-font-size: 12.5px;" +
               "-fx-text-fill: " + texto + ";";
    }

    // ── Alertas ───────────────────────────────────────────────────────────────
    public static void exito(String titulo, String msg) {
        mostrar(Alert.AlertType.INFORMATION, titulo, msg);
    }

    public static void error(String titulo, String msg) {
        mostrar(Alert.AlertType.ERROR, titulo, msg);
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle(
            "-fx-background-color: white;" +
            "-fx-font-family: " + FUENTE + ";" +
            "-fx-font-size: 13px;"
        );
        alert.showAndWait();
    }
}
package presentacion.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class EstilosUI {

    // ── Paleta ────────────────────────────────────────────────────────────────
    public static final String PRIMARIO      = "#3b82f6";
    public static final String PRIMARIO_DARK = "#2563eb";
    public static final String EXITO         = "#10b981";
    public static final String EXITO_DARK    = "#059669";
    public static final String PELIGRO       = "#ef4444";
    public static final String PELIGRO_DARK  = "#dc2626";
    public static final String ADVERTENCIA   = "#f59e0b";
    public static final String SIDEBAR       = "#0f172a";
    public static final String SIDEBAR_HOVER = "#1e293b";
    public static final String FONDO         = "#f1f5f9";
    public static final String TEXTO         = "#0f172a";
    public static final String TEXTO_SUAVE   = "#64748b";
    public static final String BORDE         = "#e2e8f0";
    public static final String BORDE_FOCUS   = "#93c5fd";

    // ── Contenedores ──────────────────────────────────────────────────────────
    public static final String TARJETA =
        "-fx-background-color: white;" +
        "-fx-background-radius: 14;" +
        "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.08), 14, 0, 0, 3);";

    public static final String TARJETA_HOVER =
        "-fx-background-color: white;" +
        "-fx-background-radius: 14;" +
        "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.14), 18, 0, 0, 5);";

    // ── Botones ───────────────────────────────────────────────────────────────
    public static String boton(String hex) {
        return "-fx-background-color: " + hex + ";" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 13px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 10 22 10 22;" +
               "-fx-cursor: hand;" +
               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 4, 0, 0, 2);";
    }

    public static String botonOutline(String hex) {
        return "-fx-background-color: transparent;" +
               "-fx-border-color: " + hex + ";" +
               "-fx-border-width: 1.5;" +
               "-fx-border-radius: 8;" +
               "-fx-text-fill: " + hex + ";" +
               "-fx-font-size: 13px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 9 20 9 20;" +
               "-fx-cursor: hand;";
    }

    public static String botonIcono(String hex) {
        return "-fx-background-color: " + hex + "11;" +
               "-fx-text-fill: " + hex + ";" +
               "-fx-font-size: 12px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 6;" +
               "-fx-padding: 6 14 6 14;" +
               "-fx-cursor: hand;";
    }

    public static void aplicarHover(Button btn, String normal, String hover) {
        btn.setStyle(boton(normal));
        btn.setOnMouseEntered(e -> btn.setStyle(boton(hover)));
        btn.setOnMouseExited (e -> btn.setStyle(boton(normal)));
    }

    // ── Inputs ────────────────────────────────────────────────────────────────
    public static String campo() {
        return "-fx-background-color: white;" +
               "-fx-border-color: " + BORDE + ";" +
               "-fx-border-width: 1.5;" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 9 12 9 12;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";";
    }

    public static String campoFocused() {
        return "-fx-background-color: white;" +
               "-fx-border-color: " + PRIMARIO + ";" +
               "-fx-border-width: 2;" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 9 12 9 12;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";" +
               "-fx-effect: dropshadow(gaussian, rgba(59,130,246,0.15), 6, 0, 0, 0);";
    }

    public static String campoError() {
        return "-fx-background-color: #fff5f5;" +
               "-fx-border-color: " + PELIGRO + ";" +
               "-fx-border-width: 1.5;" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 9 12 9 12;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";";
    }

    public static void aplicarFocus(TextField campo) {
        campo.focusedProperty().addListener((obs, wasF, isF) ->
            campo.setStyle(isF ? campoFocused() : campo())
        );
    }

    // ── Textos ────────────────────────────────────────────────────────────────
    public static String titulo() {
        return "-fx-font-size: 22px;-fx-font-weight: bold;-fx-text-fill: " + TEXTO + ";";
    }

    public static String subtitulo() {
        return "-fx-font-size: 13px;-fx-text-fill: " + TEXTO_SUAVE + ";";
    }

    public static String tituloSeccion() {
        return "-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: " + TEXTO + ";";
    }

    public static String etiquetaForm() {
        return "-fx-font-size: 11px;-fx-font-weight: bold;" +
               "-fx-text-fill: " + TEXTO_SUAVE + ";-fx-letter-spacing: 0.5;";
    }

    // ── Notificaciones inline ─────────────────────────────────────────────────
    public static String notaExito() {
        return "-fx-background-color: #f0fdf4;" +
               "-fx-border-color: #86efac;" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 10 14 10 14;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: #166534;";
    }

    public static String notaError() {
        return "-fx-background-color: #fff5f5;" +
               "-fx-border-color: #fca5a5;" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 10 14 10 14;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: #991b1b;";
    }

    public static String notaAdvertencia() {
        return "-fx-background-color: #fffbeb;" +
               "-fx-border-color: #fcd34d;" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 10 14 10 14;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: #92400e;";
    }

    // ── Alertas ───────────────────────────────────────────────────────────────
    public static void exito(String titulo, String msg) {
        mostrar(Alert.AlertType.INFORMATION, "✅  " + titulo, msg);
    }

    public static void error(String titulo, String msg) {
        mostrar(Alert.AlertType.ERROR, "❌  " + titulo, msg);
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String msg) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        DialogPane dp = a.getDialogPane();
        dp.setStyle("-fx-background-color: white;-fx-font-size: 13px;-fx-font-family: 'Segoe UI', System;");
        a.showAndWait();
    }
}

package presentacion.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;

public class EstilosUI {

    // ── Paleta de colores ─────────────────────────────────────────────────────
    public static final String PRIMARIO    = "#3b82f6";
    public static final String EXITO       = "#10b981";
    public static final String PELIGRO     = "#ef4444";
    public static final String ADVERTENCIA = "#f59e0b";
    public static final String SIDEBAR     = "#0f2744";
    public static final String FONDO       = "#f1f5f9";
    public static final String TEXTO       = "#1e293b";
    public static final String TEXTO_SUAVE = "#64748b";
    public static final String BORDE       = "#e2e8f0";

    // ── Contenedores ──────────────────────────────────────────────────────────
    public static final String TARJETA =
        "-fx-background-color: white;" +
        "-fx-background-radius: 12;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.07), 12, 0, 0, 2);";

    // ── Botones ───────────────────────────────────────────────────────────────
    public static String boton(String hexColor) {
        return "-fx-background-color: " + hexColor + ";" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 13px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 10 22 10 22;" +
               "-fx-cursor: hand;";
    }

    public static String botonOutline(String hexColor) {
        return "-fx-background-color: transparent;" +
               "-fx-border-color: " + hexColor + ";" +
               "-fx-border-width: 1.5;" +
               "-fx-border-radius: 8;" +
               "-fx-text-fill: " + hexColor + ";" +
               "-fx-font-size: 13px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 9 20 9 20;" +
               "-fx-cursor: hand;";
    }

    public static void aplicarHover(Button btn, String colorNormal, String colorHover) {
        btn.setStyle(boton(colorNormal));
        btn.setOnMouseEntered(e -> btn.setStyle(boton(colorHover)));
        btn.setOnMouseExited(e  -> btn.setStyle(boton(colorNormal)));
    }

    // ── Campos de texto ───────────────────────────────────────────────────────
    public static String campo() {
        return "-fx-background-color: white;" +
               "-fx-border-color: " + BORDE + ";" +
               "-fx-border-radius: 8;" +
               "-fx-background-radius: 8;" +
               "-fx-padding: 9 12 9 12;" +
               "-fx-font-size: 13px;" +
               "-fx-text-fill: " + TEXTO + ";";
    }

    // ── Textos ─────────────────────────────────────────────────────────────────
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
        return "-fx-font-size: 12px;-fx-font-weight: bold;-fx-text-fill: " + TEXTO_SUAVE + ";";
    }

    // ── Alertas ───────────────────────────────────────────────────────────────
    public static void exito(String titulo, String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, "✅  " + titulo, mensaje);
    }

    public static void error(String titulo, String mensaje) {
        mostrar(Alert.AlertType.ERROR, "❌  " + titulo, mensaje);
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle(
            "-fx-background-color: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI', System;"
        );
        alert.showAndWait();
    }
}

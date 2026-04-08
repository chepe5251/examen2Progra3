package presentacion.controladores;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logicaNegocio.AccesoService;
import presentacion.util.EstilosUI;

public class AccesosController {

    private final AccesoService service = new AccesoService();

    private TextField campoEntrada;
    private TextField campoSalida;
    private Label estadoEntrada;
    private Label estadoSalida;

    public Node getView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox contenido = new VBox(24);
        contenido.setPadding(new Insets(32));
        contenido.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        contenido.getChildren().addAll(
            construirEncabezado(),
            construirPanelAccesos(),
            construirInfoPolicy()
        );

        scroll.setContent(contenido);
        return scroll;
    }

    // ── Encabezado ────────────────────────────────────────────────────────────

    private Node construirEncabezado() {
        VBox caja = new VBox(4);
        Label titulo    = new Label("🚪  Registro de Accesos");
        titulo.setStyle(EstilosUI.titulo());
        Label subtitulo = new Label("Controla la entrada y salida de usuarios al laboratorio");
        subtitulo.setStyle(EstilosUI.subtitulo());
        caja.getChildren().addAll(titulo, subtitulo);
        return caja;
    }

    // ── Panel de accesos ──────────────────────────────────────────────────────

    private Node construirPanelAccesos() {
        HBox fila = new HBox(20);
        fila.setFillHeight(false);

        Node cardEntrada = construirCardAcceso(
            "Registrar Entrada",
            "🟢",
            EstilosUI.EXITO,
            "El usuario ingresa al laboratorio",
            true
        );

        Node cardSalida = construirCardAcceso(
            "Registrar Salida",
            "🔴",
            EstilosUI.PELIGRO,
            "El usuario sale del laboratorio",
            false
        );

        HBox.setHgrow(cardEntrada, Priority.ALWAYS);
        HBox.setHgrow(cardSalida,  Priority.ALWAYS);

        fila.getChildren().addAll(cardEntrada, cardSalida);
        return fila;
    }

    private Node construirCardAcceso(String titulo, String emoji, String color,
                                     String descripcion, boolean esEntrada) {
        VBox card = new VBox(20);
        card.setPadding(new Insets(28));
        card.setMinWidth(280);
        card.setStyle(EstilosUI.TARJETA);

        // Encabezado de la tarjeta
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        Circle circulo = new Circle(22);
        circulo.setFill(Color.web(color + "22"));

        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(20));
        StackPane iconoBg = new StackPane(emojiLabel);
        iconoBg.setPrefSize(44, 44);
        iconoBg.setStyle(
            "-fx-background-color: " + color + "22;" +
            "-fx-background-radius: 50;"
        );

        VBox textos = new VBox(2);
        HBox.setHgrow(textos, Priority.ALWAYS);
        Label tituloLabel = new Label(titulo);
        tituloLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        tituloLabel.setTextFill(Color.web(EstilosUI.TEXTO));
        tituloLabel.setWrapText(true);
        Label descLabel = new Label(descripcion);
        descLabel.setStyle(EstilosUI.subtitulo());
        descLabel.setWrapText(true);
        textos.getChildren().addAll(tituloLabel, descLabel);

        header.getChildren().addAll(iconoBg, textos);

        // Separador visual
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setStyle("-fx-background-color: " + EstilosUI.BORDE + ";");

        // Campo de entrada
        VBox campoGroup = new VBox(6);
        Label campoLabel = new Label("ID del Usuario");
        campoLabel.setStyle(EstilosUI.etiquetaForm());

        TextField campo = new TextField();
        campo.setPromptText("Ej: U001");
        campo.setPrefWidth(Double.MAX_VALUE);
        campo.setStyle(EstilosUI.campo());

        campoGroup.getChildren().addAll(campoLabel, campo);

        // Botón de acción
        Button btnAccion = new Button(titulo);
        btnAccion.setMaxWidth(Double.MAX_VALUE);
        btnAccion.setStyle(EstilosUI.boton(color));
        EstilosUI.aplicarHover(btnAccion, color, esEntrada ? "#059669" : "#dc2626");

        // Etiqueta de estado
        Label estado = new Label("");
        estado.setWrapText(true);
        estado.setStyle("-fx-font-size: 12px;");

        btnAccion.setOnAction(e -> {
            if (esEntrada) {
                registrarEntrada(campo, estado);
            } else {
                registrarSalida(campo, estado);
            }
        });

        // Asignar referencias
        if (esEntrada) {
            campoEntrada  = campo;
            estadoEntrada = estado;
        } else {
            campoSalida  = campo;
            estadoSalida = estado;
        }

        card.getChildren().addAll(header, sep, campoGroup, btnAccion, estado);
        return card;
    }

    // ── Info de política ──────────────────────────────────────────────────────

    private Node construirInfoPolicy() {
        HBox fila = new HBox(16);

        Node cardInfo1 = crearNotaInfo(
            "⚠️  Doble entrada bloqueada",
            "No se puede registrar una segunda entrada si el usuario ya se encuentra dentro del laboratorio.",
            EstilosUI.ADVERTENCIA
        );

        Node cardInfo2 = crearNotaInfo(
            "⚠️  Salida sin entrada bloqueada",
            "No es posible registrar una salida si el usuario no tiene una entrada activa registrada.",
            EstilosUI.ADVERTENCIA
        );

        HBox.setHgrow(cardInfo1, Priority.ALWAYS);
        HBox.setHgrow(cardInfo2, Priority.ALWAYS);

        fila.getChildren().addAll(cardInfo1, cardInfo2);
        return fila;
    }

    private Node crearNotaInfo(String titulo, String texto, String color) {
        HBox card = new HBox(16);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
            "-fx-background-color: " + color + "11;" +
            "-fx-border-color: " + color + "44;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        VBox textos = new VBox(4);
        Label tituloLabel = new Label(titulo);
        tituloLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        tituloLabel.setTextFill(Color.web(color));

        Label textoLabel = new Label(texto);
        textoLabel.setStyle("-fx-font-size: 12px;-fx-text-fill: " + EstilosUI.TEXTO_SUAVE + ";");
        textoLabel.setWrapText(true);

        textos.getChildren().addAll(tituloLabel, textoLabel);
        card.getChildren().add(textos);
        return card;
    }

    // ── Acciones ──────────────────────────────────────────────────────────────

    private void registrarEntrada(TextField campo, Label estado) {
        String id = campo.getText().trim();
        if (id.isEmpty()) {
            mostrarEstado(estado, "⚠️  Ingresa un ID de usuario.", EstilosUI.ADVERTENCIA);
            return;
        }
        try {
            service.registrarEntrada(id);
            mostrarEstado(estado, "✅  Entrada registrada para el usuario " + id + ".", EstilosUI.EXITO);
            campo.clear();
        } catch (Exception ex) {
            mostrarEstado(estado, "❌  " + ex.getMessage(), EstilosUI.PELIGRO);
        }
    }

    private void registrarSalida(TextField campo, Label estado) {
        String id = campo.getText().trim();
        if (id.isEmpty()) {
            mostrarEstado(estado, "⚠️  Ingresa un ID de usuario.", EstilosUI.ADVERTENCIA);
            return;
        }
        try {
            service.registrarSalida(id);
            mostrarEstado(estado, "✅  Salida registrada para el usuario " + id + ".", EstilosUI.EXITO);
            campo.clear();
        } catch (Exception ex) {
            mostrarEstado(estado, "❌  " + ex.getMessage(), EstilosUI.PELIGRO);
        }
    }

    private void mostrarEstado(Label label, String mensaje, String color) {
        label.setText(mensaje);
        label.setTextFill(Color.web(color));
    }
}

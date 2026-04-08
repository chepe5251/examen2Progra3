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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logicaNegocio.AccesoService;
import presentacion.util.EstilosUI;

public class AccesosController {

    private final AccesoService service = new AccesoService();

    public Node getView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox contenido = new VBox(28);
        contenido.setPadding(new Insets(36));
        contenido.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        contenido.getChildren().addAll(
            construirEncabezado(),
            construirPanelAccesos(),
            construirInfoPolicy()
        );

        scroll.setContent(contenido);
        return scroll;
    }

    private Node construirEncabezado() {
        VBox caja = new VBox(6);
        Label titulo = new Label("Registro de accesos");
        titulo.setStyle(EstilosUI.titulo());
        Label subtitulo = new Label("Controla entradas y salidas con retroalimentación inmediata dentro de la misma vista.");
        subtitulo.setStyle(EstilosUI.subtitulo());
        caja.getChildren().addAll(titulo, subtitulo);
        return caja;
    }

    private Node construirPanelAccesos() {
        HBox fila = new HBox(18);
        fila.setFillHeight(false);

        Node cardEntrada = construirCardAcceso(
            "Entrada al laboratorio",
            "Marca el ingreso del usuario al sistema.",
            "ENTRADA",
            EstilosUI.EXITO,
            true
        );

        Node cardSalida = construirCardAcceso(
            "Salida del laboratorio",
            "Cierra el acceso activo del usuario.",
            "SALIDA",
            EstilosUI.PELIGRO,
            false
        );

        HBox.setHgrow(cardEntrada, Priority.ALWAYS);
        HBox.setHgrow(cardSalida, Priority.ALWAYS);
        fila.getChildren().addAll(cardEntrada, cardSalida);
        return fila;
    }

    private Node construirCardAcceso(String titulo, String descripcion, String etiqueta, String color, boolean esEntrada) {
        VBox card = new VBox(20);
        card.setPadding(new Insets(28));
        card.setMinWidth(320);
        EstilosUI.aplicarHoverTarjeta(card);

        VBox header = new VBox(10);

        Label chip = new Label(etiqueta);
        chip.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10.5));
        chip.setStyle(EstilosUI.badgeSuave(color));

        Label tituloLabel = new Label(titulo);
        tituloLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        tituloLabel.setTextFill(Color.web(EstilosUI.TEXTO));

        Label descLabel = new Label(descripcion);
        descLabel.setStyle(EstilosUI.subtitulo());
        descLabel.setWrapText(true);

        header.getChildren().addAll(chip, tituloLabel, descLabel);

        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setStyle("-fx-background-color: " + EstilosUI.BORDE + ";");

        VBox campoGroup = new VBox(7);
        Label campoLabel = new Label("ID del usuario");
        campoLabel.setStyle(EstilosUI.etiquetaForm());

        TextField campo = new TextField();
        campo.setPromptText("Ej: U001");
        campo.setPrefWidth(Double.MAX_VALUE);
        EstilosUI.aplicarFocus(campo);

        campoGroup.getChildren().addAll(campoLabel, campo);

        Button btnAccion = new Button(esEntrada ? "Registrar entrada" : "Registrar salida");
        btnAccion.setMaxWidth(Double.MAX_VALUE);
        EstilosUI.aplicarHover(btnAccion, color, esEntrada ? EstilosUI.EXITO_DARK : EstilosUI.PELIGRO_DARK);

        Label estado = new Label();
        estado.setWrapText(true);
        estado.setVisible(false);
        estado.setManaged(false);

        btnAccion.setOnAction(e -> {
            if (esEntrada) {
                registrarEntrada(campo, estado);
            } else {
                registrarSalida(campo, estado);
            }
        });

        card.getChildren().addAll(header, sep, campoGroup, btnAccion, estado);
        return card;
    }

    private Node construirInfoPolicy() {
        HBox fila = new HBox(18);

        Node cardInfo1 = crearNotaInfo(
            "Entrada duplicada bloqueada",
            "Si el usuario ya tiene una entrada activa, el sistema evita un nuevo registro hasta que exista una salida.",
            EstilosUI.ADVERTENCIA
        );

        Node cardInfo2 = crearNotaInfo(
            "Salida sin ingreso bloqueada",
            "Una salida solo puede registrarse cuando el usuario tiene una entrada activa pendiente de cierre.",
            EstilosUI.ADVERTENCIA
        );

        HBox.setHgrow(cardInfo1, Priority.ALWAYS);
        HBox.setHgrow(cardInfo2, Priority.ALWAYS);
        fila.getChildren().addAll(cardInfo1, cardInfo2);
        return fila;
    }

    private Node crearNotaInfo(String titulo, String texto, String color) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle(
            "-fx-background-color: " + color + "10;" +
            "-fx-border-color: " + color + "30;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 14;" +
            "-fx-background-radius: 14;"
        );

        Label tituloLabel = new Label(titulo);
        tituloLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        tituloLabel.setTextFill(Color.web(color));

        Label textoLabel = new Label(texto);
        textoLabel.setStyle(EstilosUI.subtitulo());
        textoLabel.setWrapText(true);

        card.getChildren().addAll(tituloLabel, textoLabel);
        return card;
    }

    private void registrarEntrada(TextField campo, Label estado) {
        String id = campo.getText().trim();
        if (id.isEmpty()) {
            mostrarEstado(estado, "Ingresa un ID de usuario para registrar la entrada.", EstilosUI.notaAdvertencia());
            return;
        }
        try {
            service.registrarEntrada(id);
            mostrarEstado(estado, "Entrada registrada correctamente para el usuario " + id + ".", EstilosUI.notaExito());
            campo.clear();
        } catch (Exception ex) {
            mostrarEstado(estado, ex.getMessage(), EstilosUI.notaError());
        }
    }

    private void registrarSalida(TextField campo, Label estado) {
        String id = campo.getText().trim();
        if (id.isEmpty()) {
            mostrarEstado(estado, "Ingresa un ID de usuario para registrar la salida.", EstilosUI.notaAdvertencia());
            return;
        }
        try {
            service.registrarSalida(id);
            mostrarEstado(estado, "Salida registrada correctamente para el usuario " + id + ".", EstilosUI.notaExito());
            campo.clear();
        } catch (Exception ex) {
            mostrarEstado(estado, ex.getMessage(), EstilosUI.notaError());
        }
    }

    private void mostrarEstado(Label label, String mensaje, String estilo) {
        label.setText(mensaje);
        label.setStyle(estilo);
        label.setVisible(true);
        label.setManaged(true);
    }
}
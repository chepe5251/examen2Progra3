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
    private final AccesoService  accesoService  = new AccesoService();

    public Node getView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox contenido = new VBox(24);
        contenido.setPadding(new Insets(32));
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

    // ── Encabezado ────────────────────────────────────────────────────────────

    private Node construirEncabezado() {
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER_LEFT);

        VBox textos = new VBox(4);
        Label titulo    = new Label("Dashboard");
        titulo.setStyle(EstilosUI.titulo());
        Label subtitulo = new Label("Resumen general del sistema de control de acceso al laboratorio");
        subtitulo.setStyle(EstilosUI.subtitulo());
        textos.getChildren().addAll(titulo, subtitulo);

        fila.getChildren().add(textos);
        return fila;
    }

    // ── Tarjetas de métricas ──────────────────────────────────────────────────

    private Node construirTarjetasMetrica() {
        int totalUsuarios  = 0;
        long dentroLab     = 0;
        int totalAccesos   = 0;

        try { totalUsuarios = usuarioService.listar().size(); }             catch (Exception ignored) {}
        try { dentroLab     = accesoService.contarDentroDelLaboratorio(); } catch (Exception ignored) {}
        try { totalAccesos  = accesoService.listarTodosLosAccesos().size(); } catch (Exception ignored) {}

        HBox fila = new HBox(16);
        fila.getChildren().addAll(
            crearTarjeta("👥", "Usuarios Registrados", String.valueOf(totalUsuarios), EstilosUI.PRIMARIO),
            crearTarjeta("🟢", "Dentro del Laboratorio", String.valueOf(dentroLab),    EstilosUI.EXITO),
            crearTarjeta("📋", "Total de Accesos",       String.valueOf(totalAccesos), EstilosUI.ADVERTENCIA)
        );

        for (Node tarjeta : fila.getChildren()) {
            HBox.setHgrow(tarjeta, Priority.ALWAYS);
        }

        return fila;
    }

    private Node crearTarjeta(String emoji, String etiqueta, String valor, String color) {
        VBox tarjeta = new VBox(14);
        tarjeta.setPadding(new Insets(24));
        tarjeta.setStyle(EstilosUI.TARJETA);

        // Ícono con fondo coloreado
        Label iconoLabel = new Label(emoji);
        iconoLabel.setFont(Font.font(22));
        StackPane iconoBg = new StackPane(iconoLabel);
        iconoBg.setPrefSize(46, 46);
        iconoBg.setStyle(
            "-fx-background-color: " + color + "22;" +
            "-fx-background-radius: 10;"
        );

        HBox filaIcono = new HBox(12);
        filaIcono.setAlignment(Pos.CENTER_LEFT);
        Label etiqLabel = new Label(etiqueta);
        etiqLabel.setStyle(EstilosUI.subtitulo());
        etiqLabel.setWrapText(true);
        filaIcono.getChildren().addAll(iconoBg, etiqLabel);

        // Acento de color arriba de la tarjeta
        Rectangle acento = new Rectangle(40, 4);
        acento.setFill(Color.web(color));
        acento.setArcWidth(4);
        acento.setArcHeight(4);

        Label valorLabel = new Label(valor);
        valorLabel.setFont(Font.font("System", FontWeight.BOLD, 38));
        valorLabel.setTextFill(Color.web(color));

        tarjeta.getChildren().addAll(acento, filaIcono, valorLabel);
        return tarjeta;
    }

    // ── Info del sistema ──────────────────────────────────────────────────────

    private Node construirInfoSistema() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(EstilosUI.TARJETA);

        Label titulo = new Label("Información del Sistema");
        titulo.setStyle(EstilosUI.tituloSeccion());

        String[][] filas = {
            {"🏗️  Arquitectura",    "Capas: Entidades · AccesoDatos · LogicaNegocio · Presentacion"},
            {"💾  Persistencia",    "Archivos de texto plano: usuarios.txt  y  accesos.txt"},
            {"🔐  Roles",           "Estudiante  ·  Docente"},
            {"☕  Tecnología",      "Java 17+  ·  JavaFX"},
            {"📦  Repositorio",     "github.com/chepe5251/examen2Progra3"}
        };

        GridPane grid = new GridPane();
        grid.setHgap(32);
        grid.setVgap(12);

        for (int i = 0; i < filas.length; i++) {
            Label clave = new Label(filas[i][0]);
            clave.setFont(Font.font("System", FontWeight.BOLD, 13));
            clave.setTextFill(Color.web(EstilosUI.TEXTO));

            Label valor = new Label(filas[i][1]);
            valor.setFont(Font.font("System", 13));
            valor.setTextFill(Color.web(EstilosUI.TEXTO_SUAVE));

            grid.add(clave, 0, i);
            grid.add(valor, 1, i);
        }

        card.getChildren().addAll(titulo, grid);
        return card;
    }

    // ── Reglas y política ─────────────────────────────────────────────────────

    private Node construirReglasDePolitica() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(24));
        card.setStyle(EstilosUI.TARJETA);

        Label titulo = new Label("Reglas de Acceso");
        titulo.setStyle(EstilosUI.tituloSeccion());

        String[] reglas = {
            "✅  Un usuario solo puede registrar entrada si no tiene una entrada activa previa.",
            "✅  No se puede registrar salida sin una entrada activa registrada.",
            "✅  Los IDs de usuario son únicos — no se permiten duplicados.",
            "✅  El tiempo en laboratorio solo considera accesos con entrada y salida registradas."
        };

        VBox lista = new VBox(8);
        for (String regla : reglas) {
            Label item = new Label(regla);
            item.setStyle("-fx-font-size: 13px;-fx-text-fill: " + EstilosUI.TEXTO + ";");
            item.setWrapText(true);
            lista.getChildren().add(item);
        }

        card.getChildren().addAll(titulo, lista);
        return card;
    }
}

package presentacion.controladores;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import presentacion.util.EstilosUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainController {

    private final BorderPane root;
    private final StackPane  contentArea;
    private Button btnActivo;

    public MainController() {
        root        = new BorderPane();
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        root.setTop(construirTopBar());
        root.setLeft(construirSidebar());
        root.setCenter(contentArea);

        mostrar(new DashboardController().getView(), null);
    }

    private HBox construirTopBar() {
        HBox bar = new HBox();
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(0, 24, 0, 24));
        bar.setPrefHeight(52);
        bar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: transparent transparent " + EstilosUI.BORDE + " transparent;" +
            "-fx-border-width: 0 0 1 0;"
        );

        Label appName = new Label("Sistema de Control de Acceso · Laboratorio");
        appName.setFont(Font.font("System", FontWeight.BOLD, 14));
        appName.setTextFill(Color.web(EstilosUI.TEXTO));

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        Label reloj = new Label();
        reloj.setFont(Font.font("System", 13));
        reloj.setTextFill(Color.web(EstilosUI.TEXTO_SUAVE));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM  ·  HH:mm:ss",
                Locale.forLanguageTag("es-CR"));

        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1),
                e -> reloj.setText(LocalDateTime.now().format(fmt))));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        reloj.setText(LocalDateTime.now().format(fmt));

        bar.getChildren().addAll(appName, espaciador, reloj);
        return bar;
    }

    private VBox construirSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(230);
        sidebar.setStyle("-fx-background-color: " + EstilosUI.SIDEBAR + ";");

        VBox logo = new VBox(4);
        logo.setPadding(new Insets(28, 20, 24, 20));
        logo.setAlignment(Pos.CENTER_LEFT);

        Label icono = new Label("🔬");
        icono.setFont(Font.font(30));

        Label nombre = new Label("Lab Access");
        nombre.setFont(Font.font("System", FontWeight.BOLD, 18));
        nombre.setTextFill(Color.WHITE);

        Label subtitulo = new Label("Control de Acceso");
        subtitulo.setFont(Font.font("System", 12));
        subtitulo.setTextFill(Color.web("#94a3b8"));

        logo.getChildren().addAll(icono, nombre, subtitulo);

        Region divisor = new Region();
        divisor.setPrefHeight(1);
        divisor.setStyle("-fx-background-color: #1e293b;");
        VBox.setMargin(divisor, new Insets(0, 16, 16, 16));

        Label navLabel = new Label("MENÚ");
        navLabel.setFont(Font.font("System", FontWeight.BOLD, 10));
        navLabel.setTextFill(Color.web("#475569"));
        VBox.setMargin(navLabel, new Insets(0, 0, 6, 20));

        Button bDashboard = crearBotonNav("📊   Dashboard");
        Button bUsuarios  = crearBotonNav("👥   Usuarios");
        Button bAccesos   = crearBotonNav("🚪   Accesos");
        Button bReportes  = crearBotonNav("📋   Reportes");

        bDashboard.setOnAction(e -> cambiarSeccion(bDashboard, new DashboardController().getView()));
        bUsuarios .setOnAction(e -> cambiarSeccion(bUsuarios,  new UsuariosController().getView()));
        bAccesos  .setOnAction(e -> cambiarSeccion(bAccesos,   new AccesosController().getView()));
        bReportes .setOnAction(e -> cambiarSeccion(bReportes,  new ReportesController().getView()));

        for (Button btn : new Button[]{bDashboard, bUsuarios, bAccesos, bReportes}) {
            VBox.setMargin(btn, new Insets(2, 10, 2, 10));
        }

        Region espaciador = new Region();
        VBox.setVgrow(espaciador, Priority.ALWAYS);

        Label version = new Label("v2.1  ·  Universidad Latina");
        version.setFont(Font.font("System", 11));
        version.setTextFill(Color.web("#334155"));
        VBox.setMargin(version, new Insets(16, 0, 20, 20));

        sidebar.getChildren().addAll(
            logo, divisor, navLabel,
            bDashboard, bUsuarios, bAccesos, bReportes,
            espaciador, version
        );

        setActivo(bDashboard);
        return sidebar;
    }

    private Button crearBotonNav(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(11, 16, 11, 16));
        btn.setFont(Font.font("System", 13));
        estiloInactivo(btn);

        btn.setOnMouseEntered(e -> { if (btn != btnActivo) estiloHover(btn); });
        btn.setOnMouseExited (e -> { if (btn != btnActivo) estiloInactivo(btn); });

        return btn;
    }

    private void cambiarSeccion(Button btn, javafx.scene.Node vista) {
        mostrar(vista, btn);
    }

    private void mostrar(javafx.scene.Node vista, Button btn) {
        contentArea.getChildren().setAll(vista);
        if (btn != null) {
            setActivo(btn);
        }
    }

    private void setActivo(Button btn) {
        if (btnActivo != null) {
            estiloInactivo(btnActivo);
        }
        btnActivo = btn;
        btn.setStyle(
            "-fx-background-color: linear-gradient(to right, " + EstilosUI.PRIMARIO + " 3px, #1e293b 3px);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
    }

    private void estiloInactivo(Button btn) {
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #94a3b8;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
    }

    private void estiloHover(Button btn) {
        btn.setStyle(
            "-fx-background-color: " + EstilosUI.SIDEBAR_HOVER + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
    }

    public BorderPane getView() {
        return root;
    }
}
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
    private final StackPane contentArea;
    private Button btnActivo;

    public MainController() {
        root = new BorderPane();
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        root.setTop(construirTopBar());
        root.setLeft(construirSidebar());
        root.setCenter(contentArea);

        mostrar(new DashboardController().getView(), null);
    }

    private HBox construirTopBar() {
        HBox bar = new HBox(18);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(16, 28, 16, 28));
        bar.setStyle(
            "-fx-background-color: " + EstilosUI.SUPERFICIE + ";" +
            "-fx-border-color: transparent transparent " + EstilosUI.BORDE + " transparent;" +
            "-fx-border-width: 0 0 1 0;"
        );

        VBox textos = new VBox(2);
        Label appName = new Label("Sistema de Control de Acceso");
        appName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        appName.setTextFill(Color.web(EstilosUI.TEXTO));

        Label appSubtitle = new Label("Laboratorio académico · panel principal");
        appSubtitle.setFont(Font.font("Segoe UI", 12));
        appSubtitle.setTextFill(Color.web(EstilosUI.TEXTO_SUAVE));
        textos.getChildren().addAll(appName, appSubtitle);

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        Label reloj = new Label();
        reloj.setFont(Font.font("Segoe UI", 12.5));
        reloj.setTextFill(Color.web(EstilosUI.TEXTO_SUAVE));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM  ·  HH:mm:ss",
                Locale.forLanguageTag("es-CR"));

        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1),
                e -> reloj.setText(LocalDateTime.now().format(fmt))));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        reloj.setText(LocalDateTime.now().format(fmt));

        bar.getChildren().addAll(textos, espaciador, reloj);
        return bar;
    }

    private VBox construirSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(244);
        sidebar.setPadding(new Insets(18, 14, 18, 14));
        sidebar.setStyle("-fx-background-color: " + EstilosUI.SIDEBAR + ";");

        HBox logo = new HBox(12);
        logo.setAlignment(Pos.CENTER_LEFT);
        logo.setPadding(new Insets(12, 10, 18, 10));

        StackPane marca = new StackPane();
        marca.setPrefSize(40, 40);
        marca.setMaxSize(40, 40);
        marca.setStyle(
            "-fx-background-color: " + EstilosUI.PRIMARIO + "20;" +
            "-fx-border-color: " + EstilosUI.PRIMARIO + "44;" +
            "-fx-border-width: 1;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;"
        );

        Label iniciales = new Label("LA");
        iniciales.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        iniciales.setTextFill(Color.web(EstilosUI.TEXTO_CLARO));
        marca.getChildren().add(iniciales);

        VBox textos = new VBox(2);
        Label nombre = new Label("Lab Access");
        nombre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        nombre.setTextFill(Color.WHITE);

        Label subtitulo = new Label("Control del laboratorio");
        subtitulo.setFont(Font.font("Segoe UI", 11.5));
        subtitulo.setTextFill(Color.web("#94a3b8"));
        textos.getChildren().addAll(nombre, subtitulo);
        logo.getChildren().addAll(marca, textos);

        Region divisor = new Region();
        divisor.setPrefHeight(1);
        divisor.setStyle("-fx-background-color: #202a3f;");
        VBox.setMargin(divisor, new Insets(0, 10, 14, 10));

        Label navLabel = new Label("NAVEGACION");
        navLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        navLabel.setTextFill(Color.web("#64748b"));
        VBox.setMargin(navLabel, new Insets(0, 0, 8, 12));

        Button bDashboard = crearBotonNav("Dashboard");
        Button bUsuarios = crearBotonNav("Usuarios");
        Button bAccesos = crearBotonNav("Accesos");
        Button bReportes = crearBotonNav("Reportes");

        bDashboard.setOnAction(e -> cambiarSeccion(bDashboard, new DashboardController().getView()));
        bUsuarios.setOnAction(e -> cambiarSeccion(bUsuarios, new UsuariosController().getView()));
        bAccesos.setOnAction(e -> cambiarSeccion(bAccesos, new AccesosController().getView()));
        bReportes.setOnAction(e -> cambiarSeccion(bReportes, new ReportesController().getView()));

        for (Button btn : new Button[]{bDashboard, bUsuarios, bAccesos, bReportes}) {
            VBox.setMargin(btn, new Insets(3, 4, 3, 4));
        }

        Region espaciador = new Region();
        VBox.setVgrow(espaciador, Priority.ALWAYS);

        Label version = new Label("Universidad Latina  ·  v2.3");
        version.setFont(Font.font("Segoe UI", 11));
        version.setTextFill(Color.web("#64748b"));
        VBox.setMargin(version, new Insets(18, 0, 6, 12));

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
        btn.setPadding(new Insets(12, 16, 12, 16));
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        estiloInactivo(btn);

        btn.setOnMouseEntered(e -> {
            if (btn != btnActivo) {
                estiloHover(btn);
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != btnActivo) {
                estiloInactivo(btn);
            }
        });
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
            "-fx-background-color: " + EstilosUI.PRIMARIO + "18;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: transparent transparent transparent " + EstilosUI.PRIMARIO + ";" +
            "-fx-border-width: 0 0 0 3;" +
            "-fx-cursor: hand;"
        );
    }

    private void estiloInactivo(Button btn) {
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + EstilosUI.TEXTO_CLARO + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: transparent;" +
            "-fx-cursor: hand;"
        );
    }

    private void estiloHover(Button btn) {
        btn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.06);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: transparent;" +
            "-fx-cursor: hand;"
        );
    }

    public BorderPane getView() {
        return root;
    }
}
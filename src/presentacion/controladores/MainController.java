package presentacion.controladores;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import presentacion.util.EstilosUI;

public class MainController {

    private final BorderPane root;
    private final StackPane  contentArea;

    private Button btnActivo;

    public MainController() {
        root        = new BorderPane();
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        root.setLeft(construirSidebar());
        root.setCenter(contentArea);

        mostrar(new DashboardController().getView(), null);
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────

    private VBox construirSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(230);
        sidebar.setStyle("-fx-background-color: " + EstilosUI.SIDEBAR + ";");

        // Logo
        VBox logo = new VBox(4);
        logo.setPadding(new Insets(28, 20, 24, 20));
        logo.setAlignment(Pos.CENTER_LEFT);

        Label icono    = new Label("🔬");
        icono.setFont(Font.font(30));

        Label nombre   = new Label("Lab Access");
        nombre.setFont(Font.font("System", FontWeight.BOLD, 18));
        nombre.setTextFill(Color.WHITE);

        Label subtitulo = new Label("Control de Acceso");
        subtitulo.setFont(Font.font("System", 12));
        subtitulo.setTextFill(Color.web("#94a3b8"));

        logo.getChildren().addAll(icono, nombre, subtitulo);

        // Divisor
        Region divisor = new Region();
        divisor.setPrefHeight(1);
        divisor.setStyle("-fx-background-color: #1e3a5f;");
        VBox.setMargin(divisor, new Insets(0, 16, 16, 16));

        // Sección de navegación
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

        // Espaciador + versión
        Region espaciador = new Region();
        VBox.setVgrow(espaciador, Priority.ALWAYS);

        Label version = new Label("v1.3  ·  Universidad Latina");
        version.setFont(Font.font("System", 11));
        version.setTextFill(Color.web("#334155"));
        VBox.setMargin(version, new Insets(16, 0, 20, 20));

        for (Button btn : new Button[]{bDashboard, bUsuarios, bAccesos, bReportes}) {
            VBox.setMargin(btn, new Insets(2, 10, 2, 10));
        }

        sidebar.getChildren().addAll(
            logo, divisor, navLabel,
            bDashboard, bUsuarios, bAccesos, bReportes,
            espaciador, version
        );

        // Activo por defecto
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
        if (btn != null) setActivo(btn);
    }

    private void setActivo(Button btn) {
        if (btnActivo != null) estiloInactivo(btnActivo);
        btnActivo = btn;
        btn.setStyle(
            "-fx-background-color: #3b82f6;" +
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
            "-fx-background-color: #1a3a5c;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
    }

    public BorderPane getView() {
        return root;
    }
}

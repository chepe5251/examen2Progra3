package presentacion.controladores;

import entidades.Rol;
import entidades.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logicaNegocio.UsuarioService;
import presentacion.util.EstilosUI;

import java.util.List;

public class UsuariosController {

    private final UsuarioService service = new UsuarioService();

    private TextField campoId;
    private TextField campoNombre;
    private ComboBox<String> comboRol;
    private TableView<Usuario> tabla;
    private final ObservableList<Usuario> datos = FXCollections.observableArrayList();

    public Node getView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox contenido = new VBox(24);
        contenido.setPadding(new Insets(32));
        contenido.setStyle("-fx-background-color: " + EstilosUI.FONDO + ";");

        contenido.getChildren().addAll(
            construirEncabezado(),
            construirFormulario(),
            construirTabla()
        );

        cargarDatos();

        scroll.setContent(contenido);
        return scroll;
    }

    private Node construirEncabezado() {
        VBox caja = new VBox(4);
        Label titulo    = new Label("👥  Gestión de Usuarios");
        titulo.setStyle(EstilosUI.titulo());
        Label subtitulo = new Label("Registra, consulta y elimina usuarios del sistema");
        subtitulo.setStyle(EstilosUI.subtitulo());
        caja.getChildren().addAll(titulo, subtitulo);
        return caja;
    }

    private Node construirFormulario() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(EstilosUI.TARJETA);

        Label titulo = new Label("Registrar Nuevo Usuario");
        titulo.setStyle(EstilosUI.tituloSeccion());

        HBox campos = new HBox(16);
        campos.setAlignment(Pos.BOTTOM_LEFT);

        campoId     = crearCampo("ID de Usuario", 160);
        campoNombre = crearCampo("Nombre Completo", 260);

        comboRol = new ComboBox<>();
        comboRol.getItems().addAll("ESTUDIANTE", "DOCENTE");
        comboRol.setPromptText("Rol");
        comboRol.setPrefWidth(160);
        comboRol.setStyle(EstilosUI.campo());

        Button btnRegistrar = new Button("Registrar Usuario");
        btnRegistrar.setStyle(EstilosUI.boton(EstilosUI.PRIMARIO));
        EstilosUI.aplicarHover(btnRegistrar, EstilosUI.PRIMARIO, "#2563eb");
        btnRegistrar.setOnAction(e -> registrarUsuario());

        campos.getChildren().addAll(
            labeledField("ID", campoId),
            labeledField("Nombre", campoNombre),
            labeledField("Rol", comboRol),
            btnRegistrar
        );

        HBox.setMargin(btnRegistrar, new Insets(0, 0, 1, 8));

        card.getChildren().addAll(titulo, campos);
        return card;
    }

    private VBox labeledField(String etiqueta, Control campo) {
        VBox caja = new VBox(6);
        Label lbl = new Label(etiqueta);
        lbl.setStyle(EstilosUI.etiquetaForm());
        caja.getChildren().addAll(lbl, campo);
        return caja;
    }

    private TextField crearCampo(String placeholder, double ancho) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setPrefWidth(ancho);
        tf.setStyle(EstilosUI.campo());
        return tf;
    }

    private Node construirTabla() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(EstilosUI.TARJETA);

        Label titulo = new Label("Usuarios Registrados");
        titulo.setStyle(EstilosUI.tituloSeccion());

        tabla = new TableView<>(datos);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPrefHeight(380);
        tabla.setPlaceholder(new Label("No hay usuarios registrados."));
        tabla.setStyle("-fx-background-color: transparent;");

        TableColumn<Usuario, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getId()));
        colId.setPrefWidth(120);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colNombre.setPrefWidth(240);

        TableColumn<Usuario, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRol().name()));
        colRol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String rol, boolean empty) {
                super.updateItem(rol, empty);
                if (empty || rol == null) {
                    setGraphic(null);
                    return;
                }
                Label badge = new Label(rol);
                badge.setFont(Font.font("System", FontWeight.BOLD, 11));
                badge.setTextFill(Color.WHITE);
                String color = rol.equals("DOCENTE") ? EstilosUI.PRIMARIO : EstilosUI.EXITO;
                badge.setStyle(
                    "-fx-background-color: " + color + ";" +
                    "-fx-background-radius: 20;" +
                    "-fx-padding: 3 10 3 10;"
                );
                setGraphic(badge);
            }
        });

        TableColumn<Usuario, Void> colAccion = new TableColumn<>("Acción");
        colAccion.setPrefWidth(120);
        colAccion.setCellFactory(col -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");
            {
                btnEliminar.setStyle(EstilosUI.boton(EstilosUI.PELIGRO));
                btnEliminar.setFont(Font.font("System", 12));
                btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(EstilosUI.boton("#dc2626")));
                btnEliminar.setOnMouseExited (e -> btnEliminar.setStyle(EstilosUI.boton(EstilosUI.PELIGRO)));
                btnEliminar.setOnAction(e -> {
                    Usuario u = getTableView().getItems().get(getIndex());
                    eliminarUsuario(u.getId());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnEliminar);
            }
        });

        tabla.getColumns().add(colId);
        tabla.getColumns().add(colNombre);
        tabla.getColumns().add(colRol);
        tabla.getColumns().add(colAccion);
        card.getChildren().addAll(titulo, tabla);
        return card;
    }

    private void registrarUsuario() {
        String id     = campoId.getText().trim();
        String nombre = campoNombre.getText().trim();
        String rolStr = comboRol.getValue();

        if (id.isEmpty() || nombre.isEmpty() || rolStr == null) {
            EstilosUI.error("Campos incompletos", "Por favor, completa todos los campos antes de registrar.");
            return;
        }

        try {
            service.registrar(id, nombre, Rol.valueOf(rolStr));
            EstilosUI.exito("Usuario registrado", "El usuario \"" + nombre + "\" fue registrado correctamente.");
            limpiarFormulario();
            cargarDatos();
        } catch (Exception ex) {
            EstilosUI.error("Error al registrar", ex.getMessage());
        }
    }

    private void eliminarUsuario(String id) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Deseas eliminar al usuario con ID: " + id + "?\nSi tiene historial, este se conserva; si está activo, la operación se bloquea.");
        confirm.getDialogPane().setStyle("-fx-background-color: white; -fx-font-size: 13px;");

        confirm.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.OK) {
                try {
                    service.eliminar(id);
                    EstilosUI.exito("Usuario eliminado", "El usuario " + id + " fue eliminado del sistema.");
                    cargarDatos();
                } catch (Exception ex) {
                    EstilosUI.error("Error al eliminar", ex.getMessage());
                }
            }
        });
    }

    private void cargarDatos() {
        try {
            List<Usuario> lista = service.listar();
            datos.setAll(lista);
        } catch (Exception ex) {
            EstilosUI.error("Error al cargar datos", ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        campoId.clear();
        campoNombre.clear();
        comboRol.setValue(null);
    }
}
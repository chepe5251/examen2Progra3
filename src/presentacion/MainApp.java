package presentacion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentacion.controladores.MainController;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController mainController = new MainController();

        Scene scene = new Scene(mainController.getView(), 1150, 730);

        primaryStage.setTitle("Sistema de Control de Acceso — Laboratorio");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(950);
        primaryStage.setMinHeight(620);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

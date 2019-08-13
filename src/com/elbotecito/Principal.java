package com.elbotecito;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vista/Principal.fxml"));
        primaryStage.setTitle("El botecito");
        primaryStage.getIcons().add(new Image("/com/elbotecito/vista/imagenes/Logo45x45.png"));
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1080);
        primaryStage.setResizable(true);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.CANCEL);
                alert.getButtonTypes().add(ButtonType.YES);
                alert.setTitle("Cerrar Aplicación");
                alert.setContentText(String.format("¿Desea salir de la aplicación?"));
                alert.initOwner(primaryStage.getOwner());
                Optional<ButtonType> res = alert.showAndWait();

                if (res.isPresent()) {
                    if (res.get().equals(ButtonType.CANCEL))
                        we.consume();
                }
            }
        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}

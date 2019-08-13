package com.elbotecito.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import com.elbotecito.bsn.BarcoBsn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorPrincipal implements Initializable {

    @FXML
    private Button btnTripulacion;

    @FXML
    private Button btnBarcos;

    @FXML
    private Button btnRutas;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnInformacion;

    @FXML
    private Button btnEmpresa;

    @FXML
    private TabPane Barcos;

    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    private BarcoBsn barcoBsn = new BarcoBsn();

    @FXML
    private void handleButtonClicks(ActionEvent event) {

        if (event.getSource() == btnEmpresa) {
            loadStage("/com/elbotecito/vista/Puerto.fxml");
        } else if (event.getSource() == btnBarcos) {
            loadStage("/com/elbotecito/vista/Barco.fxml");
        } else if (event.getSource() == btnTripulacion) {
            loadStage("/com/elbotecito/vista/Tripulacion.fxml");
        } else if (event.getSource() == btnRutas) {
            loadStage("/com/elbotecito/vista/Ruta.fxml");
        } else if (event.getSource() == btnReportes) {
            loadStage("/com/elbotecito/vista/Reporte.fxml");
        } else if (event.getSource() == btnInformacion) {
            loadStage("/com/elbotecito/vista/Informacion.fxml");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Logo.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root,1080, 720));
            stage.getIcons().add(new Image("/com/elbotecito/vista/imagenes/Logo45x45.png"));
            stage.setMinHeight(720);
            stage.setMinWidth(1080);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

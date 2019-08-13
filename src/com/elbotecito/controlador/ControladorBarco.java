package com.elbotecito.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorBarco implements Initializable {
    @FXML
    private Button BTModificar;

    @FXML
    private TextField TFDireccion;

    @FXML
    private TextField TFTelefono;

    @FXML
    private TextField TFCorreo;

    @FXML
    private Button guardarEmpresa;

    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    @FXML
    void guardarEmpresa(ActionEvent event) {

    }

    @FXML
    void modificarEmpresa(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Barco.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
    }
}

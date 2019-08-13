package com.elbotecito.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorTripulacion implements Initializable {

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
    private Pane Capitan;

    @FXML
    private TextField TFDireccion1;

    @FXML
    private TextField TFDireccion2;

    @FXML
    private TextField TFDireccion4;

    @FXML
    private Pane Esposa;

    @FXML
    private TextField TFDireccion12;

    @FXML
    private TextField TFDireccion42;

    @FXML
    private Pane Hijo;

    @FXML
    private TextField TFDireccion121;

    @FXML
    private TextField TFDireccion421;

    @FXML
    void guardarEmpresa(ActionEvent event) {

    }

    @FXML
    void modificarEmpresa(ActionEvent event) {

    }


    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    /**
     * Called to initialize a controller after its root element has been completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Capitan.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
    }
}

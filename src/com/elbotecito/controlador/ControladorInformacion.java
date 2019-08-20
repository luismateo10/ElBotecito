package com.elbotecito.controlador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorInformacion implements Initializable {

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
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Ave.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
    }
}

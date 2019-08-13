package com.elbotecito.controlador;

import com.elbotecito.bsn.PuertoBsn;
import com.elbotecito.bsn.exception.PuertoYaExisteException;
import com.elbotecito.modelo.Puerto;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorPuerto implements Initializable {

    @FXML
    private ImageView imagenBandera;

    @FXML
    private Button modificarBTN;

    @FXML
    private TextField identificacionTF;

    @FXML
    private TextField nombreTF;

    @FXML
    private TextField ciudadTF;

    @FXML
    private Button guardarBTN;

    @FXML
    private Button eliminarBTN;

    @FXML
    private HBox hBoxImagen;

    PuertoBsn puertoBsn = new PuertoBsn();

    @FXML
    void eliminarPuerto(ActionEvent event) {
        puertoBsn.eliminarPuerto(identificacionTF.getText().toString());

        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(true);
        nombreTF.setDisable(true);
        ciudadTF.setDisable(true);
        identificacionTF.setText("");
        nombreTF.setText("");
        ciudadTF.setText("");
    }

    @FXML
    void guardarPuerto(ActionEvent event) {
        if (!"".equals(nombreTF.getText()) && !"".equals(ciudadTF.getText())) {
            Puerto puerto = new Puerto();
            puerto.setIdentificacion(identificacionTF.getText());
            puerto.setNombre(nombreTF.getText());
            puerto.setCiudad(ciudadTF.getText());

            try {
                puertoBsn.guardarPuerto(puerto);
            } catch (PuertoYaExisteException e) {
                puertoBsn.actualizarPuerto(puerto);
            }


            identificacionTF.setDisable(false);
            identificacionTF.setText("");
            nombreTF.setDisable(true);
            ciudadTF.setDisable(true);

            modificarBTN.setDisable(true);
            eliminarBTN.setDisable(true);

            nombreTF.setText("");
            ciudadTF.setText("");
        } else {
            System.out.println("algun campo vacio");
        }

    }

    @FXML
    void modificarPuerto(ActionEvent event) {
        identificacionTF.setDisable(true);
        nombreTF.setDisable(false);
        ciudadTF.setDisable(false);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(false);
    }

    @FXML
    void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            //Busca el Puerto por Id
            Puerto puerto = new Puerto();
            puerto = puertoBsn.consultarPuerto(identificacionTF.getText().toString());
            //Si el puerto existe habilita los botones
            if (puerto != null) {
                nombreTF.setText(puerto.getNombre());
                ciudadTF.setText(puerto.getCiudad());
                modificarBTN.setDisable(false);
                eliminarBTN.setDisable(false);
            } else {
                //Limpia los campos y los habilita
                nombreTF.setText("");
                ciudadTF.setText("");
                nombreTF.setDisable(false);
                ciudadTF.setDisable(false);
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Puerto.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(true);
        nombreTF.setDisable(true);
        ciudadTF.setDisable(true);
        identificacionTF.setText("");
        nombreTF.setText("");
        ciudadTF.setText("");

    }
}

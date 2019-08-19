package com.elbotecito.controlador;

import com.elbotecito.bsn.PuertoBsn;
import com.elbotecito.bsn.exception.PuertoYaExisteException;
import com.elbotecito.modelo.Puerto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorPuerto implements Initializable {

    @FXML
    private ImageView imagenBandera;

    @FXML
    private JFXTextField identificacionTF;

    @FXML
    private JFXTextField nombreTF;

    @FXML
    private JFXTextField ciudadTF;

    @FXML
    private JFXButton modificarBTN;

    @FXML
    private JFXButton guardarBTN;

    @FXML
    private JFXButton eliminarBTN;

    @FXML
    private HBox hBoxImagen;

    @FXML
    private Pane mensajePane;

    @FXML
    private Label mensajeText;

    private PuertoBsn puertoBsn = new PuertoBsn();

    @FXML
    void eliminarPuerto(ActionEvent event) {
        puertoBsn.eliminarPuerto(identificacionTF.getText());
        desactivarBtns();
        habilitarCampos(false);
        identificacionTF.setText("");
        resetearCampos();

        mensajeText.setText("Puerto eliminado");
        mensajePane.setVisible(true);
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
                mensajeText.setText("Puerto guardado");
                mensajePane.setVisible(true);
            } catch (PuertoYaExisteException e) {
                puertoBsn.actualizarPuerto(puerto);
                mensajeText.setText("Puerto actualizado");
                mensajePane.setVisible(true);
            }

            identificacionTF.setDisable(false);

            habilitarCampos(false);

            desactivarBtns();
        } else {
            mensajeText.setText("Algun campo se encuentra vacio");
            mensajePane.setVisible(true);
        }
    }

    @FXML
    void modificarPuerto(ActionEvent event) {
        identificacionTF.setDisable(true);
        habilitarCampos(true);
        guardarBTN.setDisable(false);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(false);
    }

    @FXML
    void mensajeCerrar(MouseEvent event) {
        mensajePane.setVisible(false);
    }

    @FXML
    void onEnter(KeyEvent event) {
        desactivarBtns();
        habilitarCampos(false);
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
                resetearCampos();
                habilitarCampos(true);
                guardarBTN.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Puerto.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
        mensajePane.setVisible(false);
        desactivarBtns();
        habilitarCampos(false);
        resetearCampos();
    }

    private void desactivarBtns() {
        guardarBTN.setDisable(true);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(true);
    }

    private void resetearCampos() {
        nombreTF.setText("");
        ciudadTF.setText("");
    }

    private void habilitarCampos(Boolean x) {
        nombreTF.setEditable(x);
        ciudadTF.setEditable(x);
    }
}

package com.elbotecito.controlador;

import com.elbotecito.bsn.RutaBsn;
import com.elbotecito.bsn.exception.RutaYaExisteException;
import com.elbotecito.modelo.Ruta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControladorRuta implements Initializable {

    @FXML
    private JFXButton modificarBTN;

    @FXML
    private JFXButton guardarBTN;

    @FXML
    private JFXButton eliminarBTN;

    @FXML
    private JFXTextField numero;

    @FXML
    private JFXTextField matricula;

    @FXML
    private JFXTextField origen;

    @FXML
    private JFXDatePicker fechaOrigen;

    @FXML
    private JFXDatePicker fechaDestino;

    @FXML
    private JFXTextField destino;

    @FXML
    private JFXTextField capitan;

    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    @FXML
    private Pane mensajePane;

    @FXML
    private Label mensajeText;

    @FXML
    private JFXTextField numeroRutaAtraco;

    @FXML
    private JFXTextField puertoAtraco;

    @FXML
    private JFXDatePicker fechaAtraco;

    @FXML
    private JFXButton registrarBTN;

    @FXML
    private Pane registrarAtraco;

    RutaBsn rutaBsn = new RutaBsn();




    @FXML
    void eliminar(ActionEvent event) {
        rutaBsn.eliminarRuta(numero.getText());
        desactivarBtns();
        habilitarCampos(false);
        numero.setText("");
        resetearCampos();

        mensajeText.setText("Puerto eliminado");
        mensajePane.setVisible(true);
    }

    @FXML
    void guardar(ActionEvent event) {
        if (!"".equals(numero.getText()) && !"".equals(matricula.getText())) {
            Ruta ruta = new Ruta();
            ruta.setIdPuertoAtual("");
            ruta.setNumero(numero.getText());
            ruta.setMatriculaBarco(matricula.getText());
            ruta.setIdPuertoOrigen(origen.getText());
            ruta.setIdPuertoDestino(origen.getText());
            ruta.setIdCapitan(origen.getText());
            ruta.setFechaPuertoDestino(fechaDestino.getValue().toString());
            ruta.setFechaPuertoOrigen(fechaOrigen.getValue().toString());

            try {
                rutaBsn.guardarRuta(ruta);
                mensajeText.setText("Ruta guardado");
                mensajePane.setVisible(true);
            } catch (RutaYaExisteException e) {
                rutaBsn.actualizarRuta(ruta);
                mensajeText.setText("Ruta actualizado");
                mensajePane.setVisible(true);
            }

            numero.setDisable(false);

            habilitarCampos(false);

            desactivarBtns();
        } else {
            mensajeText.setText("Algun campo se encuentra vacio");
            mensajePane.setVisible(true);
        }
    }

    @FXML
    void mensajeCerrar(MouseEvent event) {
        mensajePane.setVisible(false);
    }

    @FXML
    void modificar(ActionEvent event) {
        matricula.setDisable(true);
        habilitarCampos(true);
        guardarBTN.setDisable(false);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(false);
    }

    @FXML
    void onEnter(KeyEvent event) {
        desactivarBtns();
        habilitarCampos(false);
        if (event.getCode() == KeyCode.ENTER) {
            //Busca el Puerto por Id
            Ruta ruta = new Ruta();
            ruta = rutaBsn.consultarRuta(numero.getText().toString());
            //Si el puerto existe habilita los botones
            if (ruta != null) {
                matricula.setText(ruta.getMatriculaBarco());
                origen.setText(ruta.getIdPuertoOrigen());
                fechaOrigen.setValue(LocalDate.parse(ruta.getFechaPuertoOrigen()));
                destino.setText(ruta.getIdPuertoDestino());
                fechaDestino.setValue(LocalDate.parse(ruta.getFechaPuertoDestino()));
                capitan.setText(ruta.getIdCapitan());
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

    @FXML
    void registrar(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Ruta.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
        mensajePane.setVisible(false);
        registrarAtraco.setVisible(false);
        numero.setText("");
        desactivarBtns();
        habilitarCampos(false);
        resetearCampos();
    }

    private void desactivarBtns() {
        guardarBTN.setDisable(true);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(true);
        registrarBTN.setDisable(true);
    }

    private void resetearCampos() {
        matricula.setText("");
        origen.setText("");
        fechaOrigen.setValue(null);
        destino.setText("");
        fechaDestino.setValue(null);
        capitan.setText("");
    }

    private void habilitarCampos(boolean x) {
        matricula.setEditable(x);
        origen.setEditable(x);
        fechaOrigen.setEditable(x);
        destino.setEditable(x);
        fechaDestino.setEditable(x);
        capitan.setEditable(x);
    }


}

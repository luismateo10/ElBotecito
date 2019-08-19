package com.elbotecito.controlador;

import com.elbotecito.bsn.BarcoBsn;
import com.elbotecito.bsn.exception.BarcoYaExisteException;
import com.elbotecito.modelo.Barco;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControladorBarco implements Initializable {
    @FXML
    private JFXButton modificarBarcoBTN;

    @FXML
    private JFXComboBox<String> tipoBarco;

    @FXML
    private JFXDatePicker fechRegMerc;

    @FXML
    private JFXTextField estado;

    @FXML
    private JFXTextField matricula;

    @FXML
    private JFXTextField nombre;

    @FXML
    private JFXTextField capacidadMax;

    @FXML
    private JFXTextField numRegMerc;

    @FXML
    private JFXButton guardarBarcoBTN;

    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    @FXML
    private JFXButton eliminarBarcoBTN;

    private BarcoBsn barcoBsn = new BarcoBsn();

    @FXML
    void eliminarBarco(ActionEvent event) {
        barcoBsn.eliminarBarco(matricula.getText());
        desactivarBtns();
        habilitarCampos(false);
        matricula.setText("");
        resetearCampos();
    }

    @FXML
    void guadarBarco(ActionEvent event) {
        if (!"".equals(nombre.getText()) && !"".equals(numRegMerc.getText())) {
            Barco barco = new Barco(matricula.getText(), capacidadMax.getText(), numRegMerc.getText(), fechRegMerc.getValue().toString(),
                    nombre.getText(), "", tipoBarco.getValue());

            try {
                barcoBsn.guardarBarco(barco);
            } catch (BarcoYaExisteException e) {
                barcoBsn.actualizarBarco(barco);
            }

            matricula.setDisable(false);

            habilitarCampos(false);

            desactivarBtns();
        } else {
            System.out.println("Algún campo vacío");
        }
    }

    @FXML
    void modificarBarco(ActionEvent event) {
        matricula.setDisable(true);
        habilitarCampos(true);
        guardarBarcoBTN.setDisable(false);
        modificarBarcoBTN.setDisable(true);
        eliminarBarcoBTN.setDisable(false);
    }

    @FXML
    void onEnterPressed(KeyEvent event) {
        desactivarBtns();
        habilitarCampos(false);
        if (event.getCode() == KeyCode.ENTER) {
            //Busca el Barco por matricula
            Barco barco = new Barco();
            barco = barcoBsn.consultarBarco(matricula.getText().toString());
            //Si el puerto existe habilita los botones
            if (barco != null) {
                //Muestro informacion
                nombre.setText(barco.getNombre());
                tipoBarco.valueProperty().set(barco.getTipoBarco());
                numRegMerc.setText(barco.getNumeroRegMerc());
                fechRegMerc.setValue(LocalDate.parse(barco.getFechaRegMerc()));
                capacidadMax.setText(barco.getCapacidadMax());
                estado.setText(barco.getEstado());
                //habilito los botones
                modificarBarcoBTN.setDisable(false);
                eliminarBarcoBTN.setDisable(false);
            } else {
                //Limpia los campos y los habilita
                resetearCampos();
                habilitarCampos(true);
                guardarBarcoBTN.setDisable(false);

            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Barco.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);

        //Iniciar Combobox de Tipos de barcos
        tipoBarco.getItems().setAll("Barco", "Chalupa", "Fragata", "Velero");

        //Desactivar botones de guardar, modificar y eliminar
        desactivarBtns();

        //Desactivar todos los campos menos la matricula
        habilitarCampos(false);
        estado.setDisable(true);
        //Resetear todos los campos
        resetearCampos();


    }

    private void desactivarBtns() {
        guardarBarcoBTN.setDisable(true);
        modificarBarcoBTN.setDisable(true);
        eliminarBarcoBTN.setDisable(true);
    }

    private void resetearCampos() {
        nombre.setText("");
        tipoBarco.valueProperty().set(null);
        numRegMerc.setText("");
        fechRegMerc.setValue(null);
        capacidadMax.setText("");
        estado.setText("");
    }

    private void habilitarCampos(Boolean x) {
        nombre.setEditable(x);
        tipoBarco.setEditable(x);
        tipoBarco.setEditable(x);
        numRegMerc.setEditable(x);
        fechRegMerc.setEditable(x);
        capacidadMax.setEditable(x);


    }
}

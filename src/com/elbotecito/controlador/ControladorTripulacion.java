package com.elbotecito.controlador;

import com.elbotecito.bsn.PersonaBsn;
import com.elbotecito.bsn.exception.BarcoYaExisteException;
import com.elbotecito.bsn.exception.PersonaYaExisteException;
import com.elbotecito.modelo.Capitan;
import com.elbotecito.modelo.Esposa;
import com.elbotecito.modelo.Hijo;
import com.elbotecito.modelo.Marinero;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private JFXButton modificarBTN;

    @FXML
    private JFXButton guardarBTN;

    @FXML
    private JFXButton eliminarBTN;

    @FXML
    private JFXTextField IdentificacionTF;

    @FXML
    private JFXTextField nombreTF;

    @FXML
    private JFXComboBox<String> rolCB;

    @FXML
    private Pane paneCapitan;

    @FXML
    private JFXTextField capitanNumEsposasTF;

    @FXML
    private JFXTextField capitanNumHijos;

    @FXML
    private JFXTextField capitanFortunaTF;

    @FXML
    private Pane paneEsposa;

    @FXML
    private JFXTextField esposaEsposoTF;

    @FXML
    private JFXTextField esposaHerenciaTF;

    @FXML
    private Pane paneHijo;

    @FXML
    private JFXTextField hijoPadreTF;

    @FXML
    private JFXTextField hijoHerenciaTF;

    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    @FXML
    private JFXRadioButton generoMujer;
    @FXML
    private JFXRadioButton generoHombre;
    @FXML
    private JFXRadioButton esVivo;
    @FXML
    private JFXRadioButton esMuerto;

    private ToggleGroup groupGenero = new ToggleGroup();

    private ToggleGroup groupEstadoVivo = new ToggleGroup();

    private PersonaBsn personaBsn = new PersonaBsn();

    @FXML
    void eliminarPersona(ActionEvent event) {
        paneCapitan.setVisible(false);
        paneEsposa.setVisible(false);
        paneHijo.setVisible(false);
        personaBsn.eliminarCapitan(IdentificacionTF.getText());
        personaBsn.eliminarEsposa(IdentificacionTF.getText());
        personaBsn.eliminarMarinero(IdentificacionTF.getText());
        personaBsn.eliminarHijo(IdentificacionTF.getText());
        desactivarBtns();
        habilitarCampos(false);
        IdentificacionTF.setText("");
        resetearCampos();
    }

    @FXML
    void guardarPersona(ActionEvent event) {
        paneCapitan.setVisible(false);
        paneEsposa.setVisible(false);
        paneHijo.setVisible(false);
        if (!"".equals(nombreTF.getText())) {
            switch (rolCB.getValue()) {
                case "Capitan":
                    Capitan capitan = new Capitan();
                    capitan.setIdentificacion(IdentificacionTF.getText());
                    capitan.setNombre(nombreTF.getText());
                    capitan.setSexo("1");
                    if (groupEstadoVivo.getSelectedToggle() == esVivo) {
                        capitan.setEstadoVivo("1");
                    } else {
                        capitan.setEstadoVivo("0");
                    }
                    capitan.setNumEsposas(capitanNumEsposasTF.getText());
                    capitan.setNumHijos(capitanNumHijos.getText());
                    capitan.setFortuna(capitanFortunaTF.getText());
                    capitan.setIdRuta("");
                    try {
                        personaBsn.guardarCapitan(capitan);
                    } catch (PersonaYaExisteException e) {
                        personaBsn.actualizarCapitan(capitan);
                    }

                    break;
                case "Marinero":
                    Marinero marinero = new Marinero();
                    marinero.setIdentificacion(IdentificacionTF.getText());
                    marinero.setNombre(nombreTF.getText());
                    if (groupGenero.getSelectedToggle() == generoHombre) {
                        marinero.setSexo("1");
                    } else {
                        marinero.setSexo("0");
                    }
                    if (groupEstadoVivo.getSelectedToggle() == esVivo) {
                        marinero.setEstadoVivo("1");
                    } else {
                        marinero.setEstadoVivo("0");
                    }
                    marinero.setIdRuta("");
                    try {
                        personaBsn.guardarMarinero(marinero);
                    } catch (PersonaYaExisteException e) {
                        personaBsn.actualizarMarinero(marinero);
                    }
                    break;
                case "Esposa":
                    Esposa esposa = new Esposa();
                    esposa.setIdentificacion(IdentificacionTF.getText());
                    esposa.setNombre(nombreTF.getText());
                    esposa.setSexo("0");
                    if (groupEstadoVivo.getSelectedToggle() == esVivo) {
                        esposa.setEstadoVivo("1");
                    } else {
                        esposa.setEstadoVivo("0");
                    }
                    esposa.setEsposo(esposaEsposoTF.getText());
                    esposa.setPorcHerencia(esposaHerenciaTF.getText());
                    try {
                        personaBsn.guardarEsposa(esposa);
                    } catch (PersonaYaExisteException e) {
                        personaBsn.actualizarEsposa(esposa);
                    }

                    break;
                case "Hijo":
                    Hijo hijo = new Hijo();
                    hijo.setIdentificacion(IdentificacionTF.getText());
                    hijo.setNombre(nombreTF.getText());
                    if (groupGenero.getSelectedToggle() == generoHombre) {
                        hijo.setSexo("1");
                    } else {
                        hijo.setSexo("0");
                    }
                    if (groupEstadoVivo.getSelectedToggle() == esVivo) {
                        hijo.setEstadoVivo("1");
                    } else {
                        hijo.setEstadoVivo("0");
                    }
                    hijo.setIdPadre(hijoPadreTF.getText());
                    hijo.setPorcHerencia(hijoHerenciaTF.getText());
                    try {
                        personaBsn.guardarHijo(hijo);
                    } catch (PersonaYaExisteException e) {
                        personaBsn.actualizarHijo(hijo);
                    }

                    break;
            }

            IdentificacionTF.setDisable(false);

            habilitarCampos(false);

            desactivarBtns();
        } else {
            System.out.println("Algún campo vacío");
        }

    }

    @FXML
    void modificarPersona(ActionEvent event) {
        IdentificacionTF.setDisable(true);
        habilitarCampos(true);

        rolCB.setDisable(true);
        if (rolCB.getValue() != null) {
            switch (rolCB.getValue()) {
                case "Capitan":
                    paneCapitan.setVisible(true);
                    generoHombre.setSelected(true);
                    generoHombre.setDisable(true);
                    generoMujer.setDisable(true);
                    break;
                case "Esposa":
                    paneEsposa.setVisible(true);
                    generoHombre.setSelected(true);
                    generoHombre.setDisable(true);
                    generoMujer.setDisable(true);
                    break;
                case "Hijo":
                    paneHijo.setVisible(true);
                    break;
            }
        }

        guardarBTN.setDisable(false);
        modificarBTN.setDisable(true);
        eliminarBTN.setDisable(false);
    }

    @FXML
    void onEnter(KeyEvent event) {
        paneCapitan.setVisible(false);
        paneEsposa.setVisible(false);
        paneHijo.setVisible(false);
        desactivarBtns();
        habilitarCampos(false);
        if (event.getCode() == KeyCode.ENTER) {

            Capitan capitan = new Capitan();
            Marinero marinero = new Marinero();
            Esposa esposa = new Esposa();
            Hijo hijo = new Hijo();

            capitan = personaBsn.consultarCapitan(IdentificacionTF.getText());
            marinero = personaBsn.consultarMarinero(IdentificacionTF.getText());
            esposa = personaBsn.consultarEsposa(IdentificacionTF.getText());
            hijo = personaBsn.consultarHijo(IdentificacionTF.getText());

            if (capitan != null || marinero != null || esposa != null || hijo != null) {
                if (capitan != null) {
                    nombreTF.setText(capitan.getNombre());
                    groupGenero.selectToggle(generoHombre);
                    if (capitan.getEstadoVivo().equals("1")) {
                        groupEstadoVivo.selectToggle(esVivo);
                    } else {
                        groupEstadoVivo.selectToggle(esMuerto);
                    }

                    rolCB.setValue("Capitan");
                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Capitan.png"));
                    //capitan
                    capitanNumEsposasTF.setText(capitan.getNumEsposas());
                    capitanNumHijos.setText(capitan.getNumHijos());
                    capitanFortunaTF.setText(capitan.getFortuna());
                    modificarBTN.setDisable(false);
                    eliminarBTN.setDisable(false);
                    paneCapitan.setVisible(true);
                } else if (marinero != null) {
                    nombreTF.setText(marinero.getNombre());
                    if (marinero.getSexo().equals("1")) {
                        groupGenero.selectToggle(generoHombre);
                    } else {
                        groupGenero.selectToggle(generoMujer);
                    }

                    if (marinero.getEstadoVivo().equals("1")) {
                        groupEstadoVivo.selectToggle(esVivo);
                    } else {
                        groupEstadoVivo.selectToggle(esMuerto);
                    }

                    rolCB.setValue("Marinero");
                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Marinero.png"));
                    modificarBTN.setDisable(false);
                    eliminarBTN.setDisable(false);

                } else if (esposa != null) {
                    nombreTF.setText(esposa.getNombre());
                    groupGenero.selectToggle(generoMujer);
                    if (esposa.getEstadoVivo().equals("1")) {
                        groupEstadoVivo.selectToggle(esVivo);
                    } else {
                        groupEstadoVivo.selectToggle(esMuerto);
                    }

                    rolCB.setValue("Esposa");
                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Esposa.png"));
                    //Esposa
                    esposaEsposoTF.setText(esposa.getEsposo());
                    esposaHerenciaTF.setText(esposa.getPorcHerencia());
                    modificarBTN.setDisable(false);
                    eliminarBTN.setDisable(false);
                    paneEsposa.setVisible(true);
                } else if (hijo != null) {
                    nombreTF.setText(hijo.getNombre());
                    if (hijo.getSexo().equals("1")) {
                        groupGenero.selectToggle(generoHombre);
                    } else {
                        groupGenero.selectToggle(generoMujer);
                    }

                    if (hijo.getEstadoVivo().equals("1")) {
                        groupEstadoVivo.selectToggle(esVivo);
                    } else {
                        groupEstadoVivo.selectToggle(esMuerto);
                    }

                    rolCB.setValue("Hijo");
                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Hijo.png"));
                    //Hijo
                    hijoPadreTF.setText(hijo.getIdPadre());
                    hijoHerenciaTF.setText(hijo.getPorcHerencia());
                    modificarBTN.setDisable(false);
                    eliminarBTN.setDisable(false);
                    paneHijo.setVisible(true);
                }


            } else {
                resetearCampos();
                habilitarCampos(true);

                rolCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> selected, String oldRol, String newRol) {
                        if (oldRol != null) {
                            switch (oldRol) {
                                case "Capitan":
                                    paneCapitan.setVisible(false);
                                    break;
                                case "Esposa":
                                    paneEsposa.setVisible(false);
                                    break;
                                case "Hijo":
                                    paneHijo.setVisible(false);
                                    break;
                            }
                        }
                        if (newRol != null) {
                            switch (newRol) {
                                case "Capitan":
                                    paneCapitan.setVisible(true);
                                    rolCB.setDisable(true);
                                    groupGenero.selectToggle(generoHombre);
                                    generoHombre.setDisable(true);
                                    generoMujer.setDisable(true);
                                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Capitan.png"));
                                    break;
                                case "Esposa":
                                    paneEsposa.setVisible(true);
                                    rolCB.setDisable(true);
                                    groupGenero.selectToggle(generoMujer);
                                    generoHombre.setDisable(true);
                                    generoMujer.setDisable(true);
                                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Esposa.png"));
                                    break;
                                case "Hijo":
                                    paneHijo.setVisible(true);
                                    rolCB.setDisable(true);
                                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Hijo.png"));
                                    break;
                                case "Marinero":
                                    paneHijo.setVisible(false);
                                    paneCapitan.setVisible(false);
                                    paneEsposa.setVisible(false);
                                    rolCB.setDisable(true);
                                    imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Marinero.png"));
                                    break;
                            }
                        }
                    }
                });
                guardarBTN.setDisable(false);
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Capitan.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
        //Iniciar toggleButtons

        generoHombre.setToggleGroup(groupGenero);
        generoMujer.setToggleGroup(groupGenero);
        generoHombre.setSelected(true);

        esMuerto.setToggleGroup(groupEstadoVivo);
        esVivo.setToggleGroup(groupEstadoVivo);
        esVivo.setSelected(true);
        //Iniciar Combobox de Roles
        rolCB.getItems().setAll("Capitan", "Marinero", "Esposa", "Hijo");

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
        generoHombre.setSelected(true);
        esVivo.setSelected(true);
        rolCB.setValue(null);

        //capitan
        capitanNumEsposasTF.setText("");
        capitanNumHijos.setText("");
        capitanFortunaTF.setText("");
        //Esposa
        esposaEsposoTF.setText("");
        esposaHerenciaTF.setText("");
        //Hijo
        hijoPadreTF.setText("");
        hijoHerenciaTF.setText("");
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Capitan.png"));
    }

    private void habilitarCampos(Boolean x) {
        nombreTF.setDisable(!x);
        generoHombre.setDisable(!x);
        generoMujer.setDisable(!x);
        esVivo.setDisable(!x);
        esMuerto.setDisable(!x);
        rolCB.setDisable(!x);

        //capitan
        capitanNumEsposasTF.setDisable(!x);
        capitanNumHijos.setDisable(!x);
        capitanFortunaTF.setDisable(!x);
        //Esposa
        esposaEsposoTF.setDisable(!x);
        esposaHerenciaTF.setDisable(!x);
        //Hijo
        hijoPadreTF.setDisable(!x);
        hijoHerenciaTF.setDisable(!x);
    }
}

package com.elbotecito.controlador;

import com.elbotecito.bsn.*;
import com.elbotecito.modelo.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.ResourceBundle;

public class ControladorReporte implements Initializable {
    @FXML
    private HBox hBoxImagen;

    @FXML
    private ImageView imagenBandera;

    /************************
     * Tabla de Barcos      *
     ************************/
    @FXML
    private TableView<Barco> barcos;

    @FXML
    private TableColumn<Barco, String> matriculaBarco;  //PK
    @FXML
    private TableColumn<Barco, String> capacidadMaxBarco;
    @FXML
    private TableColumn<Barco, String> numeroRegMercBarco;
    @FXML
    private TableColumn<Barco, String> fechaRegMercBarco;
    @FXML
    private TableColumn<Barco, String> nombreBarco;
    @FXML
    private TableColumn<Barco, String> estadoBarco;
    @FXML
    private TableColumn<Barco, String> tipoBarcoBarco;

    /************************
     * Tabla de puertos     *
     ************************/
    @FXML
    private TableView<Puerto> puertos;
    @FXML
    private TableColumn<Puerto, String> identificacionPuerto;
    @FXML
    private TableColumn<Puerto, String> nombrePuerto;
    @FXML
    private TableColumn<Puerto, String> ciudadPuerto;

    /************************
     * Tabla de capitanes   *
     ************************/
    @FXML
    private TableView<Capitan> capitanes;
    @FXML
    private TableColumn<Capitan, String> identificacionCapitan;
    @FXML
    private TableColumn<Capitan, String> nombreCapitan;
    @FXML
    private TableColumn<Capitan, String> sexoCapitan;
    @FXML
    private TableColumn<Capitan, String> estadoVivoCapitan;
    @FXML
    private TableColumn<Capitan, String> idRutaCapitan;
    @FXML
    private TableColumn<Capitan, String> numHijosCapitan;
    @FXML
    private TableColumn<Capitan, String> numEsposasCapitan;
    @FXML
    private TableColumn<Capitan, String> fortunaCapitan;

    /************************
     * Tabla de marineros   *
     ************************/
    @FXML
    private TableView<Marinero> marineros;
    @FXML
    private TableColumn<Marinero, String> identificacionMarinero;
    @FXML
    private TableColumn<Marinero, String> nombreMarinero;
    @FXML
    private TableColumn<Marinero, String> sexoMarinero;
    @FXML
    private TableColumn<Marinero, String> estadoVivoMarinero;
    @FXML
    private TableColumn<Marinero, String> idRutaMarinero;

    /************************
     * Tabla de esposas     *
     ************************/
    @FXML
    private TableView<Esposa> esposas;
    @FXML
    private TableColumn<Esposa, String> identificacionEsposa;
    @FXML
    private TableColumn<Esposa, String> nombreEsposa;
    @FXML
    private TableColumn<Esposa, String> sexoEsposa;
    @FXML
    private TableColumn<Esposa, String> estadoVivoEsposa;
    @FXML
    private TableColumn<Esposa, String> esposoEsposa;
    @FXML
    private TableColumn<Esposa, String> porcHerenciaEsposa;

    /************************
     * Tabla de hijos       *
     ************************/
    @FXML
    private TableView<Hijo> hijos;
    @FXML
    private TableColumn<Hijo, String> identificacionHijo;
    @FXML
    private TableColumn<Hijo, String> nombreHijo;
    @FXML
    private TableColumn<Hijo, String> sexoHijo;
    @FXML
    private TableColumn<Hijo, String> estadoVivoHijo;
    @FXML
    private TableColumn<Hijo, String> idPadreHijo;
    @FXML
    private TableColumn<Hijo, String> porcHerenciaHijo;


    /************************
     * Tabla de escalas      *
     ************************/
    @FXML
    private TableView<Escala> escalas;
    @FXML
    private TableColumn<Escala, String> idRutaEscala;
    @FXML
    private TableColumn<Escala, String> idPuertoEscala;
    @FXML
    private TableColumn<Escala, String> fechaEscalaEscala;

    /************************
     * Tabla de rutas      *
     ************************/
    @FXML
    private TableView<Ruta> rutas;
    @FXML
    private TableColumn<Ruta, String> numeroRuta;
    @FXML
    private TableColumn<Ruta, String> matriculaBarcoRuta;
    @FXML
    private TableColumn<Ruta, String> idPuertoOrigenRuta;
    @FXML
    private TableColumn<Ruta, String> fechaPuertoOrigenRuta;
    @FXML
    private TableColumn<Ruta, String> idPuertoDestinoRuta;
    @FXML
    private TableColumn<Ruta, String> fechaPuertoDestinoRuta;
    @FXML
    private TableColumn<Ruta, String> idPuertoAtualRuta;
    @FXML
    private TableColumn<Ruta, String> idCapitanRuta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenBandera.setImage(new Image("com/elbotecito/vista/imagenes/Encomienda.png"));
        hBoxImagen.setAlignment(Pos.TOP_CENTER);
        /************************
         * Tabla de Barcos      *
         ************************/
        matriculaBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("matricula"));
        capacidadMaxBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("capacidadMax"));
        numeroRegMercBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("numeroRegMerc"));
        fechaRegMercBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("fechaRegMerc"));
        nombreBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("nombre"));
        estadoBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("estado"));
        tipoBarcoBarco.setCellValueFactory(new PropertyValueFactory<Barco, String>("tipoBarco"));
        BarcoBsn barcoBsn = new BarcoBsn();
        barcos.getItems().setAll(barcoBsn.consultarBarcos());

        /************************
         * Tabla de puertos     *
         ************************/
        identificacionPuerto.setCellValueFactory(new PropertyValueFactory<Puerto, String>("identificacion"));
        nombrePuerto.setCellValueFactory(new PropertyValueFactory<Puerto, String>("nombre"));
        ciudadPuerto.setCellValueFactory(new PropertyValueFactory<Puerto, String>("ciudad"));
        PuertoBsn puertoBsn = new PuertoBsn();
        puertos.getItems().setAll(puertoBsn.consultarPuertos());

        /************************
         * Tabla de capitanes   *
         ************************/
        identificacionCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("identificacion"));
        nombreCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("nombre"));
        sexoCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("sexo"));
        estadoVivoCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("estadoVivo"));
        idRutaCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("idRuta"));
        numHijosCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("numHijos"));
        numEsposasCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("numEsposas"));
        fortunaCapitan.setCellValueFactory(new PropertyValueFactory<Capitan, String>("fortuna"));
        PersonaBsn personaBsn = new PersonaBsn();
        capitanes.getItems().setAll(personaBsn.consultarCapitanes());

        /************************
         * Tabla de marineros   *
         ************************/
        identificacionMarinero.setCellValueFactory(new PropertyValueFactory<Marinero, String>("identificacion"));
        nombreMarinero.setCellValueFactory(new PropertyValueFactory<Marinero, String>("nombre"));
        sexoMarinero.setCellValueFactory(new PropertyValueFactory<Marinero, String>("sexo"));
        estadoVivoMarinero.setCellValueFactory(new PropertyValueFactory<Marinero, String>("estadoVivo"));
        idRutaMarinero.setCellValueFactory(new PropertyValueFactory<Marinero, String>("idRuta"));
        marineros.getItems().setAll(personaBsn.consultarMarineros());

        /************************
         * Tabla de esposas     *
         ************************/
        identificacionEsposa.setCellValueFactory(new PropertyValueFactory<Esposa, String>("identificacion"));
        nombreEsposa.setCellValueFactory(new PropertyValueFactory<Esposa, String>("nombre"));
        sexoEsposa.setCellValueFactory(new PropertyValueFactory<Esposa, String>("sexo"));
        estadoVivoEsposa.setCellValueFactory(new PropertyValueFactory<Esposa, String>("estadoVivo"));
        esposoEsposa.setCellValueFactory(new PropertyValueFactory<Esposa, String>("esposo"));
        porcHerenciaEsposa.setCellValueFactory(new PropertyValueFactory<Esposa, String>("porcHerencia"));
        esposas.getItems().setAll(personaBsn.consultarEsposas());

        /************************
         * Tabla de hijos       *
         ************************/
        identificacionHijo.setCellValueFactory(new PropertyValueFactory<Hijo, String>("identificacion"));
        nombreHijo.setCellValueFactory(new PropertyValueFactory<Hijo, String>("nombre"));
        sexoHijo.setCellValueFactory(new PropertyValueFactory<Hijo, String>("sexo"));
        estadoVivoHijo.setCellValueFactory(new PropertyValueFactory<Hijo, String>("estadoVivo"));
        idPadreHijo.setCellValueFactory(new PropertyValueFactory<Hijo, String>("idPadre"));
        porcHerenciaHijo.setCellValueFactory(new PropertyValueFactory<Hijo, String>("porcHerencia"));
        hijos.getItems().setAll(personaBsn.consultarHijos());

        /************************
         * Tabla de escalas      *
         ************************/
        idRutaEscala.setCellValueFactory(new PropertyValueFactory<Escala, String>("idRuta"));
        idPuertoEscala.setCellValueFactory(new PropertyValueFactory<Escala, String>("idPuerto"));
        fechaEscalaEscala.setCellValueFactory(new PropertyValueFactory<Escala, String>("fechaEscala"));
        EscalaBsn escalaBsn = new EscalaBsn();
        escalas.getItems().setAll(escalaBsn.consultarEscalas());

        /************************
         * Tabla de rutas      *
         ************************/
        numeroRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("numero"));
        matriculaBarcoRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("matriculaBarco"));
        idPuertoOrigenRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("idPuertoOrigen"));
        fechaPuertoOrigenRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("fechaPuertoOrigen"));
        idPuertoDestinoRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("idPuertoDestino"));
        fechaPuertoDestinoRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("fechaPuertoDestino"));
        idPuertoAtualRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("idPuertoAtual"));
        idCapitanRuta.setCellValueFactory(new PropertyValueFactory<Ruta, String>("idCapitan"));
        RutaBsn rutaBsn = new RutaBsn();
        rutas.getItems().setAll(rutaBsn.consultarRutas());
    }

}

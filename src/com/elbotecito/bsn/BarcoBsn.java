package com.elbotecito.bsn;

import com.elbotecito.bsn.exception.BarcoYaExisteException;
import com.elbotecito.dao.BarcoDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.dao.impl.BarcoDAONIO;
import com.elbotecito.modelo.Barco;
import javafx.scene.control.Alert;

import java.util.List;

public class BarcoBsn {


    private BarcoDAO barcoDAO;

    public BarcoBsn(){
        this.barcoDAO = new BarcoDAONIO(); {
        }
    }
    public void guardarBarco(Barco barco) throws BarcoYaExisteException{
        try {
            this.barcoDAO.guardarBarco(barco);
        }catch (LlaveDuplicadaException lde){
            throw new BarcoYaExisteException();
        }
    }

    public static void main(String[] args) {
        BarcoBsn barcoBsn = new BarcoBsn();
        Barco barco1 = new Barco("999","230","12334","26/05/2020","La eliza", "zarpado","fragata");

        try {
            barcoBsn.guardarBarco(barco1);
        }catch (BarcoYaExisteException eyee){
            Alert a = new Alert(Alert.AlertType.ERROR);
            System.out.println(eyee.getMessage());
            eyee.printStackTrace();
        }
    }

    /*
    private BarcoDAO empleadoDAO;
    private HerramientaDAO herramientaDAO;

    public EmpleadoBsn(){
        this.empleadoDAO = new EmpleadoDAONIO();
        this.herramientaDAO = new HerramientaDAONIO();
    }

    public void guardarEmpleado(Empleado empleado) throws EmpleadoYaExisteException {
        //todo validar reglas de negocio
        //todo aplicar lógica de casos de uso
        System.out.println("En el bsn");
        try {
            this.empleadoDAO.guardarEmpleado(empleado);
        }catch (LlaveDuplicadaException lde){
            throw new EmpleadoYaExisteException();
        }
    }

    public Empleado consultarEmpleado(String identificacion){
        return this.empleadoDAO.consultarEmpleadoPorIdentificacion(identificacion);
    }

    public List<Herramienta> consultarHerramientasEmpleado(String identificacion){
        return this.herramientaDAO.consultarHerramientasPorIdEmpleado(identificacion);
    }

    public void almacenarHerramienta(Herramienta herramienta) throws HerramientaYaExisteException {
        try {
            this.herramientaDAO.guardarHerramienta(herramienta);
        }catch (LlaveDuplicadaException lde){
            throw new HerramientaYaExisteException();
        }
    }


    public static void main(String[] args) {
        /*Herramienta herramienta = new Herramienta("001", "Martillo", "123");
        Herramienta herramienta2 = new Herramienta("002", "Taladro", "123");



        try {
            empleadoBsn.almacenarHerramienta(herramienta);
            empleadoBsn.almacenarHerramienta(herramienta2);
        }catch (HerramientaYaExisteException hyee){
            hyee.printStackTrace();
        }*/

/*
        EmpleadoBsn empleadoBsn = new EmpleadoBsn();
        List<Herramienta> herramientas = empleadoBsn.consultarHerramientasEmpleado("12");
        for(Herramienta herramienta: herramientas){
            System.out.println(herramienta);
        }
    }
    */

}

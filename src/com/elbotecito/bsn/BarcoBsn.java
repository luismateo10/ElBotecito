package com.elbotecito.bsn;

import com.elbotecito.bsn.exception.BarcoYaExisteException;
import com.elbotecito.dao.*;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.dao.impl.BarcoDAONIO;
import com.elbotecito.dao.impl.HijoDAONIO;
import com.elbotecito.modelo.Barco;
import com.elbotecito.modelo.Hijo;
import javafx.scene.control.Alert;
import jdk.nashorn.internal.objects.NativeArray;

import java.util.List;

public class BarcoBsn {


    private BarcoDAO barcoDAO;
    private CapitanDAO capitanDAO;
    private HijoDAO hijoDAO;
    private EsposaDAO esposaDAO;
    private MarineroDAO marineroDAO;

    public BarcoBsn() {
        this.barcoDAO = new BarcoDAONIO();
    }

    public void guardarBarco(Barco barco) throws BarcoYaExisteException {
        try {
            this.barcoDAO.guardarBarco(barco);
        } catch (LlaveDuplicadaException lde) {
            throw new BarcoYaExisteException();
        }
    }

    public Barco consultarBarco(String matricula) {
        return this.barcoDAO.consultarBarcoPorMatricula(matricula);
    }


    public List<Barco> consultarBarcos() {
        List<Barco> listaBarcos = this.barcoDAO.consultarBarcos();
        return listaBarcos;

    }

    public void actualizarBarco(Barco barco) {
        this.barcoDAO.actualizarBarco(barco);
    }

    public void eliminarBArco(String matricula) {
        this.barcoDAO.eliminarBarco(matricula);

    }


    public static void main(String[] args) throws LlaveDuplicadaException, BarcoYaExisteException {
        BarcoBsn barcoBsn = new BarcoBsn();
        Barco barco1 = new Barco("123", "230", "12334", "26/05/2020", "a", "zarpado", "fragataA");
        Barco barco2 = new Barco("1234", "230", "12334", "26/05/2020", "b", "zarpado", "fragataB");
        Barco barco3 = new Barco("12345", "230", "12334", "26/05/2020", "c", "zarpado", "fragataC");
        Barco barco4 = new Barco("123456", "230", "12334", "26/05/2020", "d", "zarpado", "fragataD");

        Hijo hijo = new Hijo("123", "Juan", "M", "vivo", "el papa", "30");
        HijoDAONIO hijoDAONIO = new HijoDAONIO();
        hijoDAONIO.guardarHijo(hijo);
        barcoBsn.guardarBarco(barco1);
        barcoBsn.guardarBarco(barco2);
        barcoBsn.guardarBarco(barco3);
        barcoBsn.guardarBarco(barco4);

        //-------------1
        System.out.println("Primera consulta");
        System.out.println(barcoBsn.consultarBarco("999"));

        System.out.println("\n Consultar barcos Primera vez \n");
        for (Barco barco : barcoBsn.consultarBarcos()) {
            System.out.println(barco);
        }
        //-----------Elimino un barco para probar
        barcoBsn.eliminarBArco("123");

        //-----------Imprimo
        System.out.println("\n Consultar Barcos Segunda vez\n");
        barcoBsn.consultarBarcos();
        for (Barco barco : barcoBsn.consultarBarcos()) {
            System.out.println(barco);
        }
        //-----------Actualizo un Barco
        barco4.setCapacidadMax("999");
        barco4.setFechaRegMerc("15/20/3000");
        barcoBsn.actualizarBarco(barco4);
        //Imprimo----------------------
        System.out.println("\n Consultar Barcos Segunda vez\n");
        barcoBsn.consultarBarcos();
        for (Barco barco : barcoBsn.consultarBarcos()) {
            System.out.println(barco);
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

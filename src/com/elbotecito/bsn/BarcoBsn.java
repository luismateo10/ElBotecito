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

    public boolean actualizarBarco(Barco barco) {
        return this.barcoDAO.actualizarBarco(barco);
    }

    public boolean eliminarBarco(String matricula) {
        return this.barcoDAO.eliminarBarco(matricula);

    }

}

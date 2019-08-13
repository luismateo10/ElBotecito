package com.elbotecito.bsn;

import com.elbotecito.bsn.exception.PuertoYaExisteException;
import com.elbotecito.dao.PuertoDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.dao.impl.PuertoDAONIO;
import com.elbotecito.modelo.Puerto;

import java.util.List;

public class PuertoBsn {


    private PuertoDAO puertoDAO;

    public PuertoBsn() {
        this.puertoDAO = new PuertoDAONIO();
    }

    public void guardarPuerto(Puerto puerto) throws PuertoYaExisteException {
        try {
            this.puertoDAO.guardarPuerto(puerto);
        } catch (LlaveDuplicadaException lde) {
            throw new PuertoYaExisteException();
        }
    }

    public Puerto consultarPuerto(String identificacion) {
        return this.puertoDAO.consultarPuertoPorIdentificacion(identificacion);
    }


    public List<Puerto> consultarPuertos() {
        List<Puerto> listaPuertos = this.puertoDAO.consultarPuertos();
        return listaPuertos;

    }

    public void actualizarPuerto(Puerto puerto) {
        this.puertoDAO.actualizarPuerto(puerto);
    }

    public void eliminarPuerto(String identificacion) {
        this.puertoDAO.eliminarPuerto(identificacion);

    }

}

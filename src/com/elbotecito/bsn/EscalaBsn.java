package com.elbotecito.bsn;

import com.elbotecito.bsn.exception.BarcoYaExisteException;
import com.elbotecito.bsn.exception.EscalaYaExisteException;
import com.elbotecito.dao.BarcoDAO;
import com.elbotecito.dao.EscalaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.dao.impl.BarcoDAONIO;
import com.elbotecito.dao.impl.EscalaDAONIO;
import com.elbotecito.modelo.Barco;
import com.elbotecito.modelo.Escala;

import java.util.List;

public class EscalaBsn {

    private EscalaDAO escalaDAO;

    public EscalaBsn() {
        this.escalaDAO = new EscalaDAONIO();
    }

    public void guardarEscala(Escala escala) throws EscalaYaExisteException {
        try {
            this.escalaDAO.guardarEscala(escala);
        } catch (LlaveDuplicadaException lde) {
            throw new EscalaYaExisteException();
        }
    }

    public Escala consultarEscala(String idRuta, String idPuerto) {
        return this.escalaDAO.consultarEscala(idRuta, idPuerto);
    }

    public List<Escala> consultarEscalasPorIdRuta(String idRuta) {
        List<Escala> listaEscalas = this.escalaDAO.consultarEscalaPorIdRuta(idRuta);
        return listaEscalas;

    }

    public List<Escala> consultarEscalas() {
        List<Escala> listaEscalas = this.escalaDAO.consultarEscalas();
        return listaEscalas;

    }

    public void actualizarEscala(Escala escala) {
        this.escalaDAO.actualizarEscala(escala);
    }

    public void eliminarEscala(String idRuta, String idPuerto) {
        this.escalaDAO.eliminarEscala(idRuta, idPuerto);

    }

}

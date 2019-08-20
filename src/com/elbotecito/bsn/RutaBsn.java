package com.elbotecito.bsn;

import com.elbotecito.bsn.exception.RutaYaExisteException;
import com.elbotecito.dao.RutaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.dao.impl.RutaDAONIO;
import com.elbotecito.modelo.Ruta;

import java.util.List;

public class RutaBsn {


    private RutaDAO rutaDAO;

    public RutaBsn() {
        this.rutaDAO = new RutaDAONIO();
    }

    public void guardarRuta(Ruta ruta) throws RutaYaExisteException {
        try {
            this.rutaDAO.guardarRuta(ruta);
        } catch (LlaveDuplicadaException lde) {
            throw new RutaYaExisteException();
        }
    }

    public Ruta consultarRuta(String numero) {
        return this.rutaDAO.consultarRutaPorIdentificacion(numero);
    }

    public List<Ruta> consultarRutas() {
        List<Ruta> listaRutas = this.rutaDAO.consultarRutas();
        return listaRutas;
    }

    public boolean actualizarRuta(Ruta ruta) {
       return this.rutaDAO.actualizarRuta(ruta);
    }

    public boolean eliminarRuta(String numero) {
        return this.rutaDAO.eliminarRuta(numero);
    }

}

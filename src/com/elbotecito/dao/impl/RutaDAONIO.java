package com.elbotecito.dao.impl;

import com.elbotecito.dao.RutaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Ruta;

import java.util.List;

public class RutaDAONIO implements RutaDAO {

    @Override
    public boolean guardarRuta(Ruta ruta) throws LlaveDuplicadaException {
        return false;
    }


    @Override
    public Ruta consultarRutaPorIdentificacion(String idRuta) {
        return null;
    }

    @Override
    public List<Ruta> consultarRutas() {
        return null;
    }

    @Override
    public boolean actualizarRuta(Ruta ruta) {
        return false;
    }

    @Override
    public boolean eliminarRuta(String idRuta) {
        return false;
    }
}

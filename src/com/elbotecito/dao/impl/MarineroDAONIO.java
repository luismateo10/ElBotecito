package com.elbotecito.dao.impl;

import com.elbotecito.dao.MarineroDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Marinero;

import java.util.List;

public class MarineroDAONIO implements MarineroDAO {
    @Override
    public boolean guardarMarinero(Marinero marinero) throws LlaveDuplicadaException {
        return false;
    }

    @Override
    public Marinero consultarMarineroPorIdentificacion(String idMarinero) {
        return null;
    }

    @Override
    public List<Marinero> consultarMarineros() {
        return null;
    }

    @Override
    public boolean actualizarMarinero(Marinero marinero) {
        return false;
    }

    @Override
    public boolean eliminarMarinero(String idMarinero) {
        return false;
    }
}

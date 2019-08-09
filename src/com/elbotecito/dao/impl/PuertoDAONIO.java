package com.elbotecito.dao.impl;

import com.elbotecito.dao.PuertoDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Puerto;

import java.util.List;

public class PuertoDAONIO implements PuertoDAO {
    @Override
    public boolean guardarPuerto(Puerto puerto) throws LlaveDuplicadaException {
        return false;
    }

    @Override
    public Puerto consultarPuertoPorIdentificacion(String idPuerto) {
        return null;
    }

    @Override
    public List<Puerto> consultarPuertos() {
        return null;
    }

    @Override
    public boolean actualizarPuerto(Puerto puerto) {
        return false;
    }

    @Override
    public boolean eliminarPuerto(String idPuerto) {
        return false;
    }
}

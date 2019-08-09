package com.elbotecito.dao.impl;

import com.elbotecito.dao.CapitanDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Capitan;

import java.util.List;

public class CapitanDAONIO implements CapitanDAO {
    @Override
    public boolean guardarCapitan(Capitan capitan) throws LlaveDuplicadaException {
        return false;
    }

    @Override
    public Capitan consultarCapitanPorIdentificacion(String idCapitan) {
        return null;
    }

    @Override
    public List<Capitan> consultarCapitanes() {
        return null;
    }

    @Override
    public boolean actualizarCapitan(Capitan capitan) {
        return false;
    }

    @Override
    public boolean eliminarCapitan(String idCapitan) {
        return false;
    }
}

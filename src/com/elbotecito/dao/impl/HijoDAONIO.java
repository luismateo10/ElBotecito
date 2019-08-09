package com.elbotecito.dao.impl;

import com.elbotecito.dao.HijoDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Hijo;

import java.util.List;

public class HijoDAONIO implements HijoDAO {
    @Override
    public boolean guardarHijo(Hijo hijo) throws LlaveDuplicadaException {
        return false;
    }

    @Override
    public Hijo consultarHijoPorIdentificacion(String idHijo) {
        return null;
    }

    @Override
    public List<Hijo> consultarHijos() {
        return null;
    }

    @Override
    public boolean actualizarHijo(Hijo hijo) {
        return false;
    }

    @Override
    public boolean eliminarHijo(String idHijo) {
        return false;
    }
}

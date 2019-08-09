package com.elbotecito.dao.impl;

import com.elbotecito.dao.EscalaDAO;
import com.elbotecito.modelo.Escala;

import java.util.List;

public class EscalaDAONIO implements EscalaDAO {
    @Override
    public boolean guardarEscala(Escala escala) {
        return false;
    }

    @Override
    public List<Escala> consultarEscalaPorIdRuta(String idRuta) {
        return null;
    }

    @Override
    public List<Escala> consultarEscalas() {
        return null;
    }

    @Override
    public boolean actualizarEscala(Escala escala) {
        return false;
    }

    @Override
    public boolean eliminar(String idRuta) {
        return false;
    }
}


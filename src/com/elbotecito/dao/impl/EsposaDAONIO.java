package com.elbotecito.dao.impl;

import com.elbotecito.dao.EsposaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Esposa;

import java.util.List;

public class EsposaDAONIO implements EsposaDAO {
    @Override
    public boolean guardarEsposa(Esposa esposa) throws LlaveDuplicadaException {
        return false;
    }

    @Override
    public Esposa consultarEsposaPorIdentificacion(String idEsposa) {
        return null;
    }

    @Override
    public List<Esposa> consultarEsposas() {
        return null;
    }

    @Override
    public boolean actualizarEsposa(Esposa esposa) {
        return false;
    }

    @Override
    public boolean eliminarEsposa(String idEsposa) {
        return false;
    }
}

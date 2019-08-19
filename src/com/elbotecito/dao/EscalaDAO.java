package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Escala;

import java.util.List;

public interface EscalaDAO {
    //CRUD de Escala - Crear, Leer, Actualizar y Eliminar.

    //Create
    public void guardarEscala(Escala escala) throws LlaveDuplicadaException;

    //Read
    public Escala consultarEscala(String idRuta, String idPuerto);

    public List<Escala> consultarEscalaPorIdRuta(String idRuta);

    public List<Escala> consultarEscalas();

    //Update
    public boolean actualizarEscala(Escala escala);

    //Delete
    public boolean eliminarEscala(String idRuta, String idPuerto);
}

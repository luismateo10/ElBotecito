package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Esposa;

import java.util.List;

public interface EsposaDAO {
    //CRUD de Esposa - Crear, Leer, Actualizar y Eliminar.

    //Create
    public void guardarEsposa(Esposa esposa) throws LlaveDuplicadaException;

    //Read
    public Esposa consultarEsposaPorIdentificacion(String idEsposa);

    public List<Esposa> consultarEsposas();

    //Update
    public boolean actualizarEsposa(Esposa esposa);

    //Delete
    public boolean eliminarEsposa(String idEsposa);
}

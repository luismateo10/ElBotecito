package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Puerto;

import java.util.List;

public interface PuertoDAO {
    //CRUD de Puerto - Crear, Leer, Actualizar y Eliminar.

    //Create
    public boolean guardarPuerto(Puerto puerto) throws LlaveDuplicadaException;

    //Read
    public Puerto consultarPuertoPorIdentificacion(String idPuerto);

    public List<Puerto> consultarPuertos();

    //Update
    public boolean actualizarPuerto(Puerto puerto);

    //Delete
    public boolean eliminarPuerto(String idPuerto);
}

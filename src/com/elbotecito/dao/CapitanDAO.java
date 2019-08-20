package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Capitan;

import java.util.List;

public interface CapitanDAO {
    //CRUD de Capitan - Crear, Leer, Actualizar y Eliminar.

    //Create
    public void guardarCapitan(Capitan capitan) throws LlaveDuplicadaException;

    //Read
    public Capitan consultarCapitanPorIdentificacion(String idCapitan);

    public List<Capitan> consultarCapitanes();

    //Update
    public boolean actualizarCapitan(Capitan capitan);

    //Delete
    public boolean eliminarCapitan(String idCapitan);
}

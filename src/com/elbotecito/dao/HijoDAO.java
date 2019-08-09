package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Hijo;


import java.util.List;

public interface HijoDAO {
    //CRUD de Hijo - Crear, Leer, Actualizar y Eliminar.

    //Create
    public boolean guardarHijo(Hijo hijo) throws LlaveDuplicadaException;

    //Read
    public Hijo consultarHijoPorIdentificacion(String idHijo);

    public List<Hijo> consultarHijos();

    //Update
    public boolean actualizarHijo(Hijo hijo);

    //Delete
    public boolean eliminarHijo(String idHijo);
}

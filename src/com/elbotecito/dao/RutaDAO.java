package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Ruta;

import java.util.List;

public interface RutaDAO {
    //CRUD de Ruta - Crear, Leer, Actualizar y Eliminar.

    //Create
    public boolean guardarRuta(Ruta ruta) throws LlaveDuplicadaException;

    //Read
    public Ruta consultarRutaPorIdentificacion(String idRuta);

    public List<Ruta> consultarRutas();

    //Update
    public boolean actualizarRuta(Ruta ruta);

    //Delete
    public boolean eliminarRuta(String idRuta);
}

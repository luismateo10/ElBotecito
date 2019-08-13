package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;

import java.util.List;

import com.elbotecito.modelo.Barco;

public interface BarcoDAO {
    //CRUD de Barco - Crear, Leer, Actualizar y Eliminar.

    //Create

    /**
     *
     * @param barco
     * @return
     * @throws LlaveDuplicadaException
     */
    void guardarBarco(Barco barco) throws LlaveDuplicadaException;

    //Read

    /**
     *
     * @param matriculaBarco
     * @return
     */
    Barco consultarBarcoPorMatricula(String matriculaBarco);

    List<Barco> consultarBarcos();

    //Update
    boolean actualizarBarco(Barco barco);

    //Delete
    boolean eliminarBarco(String matriculaBarco);


}

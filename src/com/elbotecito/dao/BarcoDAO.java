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
    public boolean guardarBarco(Barco barco) throws LlaveDuplicadaException;

    //Read

    /**
     *
     * @param matriculaBarco
     * @return
     */
    public Barco consultarBarcoPorMatricula(String matriculaBarco);

    public List<Barco> consultarBarcos();

    //Update
    public boolean actualizarBarco(Barco barco);

    //Delete
    public boolean eliminarBarco(String matriculaBarco);


}

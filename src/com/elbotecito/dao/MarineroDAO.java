package com.elbotecito.dao;

import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Marinero;


import java.util.List;

public interface MarineroDAO {
    //CRUD de Marinero - Crear, Leer, Actualizar y Eliminar.

    //Create
    public void guardarMarinero(Marinero marinero) throws LlaveDuplicadaException;

    //Read
    public Marinero consultarMarineroPorIdentificacion(String idMarinero);

    public List<Marinero> consultarMarineros();

    //Update
    public boolean actualizarMarinero(Marinero marinero);

    //Delete
    public boolean eliminarMarinero(String idMarinero);
}

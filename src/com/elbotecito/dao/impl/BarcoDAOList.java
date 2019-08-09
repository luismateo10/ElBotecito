package com.elbotecito.dao.impl;

import com.elbotecito.dao.BarcoDAO;
import com.elbotecito.modelo.Barco;

import java.util.ArrayList;
import java.util.List;

public class BarcoDAOList implements BarcoDAO {
    public static List<Barco> barcosBD = new ArrayList<>();

    @Override
    public boolean actualizarBarco(Barco barco) {
        return false;
    }

    @Override
    public boolean eliminarBarco(String matriculaBarco) {
        return false;
    }

    @Override
    public boolean guardarBarco(Barco barco) {
        //todo Verificar si el empleado existia o no
        System.out.println("en el dao");
        barcosBD.add(barco);
        System.out.println("Empleado guardado en dao: "+barco);
        return true;
    }

    @Override
    public Barco consultarBarcoPorMatricula(String matricula) {
        //todo retornar un Optional
        for(Barco e: barcosBD){
            if(matricula.equals(e.getMatricula())){
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Barco> consultarBarcos() {
        //todo enviar una copia de la lista

        return barcosBD;
    }
}

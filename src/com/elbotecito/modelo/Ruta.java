package com.elbotecito.modelo;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
    String numero;
    String matriculaBarco;
    String idPuertoOrigen;
    String fechaPuertoOrigen;
    String idPuertoDestino;
    String fechaPuertoDestino;
    String idPuertoAtual;
    String idCapitan;

    //Relaciones
    List<Escala> escalas = new ArrayList<>();
    List<Marinero> marineros = new ArrayList<>();
}

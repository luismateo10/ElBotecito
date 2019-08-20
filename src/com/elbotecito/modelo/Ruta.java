package com.elbotecito.modelo;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
    private String numero;

    private String matriculaBarco;

    private String idPuertoOrigen;

    private String fechaPuertoOrigen;

    private String idPuertoDestino;

    private String fechaPuertoDestino;

    private String idPuertoAtual;

    private String idCapitan;

    //Relaciones
    private List<Escala> escalas = new ArrayList<>();
    private List<Marinero> marineros = new ArrayList<>();

    public Ruta() {
    }

    public Ruta(String numero, String matriculaBarco, String idPuertoOrigen, String fechaPuertoOrigen, String idPuertoDestino, String fechaPuertoDestino, String idPuertoAtual, String idCapitan, List<Escala> escalas, List<Marinero> marineros) {
        this.setNumero(numero);
        this.setMatriculaBarco(matriculaBarco);
        this.setIdPuertoOrigen(idPuertoOrigen);
        this.setFechaPuertoOrigen(fechaPuertoOrigen);
        this.setIdPuertoDestino(idPuertoDestino);
        this.setFechaPuertoDestino(fechaPuertoDestino);
        this.setIdPuertoAtual(idPuertoAtual);
        this.setIdCapitan(idCapitan);
        this.setEscalas(escalas);
        this.setMarineros(marineros);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMatriculaBarco() {
        return matriculaBarco;
    }

    public void setMatriculaBarco(String matriculaBarco) {
        this.matriculaBarco = matriculaBarco;
    }

    public String getIdPuertoOrigen() {
        return idPuertoOrigen;
    }

    public void setIdPuertoOrigen(String idPuertoOrigen) {
        this.idPuertoOrigen = idPuertoOrigen;
    }

    public String getFechaPuertoOrigen() {
        return fechaPuertoOrigen;
    }

    public void setFechaPuertoOrigen(String fechaPuertoOrigen) {
        this.fechaPuertoOrigen = fechaPuertoOrigen;
    }

    public String getIdPuertoDestino() {
        return idPuertoDestino;
    }

    public void setIdPuertoDestino(String idPuertoDestino) {
        this.idPuertoDestino = idPuertoDestino;
    }

    public String getFechaPuertoDestino() {
        return fechaPuertoDestino;
    }

    public void setFechaPuertoDestino(String fechaPuertoDestino) {
        this.fechaPuertoDestino = fechaPuertoDestino;
    }

    public String getIdPuertoAtual() {
        return idPuertoAtual;
    }

    public void setIdPuertoAtual(String idPuertoAtual) {
        this.idPuertoAtual = idPuertoAtual;
    }

    public String getIdCapitan() {
        return idCapitan;
    }

    public void setIdCapitan(String idCapitan) {
        this.idCapitan = idCapitan;
    }

    public List<Escala> getEscalas() {
        return escalas;
    }

    public void setEscalas(List<Escala> escalas) {
        this.escalas = escalas;
    }

    public List<Marinero> getMarineros() {
        return marineros;
    }

    public void setMarineros(List<Marinero> marineros) {
        this.marineros = marineros;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "numero='" + getNumero() + '\'' +
                ", matriculaBarco='" + getMatriculaBarco() + '\'' +
                ", idPuertoOrigen='" + getIdPuertoOrigen() + '\'' +
                ", fechaPuertoOrigen='" + getFechaPuertoOrigen() + '\'' +
                ", idPuertoDestino='" + getIdPuertoDestino() + '\'' +
                ", fechaPuertoDestino='" + getFechaPuertoDestino() + '\'' +
                ", idPuertoAtual='" + getIdPuertoAtual() + '\'' +
                ", idCapitan='" + getIdCapitan() + '\'' +
                ", escalas=" + getEscalas() +
                ", marineros=" + getMarineros() +
                '}';
    }
}

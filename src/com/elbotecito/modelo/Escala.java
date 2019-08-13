package com.elbotecito.modelo;

public class Escala {
    String idRuta;
    String idPuerto;
    String fechaEscala;

    public Escala() {
    }

    public Escala(String idRuta, String idPuerto, String fechaEscala) {
        this.idRuta = idRuta;
        this.idPuerto = idPuerto;
        this.fechaEscala = fechaEscala;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getIdPuerto() {
        return idPuerto;
    }

    public void setIdPuerto(String idPuerto) {
        this.idPuerto = idPuerto;
    }

    public String getFechaEscala() {
        return fechaEscala;
    }

    public void setFechaEscala(String fechaEscala) {
        this.fechaEscala = fechaEscala;
    }

    @Override
    public String toString() {
        return "Escala{" +
                "idRuta='" + idRuta + '\'' +
                ", idPuerto='" + idPuerto + '\'' +
                ", fechaEscala='" + fechaEscala + '\'' +
                '}';
    }
}

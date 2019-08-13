package com.elbotecito.modelo;

public class Marinero extends Persona {
    private String idRuta;

    public Marinero() {
    }

    public Marinero(String identificacion, String nombre, String sexo, String estadoVivo, String idRuta) {
        super(identificacion, nombre, sexo, estadoVivo);
        this.idRuta = idRuta;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    @Override
    public String toString() {
        return "Marinero{" +
                "idRuta='" + idRuta + '\'' +
                '}';
    }
}

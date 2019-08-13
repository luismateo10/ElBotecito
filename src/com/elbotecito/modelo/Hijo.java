package com.elbotecito.modelo;

public class Hijo extends Persona{
    private String idPadre;
    private String porcHerencia;

    public Hijo() {
    }

    public Hijo(String identificacion, String nombre, String sexo, String estadoVivo, String idPadre, String porcHerencia) {
        super(identificacion, nombre, sexo, estadoVivo);
        this.idPadre = idPadre;
        this.porcHerencia = porcHerencia;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public String getPorcHerencia() {
        return porcHerencia;
    }

    public void setPorcHerencia(String porcHerencia) {
        this.porcHerencia = porcHerencia;
    }

    @Override
    public String toString() {
        return "Hijo{" +
                "idPadre='" + idPadre + '\'' +
                ", porcHerencia='" + porcHerencia + '\'' +
                '}';
    }
}

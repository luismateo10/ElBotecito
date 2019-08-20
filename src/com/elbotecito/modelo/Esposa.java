package com.elbotecito.modelo;

public class Esposa extends Persona{
    private String esposo;
    private String porcHerencia;

    public Esposa() {
    }

    public Esposa(String identificacion, String nombre, String sexo, String estadoVivo, String esposo, String porcHerencia) {
        super(identificacion, nombre, sexo, estadoVivo);
        this.esposo = esposo;
        this.porcHerencia = porcHerencia;
    }

    public String getEsposo() {
        return esposo;
    }

    public void setEsposo(String esposo) {
        this.esposo = esposo;
    }

    public String getPorcHerencia() {
        return porcHerencia;
    }

    public void setPorcHerencia(String porcHerencia) {
        this.porcHerencia = porcHerencia;
    }

    @Override
    public String toString() {
        return "Esposa{" +
                "esposo='" + esposo + '\'' +
                ", porcHerencia='" + porcHerencia + '\'' +
                '}';
    }
}

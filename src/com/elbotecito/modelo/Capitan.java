package com.elbotecito.modelo;

public class Capitan extends Persona {
    private String idRuta;
    private String numHijos;
    private String numEsposas;
    private String fortuna;

    public Capitan() {
    }

    public Capitan(String identificacion, String nombre, String sexo, String estadoVivo, String idRuta, String numHijos, String numEsposas, String fortuna) {
        super(identificacion, nombre, sexo, estadoVivo);
        this.idRuta = idRuta;
        this.numHijos = numHijos;
        this.numEsposas = numEsposas;
        this.fortuna = fortuna;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getNumHijos() {
        return numHijos;
    }

    public void setNumHijos(String numHijos) {
        this.numHijos = numHijos;
    }

    public String getNumEsposas() {
        return numEsposas;
    }

    public void setNumEsposas(String numEsposas) {
        this.numEsposas = numEsposas;
    }

    public String getFortuna() {
        return fortuna;
    }

    public void setFortuna(String fortuna) {
        this.fortuna = fortuna;
    }

    @Override
    public String toString() {
        return "Capitan{" +
                "idRuta='" + idRuta + '\'' +
                ", numHijos='" + numHijos + '\'' +
                ", numEsposas='" + numEsposas + '\'' +
                ", fortuna='" + fortuna + '\'' +
                '}';
    }
}

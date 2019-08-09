package com.elbotecito.modelo;

public class Persona {


    private String identificacion;
    private String nombre;
    private String sexo;
    private String estadoVivo;

    public Persona() {
    }

    public Persona(String identificacion, String nombre, String sexo, String estadoVivo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.sexo = sexo;
        this.estadoVivo = estadoVivo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoVivo() {
        return estadoVivo;
    }

    public void setEstadoVivo(String estadoVivo) {
        this.estadoVivo = estadoVivo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", sexo='" + sexo + '\'' +
                ", estadoVivo='" + estadoVivo + '\'' +
                '}';
    }
}

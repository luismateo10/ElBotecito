package com.elbotecito.modelo;

public class Barco {
    //POJO de Barco
    private String matricula;  //PK
    private String capacidadMax;
    private String numeroRegMerc;
    private String fechaRegMerc;
    private String nombre;
    private String estado;
    private String tipoId;

    public Barco() {
    }

    public Barco(String matricula, String capacidadMax, String numeroRegMerc, String fechaRegMerc, String nombre, String estado, String tipoId) {
        this.matricula = matricula;
        this.capacidadMax = capacidadMax;
        this.numeroRegMerc = numeroRegMerc;
        this.fechaRegMerc = fechaRegMerc;
        this.nombre = nombre;
        this.estado = estado;
        this.tipoId = tipoId;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(String capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public String getNumeroRegMerc() {
        return numeroRegMerc;
    }

    public void setNumeroRegMerc(String numeroRegMerc) {
        this.numeroRegMerc = numeroRegMerc;
    }

    public String getFechaRegMerc() {
        return fechaRegMerc;
    }

    public void setFechaRegMerc(String fechaRegMerc) {
        this.fechaRegMerc = fechaRegMerc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    @Override
    public String toString() {
        return "Barco{" +
                "matricula='" + matricula + '\'' +
                ", capacidadMax='" + capacidadMax + '\'' +
                ", numeroRegMerc='" + numeroRegMerc + '\'' +
                ", fechaRegMerc='" + fechaRegMerc + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", tipoId='" + tipoId + '\'' +
                '}';
    }
}

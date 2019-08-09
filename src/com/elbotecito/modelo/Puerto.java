package com.elbotecito.modelo;

public class Puerto {
    private String identificacion;
    private String nombre;
    private String ciudad;

    public Puerto() {
    }

    public Puerto(String identificacion, String nombre, String ciudad) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.ciudad = ciudad;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}

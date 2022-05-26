package com.ipn.mx.modelo.entidades;

import java.io.Serializable;

public class Carrera implements Serializable {
    //Implementando los datos que tenemos en nuestra base de datos
//    Basandonos en el diagrama de Clases
    private int idCarrera;
    private String nombreCarrera;
    private String descripcionCarrera;
    //Table Carrera
    public Carrera() {
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getDescripcionCarrera() {
        return descripcionCarrera;
    }

    public void setDescripcionCarrera(String descripcionCarrera) {
        this.descripcionCarrera = descripcionCarrera;
    }
}

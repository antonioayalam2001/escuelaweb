package com.ipn.mx.modelo.dto;


import com.ipn.mx.modelo.entidades.Carrera;

import java.io.Serializable;

public class CarreraDTO implements Serializable {
    private Carrera entidad;

    public CarreraDTO() {
        entidad=new Carrera();
    }

    public Carrera getEntidad() {
        return entidad;
    }

    public void setEntidad(Carrera entidad) {
        this.entidad = entidad;
    }


    //    This method executes automatically we don't need to invoke it it's like the constructor
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Clave de la carrera :").append(entidad.getIdCarrera()).append("\n");
        sb.append("Nombre de la Carrera :").append(entidad.getNombreCarrera()).append("\n");
        sb.append("Descripcion de la Carrera :").append(entidad.getDescripcionCarrera()).append("\n");
        return sb.toString();
    }
}
//    public static void main(String[] args) {
////        Creando una instancia de carrera Dto
////        Con .getEntidad recuperamos la entidad
////        Con el opunto realizamos la recuperación de los metodos
//        CarreraDTO dto = new CarreraDTO();
//        dto.getEntidad().setIdCarrera((1L));
//        dto.getEntidad().setNombreCarrera("Ingenieria en Sistemas Computacionales");
//        dto.getEntidad().setDescripcionCarrera("La descripcion de la carrera");
////        System.out.println("Se ejecuta primero"+dto.getEntidad().getIdCarrera());
//        System.out.println(dto.toString());
//
//    }

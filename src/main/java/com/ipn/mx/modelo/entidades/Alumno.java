package com.ipn.mx.modelo.entidades;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//Table Alumno
@Data
@NoArgsConstructor
public class Alumno implements Serializable {
    private int idAlumno;
    private String nombreAlumno;
    private String paternoAlumno;
    private String maternoAlumno;
    private String emailAlumno;
    private int idCarrera;
}

package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Alumno;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlumnoDTO {
    private Alumno entidad;

    public AlumnoDTO( ) {
        entidad = new Alumno();
    }

}


//insert into carrera (nombreCarrera,descripcionCarrera) values ('Instrumentacion','Muchos Circuitos');
//insert into carrera (nombreCarrera,descripcionCarrera) values ('Teoria de Comunicaciones y Señales','Muchas señales');
//insert into carrera (nombreCarrera,descripcionCarrera) values ('Web Application Development','Mucho mas nada');
//insert into carrera (nombreCarrera,descripcionCarrera) values ('Base de Datos','Muchas Tuplas');
//insert into carrera (nombreCarrera,descripcionCarrera) values ('Analisis de Algoritmos','Muchas Complejidades n al cubo');

//insert into alumno (nombreAlumno,paternoAlumno,maternoAlumno,emailAlumno,idCarrera) values ("Tony","Ayala","Mora","antonioayala@gmail.com",1);
//insert into alumno (nombreAlumno,paternoAlumno,maternoAlumno,emailAlumno,idCarrera) values ("Lupita","Ayala","Mora","guadalupeayala@gmail.com",2);
//insert into alumno (nombreAlumno,paternoAlumno,maternoAlumno,emailAlumno,idCarrera) values ("Ramon","Ayala","Mora","ramon@gmail.com",3);
//insert into alumno (nombreAlumno,paternoAlumno,maternoAlumno,emailAlumno,idCarrera) values ("Sandy","Ayala","Mora","sandy@gmail.com",3);
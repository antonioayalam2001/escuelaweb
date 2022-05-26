package com.ipn.mx.modelo.entidades;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor

public class Usuario implements Serializable {
    private String nomU;
    private String apeP;
    private String apeM;
    private String usuario;
    private String clave;
    private String fechaC;
    private int id;
}

package com.ipn.mx.modelo.entidades;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Articulo implements Serializable {
    private int idArticulo;
    private String nomArt;
    private String descArti;
    private int existencia;
    private int stockMinimo;
    private int stockMaximo;
    private float precio;

    private int idCategoria;
//    private Categoria idCategoria;
}

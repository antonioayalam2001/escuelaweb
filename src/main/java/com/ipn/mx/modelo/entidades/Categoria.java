package com.ipn.mx.modelo.entidades;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Categoria {
    private int idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;
}

package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private Usuario entidad;
    public UsuarioDTO(){entidad = new Usuario();}
}

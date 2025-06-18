package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UsuarioTallerResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;


    public UsuarioTallerResponseDTO(Long id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

    }


}
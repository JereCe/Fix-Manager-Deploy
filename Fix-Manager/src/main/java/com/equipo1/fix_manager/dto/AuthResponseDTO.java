package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AuthResponseDTO {
    private String token;
    private String rol;
    private Long id;
    private String email;
    private String nombre;


    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, String rol, Long id, String email, String nombre) {
        this.token = token;
        this.rol = rol;
        this.id = id;
        this.email = email;
        this.nombre = nombre;
    }
}
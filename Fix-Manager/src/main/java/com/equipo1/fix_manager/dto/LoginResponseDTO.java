package com.equipo1.fix_manager.dto;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LoginResponseDTO {
    private boolean loginExitoso;
    private String mensaje;

    public LoginResponseDTO(boolean loginExitoso, String mensaje) {
        this.loginExitoso = loginExitoso;
        this.mensaje = mensaje;
    }


}
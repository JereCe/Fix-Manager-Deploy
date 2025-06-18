package com.equipo1.fix_manager.dto;

public class MensajeRespuestaDTO {
    private String mensaje;

    public MensajeRespuestaDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getter
    public String getMensaje() {
        return mensaje;
    }
}
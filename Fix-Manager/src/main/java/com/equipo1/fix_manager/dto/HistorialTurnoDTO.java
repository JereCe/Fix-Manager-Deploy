package com.equipo1.fix_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialTurnoDTO {
    private Long id;
    private String fecha;
    private String hora;
    private String tallerNombre;
    private String tallerUbicacion;
    private String descripcionTrabajo;
}

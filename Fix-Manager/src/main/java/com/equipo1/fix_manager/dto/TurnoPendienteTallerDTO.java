package com.equipo1.fix_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurnoPendienteTallerDTO {
    private Long id;
    private String fecha;
    private String hora;
    private String estado;
    private String disponibilidad;

    private String vehiculoMarca;
    private String vehiculoModelo;
    private String vehiculoPatente;

    private String tallerNombre;
    private String tallerUbicacion;
}

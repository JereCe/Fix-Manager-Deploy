package com.equipo1.fix_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class TurnoDetalleDTO {
    private Long id;
    private String fecha;
    private String hora;
    private String tallerNombre;
    private String descripcionTrabajo;
    private List<String> imagenes;
    private String vehiculoMarca;
    private String vehiculoModelo;
    private String vehiculoPatente;
}

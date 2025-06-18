package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class VehiculoDTO {
    private String marca;
    private String modelo;
    private String patente;
    private Long usuarioId;
    private Integer anio;

}
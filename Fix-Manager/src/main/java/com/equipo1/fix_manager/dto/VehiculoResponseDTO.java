package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class VehiculoResponseDTO {
    private Long id;
    private String marca;
    private String modelo;
    private String patente;
    private Integer anio;

    public VehiculoResponseDTO(Long id, String marca, String modelo, String patente, Integer anio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.anio = anio;
    }
}

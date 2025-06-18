package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter@Setter
public class TurnoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;

    public TurnoResponseDTO(Long id, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
    }


}
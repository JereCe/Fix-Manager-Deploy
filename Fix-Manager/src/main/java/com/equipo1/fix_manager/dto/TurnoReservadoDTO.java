package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter@Setter
public class TurnoReservadoDTO {
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String taller;
    private String estado;
    private String patente;

    public TurnoReservadoDTO(Long id, LocalDate fecha, LocalTime hora, String taller, String estado, String patente) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.taller = taller;
        this.estado = estado;
        this.patente = patente;
    }


}

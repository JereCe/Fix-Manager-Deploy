package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter@Setter
public class CrearTurnoDTO {
    private LocalDate fecha;
    private LocalTime hora;


}

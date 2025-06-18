package com.equipo1.fix_manager.dto;

import com.equipo1.fix_manager.model.Turno;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter@Setter
public class TurnoDTO {

    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private String disponibilidad;
    private String patenteVehiculo;
    private String nombreCliente;
    private String nombreTaller;

    public TurnoDTO(Turno turno) {
        this.id = turno.getId();
        this.fecha = turno.getFecha();
        this.hora = turno.getHora();
        this.estado = turno.getEstado().name();
        this.disponibilidad = turno.getDisponibilidad().name();
        this.patenteVehiculo = (turno.getVehiculo() != null) ? turno.getVehiculo().getPatente() : "NO_ASIGNADO";
        this.nombreCliente = (turno.getCliente() != null) ? turno.getCliente().getNombre() : "NO_ASIGNADO";
        this.nombreTaller = (turno.getAgenda() != null && turno.getAgenda().getTaller() != null)
                ? turno.getAgenda().getTaller().getNombre()
                : "NO_ASIGNADO";
    }

}

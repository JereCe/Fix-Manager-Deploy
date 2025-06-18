package com.equipo1.fix_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne
    private Vehiculo vehiculo;

    @ManyToOne
    private UsuarioCliente cliente;

    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @Enumerated(EnumType.STRING)
    private DisponibilidadTurno disponibilidad;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    private Historial historial;

    @Lob
    private String descripcionTrabajo;

    @ElementCollection
    private List<String> imagenes;

    @OneToMany(mappedBy = "turno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TurnoEstadoHistorial> historialEstados;

    @Column
    private Integer calificacion;


    public Turno() {}


    public Turno(Long id, LocalDate fecha, LocalTime hora, Vehiculo vehiculo, UsuarioCliente cliente, Agenda agenda, DisponibilidadTurno disponibilidad, Estado estado, Historial historial, String descripcionTrabajo, List<String> imagenes, List<TurnoEstadoHistorial> historialEstados, Integer calificacion) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.agenda = agenda;
        this.disponibilidad = disponibilidad;
        this.estado = estado;
        this.historial = historial;
        this.descripcionTrabajo = descripcionTrabajo;
        this.imagenes = imagenes;
        this.historialEstados = historialEstados;
        this.calificacion = calificacion;
    }
}

package com.equipo1.fix_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@Entity
public class Taller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;

    private String descripcion;
    private String ubicacion;
    private String imagenLogo;

    @Column(name = "promedio_calificacion")
    private Double promedioCalificacion;

    @Column(name = "cantidad_calificaciones")
    private Long cantidadCalificaciones;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "taller_tipos_reparacion",
            joinColumns = @JoinColumn(name = "taller_id")
    )
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private Set<TipoReparacion> tipoReparaciones;

    @Column
    private String ciudad;



    public Taller() {
    }

    public Taller(Long id, String nombre, String descripcion, String ubicacion, String imagenLogo, Double promedioCalificacion, Long cantidadCalificaciones, Agenda agenda, Set<TipoReparacion> tipoReparaciones, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagenLogo = imagenLogo;
        this.promedioCalificacion = promedioCalificacion;
        this.cantidadCalificaciones = cantidadCalificaciones;
        this.agenda = agenda;
        this.tipoReparaciones = tipoReparaciones;
        this.ciudad = ciudad;
    }
}

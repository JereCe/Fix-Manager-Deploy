package com.equipo1.fix_manager.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter@Setter
@Entity
public class TurnoEstadoHistorial {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_id")
    private Turno turno;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private LocalDateTime fechaHora;

    private String notaOpcional;

    public TurnoEstadoHistorial() {
    }

    public TurnoEstadoHistorial(Long id, Turno turno, Estado estado, LocalDateTime fechaHora, String notaOpcional) {
        this.id = id;
        this.turno = turno;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.notaOpcional = notaOpcional;
    }
}

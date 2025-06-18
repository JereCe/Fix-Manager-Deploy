package com.equipo1.fix_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String modelo;

    private String marca;

    private String patente;

    private Integer anio;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id")
    private UsuarioCliente usuarioCliente;



    public Vehiculo() {
    }

    public Vehiculo(Long id, String modelo, String marca, String patente, Integer anio, UsuarioCliente usuarioCliente) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.patente = patente;
        this.anio = anio;
        this.usuarioCliente = usuarioCliente;

    }
}

package com.equipo1.fix_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class UsuarioCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String contrasenia;

    private String nombre;

    private String apellido;

    private String documento;


    @OneToMany(mappedBy = "usuarioCliente", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Turno> turnos;

    @ManyToMany
    @JoinTable(
            name = "favoritos_clientes_talleres",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "taller_id")
    )
    private List<Taller> talleresFavoritos;

    public UsuarioCliente() {
    }

    public UsuarioCliente(Long id, String email, String contrasenia, String nombre, String apellido, String documento, List<Vehiculo> vehiculos, List<Turno> turnos, List<Taller> talleresFavoritos) {
        this.id = id;
        this.email = email;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.vehiculos = vehiculos;
        this.turnos = turnos;
        this.talleresFavoritos = talleresFavoritos;
    }
}

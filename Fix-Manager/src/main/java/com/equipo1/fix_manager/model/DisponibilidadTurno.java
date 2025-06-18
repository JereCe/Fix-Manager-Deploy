package com.equipo1.fix_manager.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DisponibilidadTurno {

    LIBRE("Libre"),
    RESERVADO("Reservado");

    private final String descripcion;

    DisponibilidadTurno(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonValue
    public String getDescripcion() {
        return descripcion;
    }

    public static DisponibilidadTurno fromDescripcion(String descripcion) {
        for (DisponibilidadTurno d : values()) {
            if (d.descripcion.equalsIgnoreCase(descripcion)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Descripción inválida: " + descripcion);
    }
}

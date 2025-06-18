package com.equipo1.fix_manager.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Estado {

    PENDIENTE("Pendiente"),
    REALIZADO("Realizado"),
    CANCELADO("Cancelado");

    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonValue
    public String getDescripcion() {
        return descripcion;
    }

    public static Estado fromDescripcion(String descripcion) {
        for (Estado estado : values()) {
            if (estado.descripcion.equalsIgnoreCase(descripcion)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Descripción inválida: " + descripcion);
    }
}

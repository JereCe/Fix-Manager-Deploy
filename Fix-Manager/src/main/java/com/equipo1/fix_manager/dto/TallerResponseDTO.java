package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TallerResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String imagenLogo;
    private Double promedioCalificacion;
    private Long cantidadCalificaciones;


    public TallerResponseDTO(Long id, String nombre, String descripcion, String ubicacion, String imagenLogo, Double promedioCalificacion, Long cantidadCalificaciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagenLogo = imagenLogo;
        this.promedioCalificacion = promedioCalificacion;
        this.cantidadCalificaciones = cantidadCalificaciones;
    }
}

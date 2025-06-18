package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CrearTallerDTO {
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String imagenLogo;

    private  String ciudad;
}

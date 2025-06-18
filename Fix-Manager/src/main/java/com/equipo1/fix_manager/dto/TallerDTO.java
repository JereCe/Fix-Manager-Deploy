package com.equipo1.fix_manager.dto;

import com.equipo1.fix_manager.model.TipoReparacion;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@Getter@Setter
@NoArgsConstructor
public class TallerDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String imagenLogo;
    private Double promedioCalificacion;

    private String ciudad;
    private Set<TipoReparacion> tipoReparaciones;



}
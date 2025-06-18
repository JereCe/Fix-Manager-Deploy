package com.equipo1.fix_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalificacionDTO {
    private boolean calificado;
    private int puntuacion; // Si no está calificado, puede ser 0
}

package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.model.Estado;

public interface ITurnoEstadoHistorialService {
    void registrarCambioEstado(Long turnoId, Estado nuevoEstado, String notaOpcional);
}
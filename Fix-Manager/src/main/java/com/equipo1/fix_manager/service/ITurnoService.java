package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.*;

import java.util.List;

public interface ITurnoService {

    void crearTurnoDesdeTaller(Long usuarioTallerId, CrearTurnoDTO datos);

    List<TurnoResponseDTO> listarTurnosDisponiblesPorTaller(Long tallerId);


    void reservarTurno(Long turnoId, Long clienteId, Long vehiculoId);

    List<TurnoReservadoDetalleDTO> obtenerTurnosPorCliente(Long clienteId);


    List<TurnoPendienteTallerDTO> obtenerTurnosPendientesDelTaller(Long tallerId);

    void finalizarTurno(Long turnoId, FinalizarTurnoDTO datos);

    List<HistorialTurnoDTO> obtenerHistorialPorVehiculo(Long vehiculoId);

    List<TurnoDTO> obtenerTodosLosTurnosPorTaller(Long tallerId);

    void calificarTurno(Long turnoId, CalificarTurnoDTO dto);


    void cancelarTurnoPorCliente(Long turnoId);
    void cancelarTurnoPorTaller(Long turnoId);


    TurnoDetalleDTO obtenerDetalleTurno(Long id);


    CalificacionDTO obtenerCalificacionDeTurno(Long turnoId);


}

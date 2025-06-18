package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.model.Estado;
import com.equipo1.fix_manager.model.Turno;
import com.equipo1.fix_manager.model.TurnoEstadoHistorial;
import com.equipo1.fix_manager.repository.ITurnoEstadoHistorialRepository;
import com.equipo1.fix_manager.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class TurnoEstadoHistorialService implements ITurnoEstadoHistorialService {

    @Autowired
    private ITurnoEstadoHistorialRepository historialRepository;

    @Autowired
    private ITurnoRepository turnoRepository;

    @Override
    public void registrarCambioEstado(Long turnoId, Estado nuevoEstado, String notaOpcional) {
        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turno no encontrado"));

        TurnoEstadoHistorial historial = new TurnoEstadoHistorial();
        historial.setTurno(turno);
        historial.setEstado(nuevoEstado);
        historial.setFechaHora(LocalDateTime.now());
        historial.setNotaOpcional(notaOpcional);

        historialRepository.save(historial);
    }
}

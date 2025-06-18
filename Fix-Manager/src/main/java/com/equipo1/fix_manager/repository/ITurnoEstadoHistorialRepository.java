package com.equipo1.fix_manager.repository;

import com.equipo1.fix_manager.model.TurnoEstadoHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITurnoEstadoHistorialRepository extends JpaRepository<TurnoEstadoHistorial, Long> {
    List<TurnoEstadoHistorial> findByTurnoId(Long turnoId);
}

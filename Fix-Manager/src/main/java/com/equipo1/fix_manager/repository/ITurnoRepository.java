package com.equipo1.fix_manager.repository;


import com.equipo1.fix_manager.model.DisponibilidadTurno;
import com.equipo1.fix_manager.model.Estado;
import com.equipo1.fix_manager.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno,Long> {


    List<Turno> findByAgenda_IdAndDisponibilidad(Long agendaId, DisponibilidadTurno disponibilidad);

    List<Turno> findByCliente_Id(Long clienteId);

    List<Turno> findByAgenda_IdAndEstadoOrderByFechaAscHoraAsc(Long agendaId, Estado estado);


    List<Turno> findByVehiculo_IdAndEstado(Long vehiculoId, Estado estado);

    List<Turno> findByAgenda_Taller_IdOrderByFechaAscHoraAsc(Long tallerId);

    List<Turno> findByAgenda_Taller_IdAndEstadoOrderByFechaAscHoraAsc(Long tallerId, Estado estado);


    List<Turno> findByClienteId(Long clienteId);

}

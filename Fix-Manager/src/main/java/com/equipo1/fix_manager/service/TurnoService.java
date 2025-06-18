package com.equipo1.fix_manager.service;


import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.model.*;
import com.equipo1.fix_manager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService  implements ITurnoService{

    @Autowired
    private ITurnoRepository turnoRepo;

    @Autowired
    private IUsuarioTallerRepository usuarioTallerRepo;


    @Autowired
    private ITallerRepository tallerRepo;

    @Autowired
    private IVehiculoRepository vehiculoRepo;


    @Autowired
    private IUsuarioClienteRepository usuarioClienteRepo;

    @Autowired
    private ITurnoEstadoHistorialService turnoEstadoHistorialService;



    @Override
    public void crearTurnoDesdeTaller(Long usuarioTallerId, CrearTurnoDTO datos) {
        UsuarioTaller usuario = usuarioTallerRepo.findById(usuarioTallerId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario taller no encontrado."));

        Taller taller = usuario.getTaller();
        if (taller == null || taller.getAgenda() == null) {
            throw new IllegalStateException("El taller no tiene una agenda asociada.");
        }

        Turno turno = new Turno();
        turno.setFecha(datos.getFecha());
        turno.setHora(datos.getHora());
        turno.setDisponibilidad(DisponibilidadTurno.LIBRE);
        turno.setEstado(Estado.PENDIENTE);
        turno.setAgenda(taller.getAgenda());

        turnoRepo.save(turno);
    }


    @Override
    public List<TurnoResponseDTO> listarTurnosDisponiblesPorTaller(Long tallerId) {
        Taller taller = tallerRepo.findById(tallerId)
                .orElseThrow(() -> new IllegalArgumentException("Taller no encontrado"));

        Agenda agenda = taller.getAgenda();
        if (agenda == null) {
            throw new IllegalStateException("El taller no tiene agenda asociada.");
        }

        List<Turno> turnos = turnoRepo.findByAgenda_IdAndDisponibilidad(agenda.getId(), DisponibilidadTurno.LIBRE);

        return turnos.stream()
                .map(t -> new TurnoResponseDTO(t.getId(), t.getFecha(), t.getHora()))
                .toList();
    }

    @Override
    public void reservarTurno(Long turnoId, Long clienteId, Long vehiculoId) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getDisponibilidad() != DisponibilidadTurno.LIBRE) {
            throw new IllegalStateException("El turno ya está reservado.");
        }

        UsuarioCliente cliente = usuarioClienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Vehiculo vehiculo = vehiculoRepo.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        turno.setCliente(cliente);
        turno.setVehiculo(vehiculo);
        turno.setDisponibilidad(DisponibilidadTurno.RESERVADO);
        turno.setEstado(Estado.PENDIENTE);

        turnoRepo.save(turno);

        turnoEstadoHistorialService.registrarCambioEstado(turno.getId(), Estado.PENDIENTE, "Turno reservado por cliente");
    }

    @Override
    public List<TurnoReservadoDetalleDTO> obtenerTurnosPorCliente(Long clienteId) {
        UsuarioCliente cliente = usuarioClienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        List<Turno> turnos = turnoRepo.findByCliente_Id(clienteId);

        return turnos.stream()
                .map(t -> {
                    Taller taller = t.getAgenda().getTaller();
                    Vehiculo vehiculo = t.getVehiculo();
                    return new TurnoReservadoDetalleDTO(
                            t.getId(),
                            t.getFecha().toString(),
                            t.getHora().toString(),
                            t.getEstado() != null ? t.getEstado().name() : "SIN_ESTADO",
                            taller != null ? taller.getNombre() : "TALLER DESCONOCIDO",
                            taller != null ? taller.getUbicacion() : "SIN UBICACIÓN",
                            vehiculo != null ? vehiculo.getMarca() : "SIN MARCA",
                            vehiculo != null ? vehiculo.getModelo() : "SIN MODELO",
                            vehiculo != null ? vehiculo.getPatente() : "SIN PATENTE"
                    );
                })
                .toList();
    }
    @Override
    public List<TurnoPendienteTallerDTO> obtenerTurnosPendientesDelTaller(Long tallerId) {
        List<Turno> turnos = turnoRepo.findByAgenda_Taller_IdAndEstadoOrderByFechaAscHoraAsc(tallerId, Estado.PENDIENTE);

        return turnos.stream().map(t -> new TurnoPendienteTallerDTO(
                t.getId(),
                t.getFecha().toString(),
                t.getHora().toString(),
                t.getEstado() != null ? t.getEstado().name() : "SIN ESTADO",
                t.getDisponibilidad() != null ? t.getDisponibilidad().name() : "SIN DISPONIBILIDAD",
                t.getVehiculo() != null ? t.getVehiculo().getMarca() : "SIN DATOS",
                t.getVehiculo() != null ? t.getVehiculo().getModelo() : "SIN DATOS",
                t.getVehiculo() != null ? t.getVehiculo().getPatente() : "SIN DATOS",
                t.getAgenda().getTaller().getNombre(),
                t.getAgenda().getTaller().getUbicacion()
        )).toList();
    }


    @Override
    public void finalizarTurno(Long turnoId, FinalizarTurnoDTO datos) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getEstado() != Estado.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden finalizar turnos en estado PENDIENTE.");
        }

        turno.setEstado(Estado.REALIZADO);
        turno.setDescripcionTrabajo(datos.getDescripcionTrabajo());
        turno.setImagenes(datos.getImagenes());

        turnoRepo.save(turno);
    }


    @Override
    public List<HistorialTurnoDTO> obtenerHistorialPorVehiculo(Long vehiculoId) {
        List<Turno> turnos = turnoRepo.findByVehiculo_IdAndEstado(vehiculoId, Estado.REALIZADO);

        return turnos.stream()
                .map(t -> {
                    Taller taller = t.getAgenda().getTaller();
                    return new HistorialTurnoDTO(
                            t.getId(),
                            t.getFecha().toString(),
                            t.getHora().toString(),
                            taller != null ? taller.getNombre() : "TALLER DESCONOCIDO",
                            taller != null ? taller.getUbicacion() : "SIN UBICACIÓN",
                            t.getDescripcionTrabajo() != null ? t.getDescripcionTrabajo() : "Sin descripción"
                    );
                })
                .toList();
    }

    @Override
    public List<TurnoDTO> obtenerTodosLosTurnosPorTaller(Long tallerId) {
        List<Turno> turnos = turnoRepo.findByAgenda_Taller_IdOrderByFechaAscHoraAsc(tallerId);
        return turnos.stream().map(TurnoDTO::new).toList();
    }


    @Override
    public void calificarTurno(Long turnoId, CalificarTurnoDTO dto) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getEstado() != Estado.REALIZADO) {
            throw new IllegalStateException("Solo se pueden calificar turnos realizados.");
        }

        if (turno.getCalificacion() != null) {
            throw new IllegalStateException("Este turno ya fue calificado.");
        }

        int calificacion = dto.getPuntuacion();
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        turno.setCalificacion(calificacion);
        turnoRepo.save(turno);

        Taller taller = turno.getAgenda().getTaller();
        Long cantidad = taller.getCantidadCalificaciones() != null ? taller.getCantidadCalificaciones() : 0L;
        Double promedio = taller.getPromedioCalificacion() != null ? taller.getPromedioCalificacion() : 0.0;

        Double nuevaSuma = promedio * cantidad + calificacion;
        Long nuevaCantidad = cantidad + 1;
        Double nuevoPromedio = nuevaSuma / nuevaCantidad;

        taller.setCantidadCalificaciones(nuevaCantidad);
        taller.setPromedioCalificacion(nuevoPromedio);
        tallerRepo.save(taller);
    }


    @Override
    public void cancelarTurnoPorCliente(Long turnoId) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getEstado() != Estado.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden cancelar turnos pendientes.");
        }

        turno.setEstado(Estado.CANCELADO);
        turno.setDescripcionTrabajo("Cancelado por el cliente");
        turnoRepo.save(turno);
    }

    @Override
    public void cancelarTurnoPorTaller(Long turnoId) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getEstado() != Estado.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden cancelar turnos pendientes.");
        }

        turno.setEstado(Estado.CANCELADO);
        turno.setDescripcionTrabajo("Cancelado por el taller");
        turnoRepo.save(turno);
    }


    @Override
    public TurnoDetalleDTO obtenerDetalleTurno(Long turnoId) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getEstado() != Estado.REALIZADO) {
            throw new IllegalStateException("El turno no está finalizado");
        }

        Taller taller = turno.getAgenda().getTaller();
        Vehiculo vehiculo = turno.getVehiculo();

        return new TurnoDetalleDTO(
                turno.getId(),
                turno.getFecha().toString(),
                turno.getHora().toString(),
                taller != null ? taller.getNombre() : "TALLER SIN NOMBRE",
                turno.getDescripcionTrabajo(),
                turno.getImagenes() != null ? turno.getImagenes() : List.of(),
                vehiculo != null ? vehiculo.getMarca() : "SIN MARCA",
                vehiculo != null ? vehiculo.getModelo() : "SIN MODELO",
                vehiculo != null ? vehiculo.getPatente() : "SIN PATENTE"
        );
    }


    @Override
    public CalificacionDTO obtenerCalificacionDeTurno(Long turnoId) {
        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        if (turno.getCalificacion() == null) {
            return new CalificacionDTO(false, 0);
        }

        return new CalificacionDTO(true, turno.getCalificacion());
    }








}

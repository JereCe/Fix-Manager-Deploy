package com.equipo1.fix_manager.controller;

import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = "*")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;


    @PostMapping("/taller/{id}/crear") //Crea turno en un taller.
    public ResponseEntity<Void> crearTurno(@PathVariable Long id, @RequestBody CrearTurnoDTO datos) {
        turnoService.crearTurnoDesdeTaller(id, datos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/disponibles")//Lista los turnos disponibles de un taller
    public ResponseEntity<?> listarDisponiblesPorTaller(@RequestParam Long tallerId) {
        List<TurnoResponseDTO> turnos = turnoService.listarTurnosDisponiblesPorTaller(tallerId);

        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(turnos);
    }

    @PutMapping("/{turnoId}/reservar") //Reservar turno taller
    public ResponseEntity<Void> reservarTurno(
            @PathVariable Long turnoId,
            @RequestParam Long clienteId,
            @RequestParam Long vehiculoId) {

        turnoService.reservarTurno(turnoId, clienteId, vehiculoId);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @GetMapping("/cliente/{id}")//Ver turnos de  Reservados de un cliente
    public ResponseEntity<?> obtenerTurnosDelCliente(@PathVariable Long id) {
        List<TurnoReservadoDetalleDTO> turnos = turnoService.obtenerTurnosPorCliente(id);

        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(turnos);
    }


    @GetMapping("/taller/{id}/pendientes")
    public ResponseEntity<?> obtenerPendientesDelTaller(@PathVariable Long id) {
        List<TurnoPendienteTallerDTO> turnos = turnoService.obtenerTurnosPendientesDelTaller(id);

        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(turnos);
    }



    @PutMapping(path = "/{id}/finalizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> finalizarTurno(
            @PathVariable Long id,
            @RequestPart("descripcionTrabajo") String descripcionTrabajo,
            @RequestPart("imagenes") List<MultipartFile> imagenes) {

        FinalizarTurnoDTO datos = new FinalizarTurnoDTO();
        datos.setDescripcionTrabajo(descripcionTrabajo);
        datos.setImagenes(imagenes.stream()
                .map(this::guardarImagenYObtenerURL)
                .collect(Collectors.toList()));

        turnoService.finalizarTurno(id, datos);
        return ResponseEntity.ok().build();
    }

    private String guardarImagenYObtenerURL(MultipartFile imagen) {
        String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
        Path ruta = Paths.get("/app/uploads/", nombreArchivo);
        try {
            Files.createDirectories(ruta.getParent()); // Asegura que exista la carpeta
            Files.copy(imagen.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + nombreArchivo;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imagen", e);
        }
    }


    @GetMapping("/vehiculo/{id}/historial")//Historial Vehiculo
    public ResponseEntity<?> obtenerHistorialPorVehiculo(@PathVariable Long id) {
        List<HistorialTurnoDTO> historial = turnoService.obtenerHistorialPorVehiculo(id);

        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(historial);
    }


    @GetMapping("/taller/{tallerId}/todos")//Listar turno de un taller
    public ResponseEntity<List<TurnoDTO>> obtenerTodosLosTurnosPorTaller(@PathVariable Long tallerId) {
        List<TurnoDTO> turnos = turnoService.obtenerTodosLosTurnosPorTaller(tallerId);
        return ResponseEntity.ok(turnos);
    }

    @PutMapping("/{turnoId}/calificar") // Calificar taller
    public ResponseEntity<String> calificarTurno(@PathVariable Long turnoId,
                                                 @RequestBody CalificarTurnoDTO dto) {
        turnoService.calificarTurno(turnoId, dto);
        return ResponseEntity.ok("Turno calificado correctamente.");
    }


    @PutMapping("/{turnoId}/cancelar-cliente")
    public ResponseEntity<?> cancelarPorCliente(@PathVariable Long turnoId) {
        turnoService.cancelarTurnoPorCliente(turnoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{turnoId}/cancelar-taller")
    public ResponseEntity<?> cancelarPorTaller(@PathVariable Long turnoId) {
        turnoService.cancelarTurnoPorTaller(turnoId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> obtenerDetalleTurno(@PathVariable Long id) {
        TurnoDetalleDTO detalle = turnoService.obtenerDetalleTurno(id);
        return ResponseEntity.ok(detalle);
    }


    @GetMapping("/{turnoId}/calificacion")
    public ResponseEntity<CalificacionDTO> obtenerCalificacion(@PathVariable Long turnoId) {
        CalificacionDTO calificacion = turnoService.obtenerCalificacionDeTurno(turnoId);
        return ResponseEntity.ok(calificacion);
    }






}

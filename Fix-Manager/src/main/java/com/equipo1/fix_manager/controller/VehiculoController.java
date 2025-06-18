package com.equipo1.fix_manager.controller;

import com.equipo1.fix_manager.dto.MensajeRespuestaDTO;
import com.equipo1.fix_manager.dto.VehiculoDTO;
import com.equipo1.fix_manager.dto.VehiculoResponseDTO;
import com.equipo1.fix_manager.model.Vehiculo;
import com.equipo1.fix_manager.service.IVehiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearVehiculo(@RequestBody VehiculoDTO datos) {
        vehiculoService.crearVehiculo(datos);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 sin body
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> obtenerVehiculosDeUsuario(@PathVariable Long id) {
        List<VehiculoResponseDTO> vehiculos = vehiculoService.obtenerVehiculosPorUsuario(id);

        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(vehiculos); // 200
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Long id, @RequestBody VehiculoDTO datos) {
        vehiculoService.actualizarVehiculo(id, datos);
        return ResponseEntity.ok().build(); // 200 OK sin body
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerVehiculoPorId(@PathVariable Long id) {
        VehiculoResponseDTO vehiculo = vehiculoService.obtenerVehiculoPorId(id);
        return ResponseEntity.ok(vehiculo);
    }
}

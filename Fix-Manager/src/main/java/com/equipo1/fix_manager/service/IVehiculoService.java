package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.VehiculoDTO;
import com.equipo1.fix_manager.dto.VehiculoResponseDTO;
import com.equipo1.fix_manager.model.Vehiculo;

import java.util.List;

public interface IVehiculoService {

    Vehiculo crearVehiculo(VehiculoDTO datos);

    List<VehiculoResponseDTO> obtenerVehiculosPorUsuario(Long usuarioId);

    void actualizarVehiculo(Long id, VehiculoDTO datos);

    void eliminarVehiculo(Long id);

    VehiculoResponseDTO obtenerVehiculoPorId(Long id);
}

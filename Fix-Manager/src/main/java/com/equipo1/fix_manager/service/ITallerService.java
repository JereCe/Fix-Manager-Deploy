package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.CrearTallerDTO;
import com.equipo1.fix_manager.dto.TallerDTO;
import com.equipo1.fix_manager.dto.TallerResponseDTO;
import com.equipo1.fix_manager.model.Taller;
import com.equipo1.fix_manager.model.TipoReparacion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ITallerService {
    Taller crearTallerConImagen(Long usuarioId, CrearTallerDTO datos, MultipartFile imagen) throws IOException;
    TallerResponseDTO obtenerOTallerDeUsuario(Long usuarioId);

    List<TallerResponseDTO> obtenerTodos();

    List<TallerDTO> filtrar(String ciudad, TipoReparacion tipo);

    void actualizarTiposReparacion(Long tallerId, List<String> tipos);


}

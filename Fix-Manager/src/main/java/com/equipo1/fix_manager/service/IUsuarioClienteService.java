package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.model.UsuarioCliente;

import java.util.List;

public interface IUsuarioClienteService {



    AuthResponseDTO registrarUsuario(RegistroUsuarioClienteDTO datos);
    AuthResponseDTO login(LoginDTO dto);

    boolean existePorEmail(String email);

    void actualizarUsuario(Long id, EditarUsuarioDTO datos);

    UsuarioClienteResponseDTO obtenerPorId(Long id);

    void agregarTallerFavorito(Long clienteId, Long tallerId);
    void removerTallerFavorito(Long clienteId, Long tallerId);
    List<TallerDTO> obtenerTalleresFavoritos(Long clienteId);

}

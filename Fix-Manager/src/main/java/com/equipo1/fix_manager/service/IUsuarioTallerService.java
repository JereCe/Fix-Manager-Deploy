package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.model.Taller;
import com.equipo1.fix_manager.model.UsuarioTaller;
import org.springframework.web.multipart.MultipartFile;

public interface IUsuarioTallerService {





    void crearTallerParaUsuario(Long usuarioId, CrearTallerDTO datos, MultipartFile imagenLogo);




    void actualizarUsuario(Long id, EditarUsuarioDTO datos);

    boolean existePorEmail(String email);

    void actualizarTaller(Long id, CrearTallerDTO datos, MultipartFile imagenLogo);

    AuthResponseDTO registrarUsuario(RegistroUsuarioTallerDTO datos);
    AuthResponseDTO login(LoginDTO dto);
    TallerResponseDTO obtenerOTallerDeUsuario(Long usuarioId);

    UsuarioTallerResponseDTO obtenerPorId(Long id);



}

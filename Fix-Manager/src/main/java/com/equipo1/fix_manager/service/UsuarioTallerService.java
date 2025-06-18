package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.exception.UnauthorizedException;
import com.equipo1.fix_manager.model.Agenda;
import com.equipo1.fix_manager.model.Taller;
import com.equipo1.fix_manager.model.UsuarioTaller;
import com.equipo1.fix_manager.repository.ITallerRepository;
import com.equipo1.fix_manager.repository.IUsuarioTallerRepository;
import com.equipo1.fix_manager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioTallerService implements IUsuarioTallerService {

    @Autowired
    private IUsuarioTallerRepository usuarioTallerRepository;

    @Autowired
    private ITallerRepository tallerRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ITallerService tallerService;


    @Override
    public AuthResponseDTO registrarUsuario(RegistroUsuarioTallerDTO datos) {
        if (usuarioTallerRepository.existsByEmail(datos.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }

        UsuarioTaller nuevo = new UsuarioTaller();
        nuevo.setNombre(datos.getNombre());
        nuevo.setApellido(datos.getApellido());
        nuevo.setEmail(datos.getEmail());
        nuevo.setContrasenia(passwordEncoder.encode(datos.getContrasenia()));

        usuarioTallerRepository.save(nuevo);

        String token = jwtService.generateToken(nuevo.getEmail());

        return new AuthResponseDTO(
                token,
                "TALLER",
                nuevo.getId(),
                nuevo.getEmail(),
                nuevo.getNombre()
        );
    }


    @Override
    public AuthResponseDTO login(LoginDTO dto) {
        UsuarioTaller cliente = usuarioTallerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no encontrado"));

        if (!passwordEncoder.matches(dto.getContrasenia(), cliente.getContrasenia())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(cliente.getEmail());
        return new AuthResponseDTO(
                token,
                "TALLER",
                cliente.getId(),
                cliente.getEmail(),
                cliente.getNombre()
        );
    }


    @Override
    public void crearTallerParaUsuario(Long usuarioId, CrearTallerDTO datos, MultipartFile imagenLogo) {
        try {
            tallerService.crearTallerConImagen(usuarioId, datos, imagenLogo);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }




    @Override
    public TallerResponseDTO obtenerOTallerDeUsuario(Long usuarioId) {
        UsuarioTaller usuario = usuarioTallerRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Taller taller = usuario.getTaller();

        if (taller == null) return null;

        return new TallerResponseDTO(
                taller.getId(),
                taller.getNombre(),
                taller.getDescripcion(),
                taller.getUbicacion(),
                taller.getImagenLogo(),
                taller.getPromedioCalificacion(),
                taller.getCantidadCalificaciones()
        );
    }

    @Override
    public UsuarioTallerResponseDTO obtenerPorId(Long id) {
        UsuarioTaller user = usuarioTallerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario taller no encontrado"));
        return new UsuarioTallerResponseDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail());

    }


    @Override
    public boolean existePorEmail(String email) {
        return usuarioTallerRepository.existsByEmail(email);
    }

    @Override
    public void actualizarUsuario(Long id, EditarUsuarioDTO datos) {
        UsuarioTaller usuario = usuarioTallerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario taller no encontrado."));

        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setContrasenia(datos.getContrasenia());

        usuarioTallerRepository.save(usuario);
    }

    @Override
    public void actualizarTaller(Long usuarioId, CrearTallerDTO datos, MultipartFile imagenLogo) {
        Optional<UsuarioTaller> optionalUsuarioTaller = usuarioTallerRepository.findById(usuarioId);

        if (optionalUsuarioTaller.isEmpty()) {
            throw new RuntimeException("Usuario taller no encontrado");
        }

        UsuarioTaller usuarioTaller = optionalUsuarioTaller.get();
        Taller taller = usuarioTaller.getTaller();

        if (taller == null) {
            throw new RuntimeException("El usuario no tiene un taller asignado");
        }

        taller.setNombre(datos.getNombre());
        taller.setDescripcion(datos.getDescripcion());
        taller.setUbicacion(datos.getUbicacion());
        taller.setCiudad(datos.getCiudad());

        if (imagenLogo != null && !imagenLogo.isEmpty()) {
            try {
                String nombreArchivo = UUID.randomUUID().toString() + "_" + imagenLogo.getOriginalFilename();
                Path rutaDestino = Paths.get("uploads").resolve(nombreArchivo);
                Files.copy(imagenLogo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

                taller.setImagenLogo("/uploads/" + nombreArchivo);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen del taller", e);
            }
        }

        tallerRepository.save(taller);
    }




}

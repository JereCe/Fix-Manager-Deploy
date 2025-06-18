package com.equipo1.fix_manager.service;


import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.exception.UnauthorizedException;
import com.equipo1.fix_manager.model.Taller;
import com.equipo1.fix_manager.model.UsuarioCliente;
import com.equipo1.fix_manager.repository.ITallerRepository;
import com.equipo1.fix_manager.repository.IUsuarioClienteRepository;
import com.equipo1.fix_manager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioClienteService implements IUsuarioClienteService {

    @Autowired
    private IUsuarioClienteRepository usuarioClienteRepo;


    @Autowired
    private ITallerRepository tallerRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public AuthResponseDTO registrarUsuario(RegistroUsuarioClienteDTO datos) {
        if (usuarioClienteRepo.existsByEmail(datos.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }

        UsuarioCliente nuevo = new UsuarioCliente();
        nuevo.setNombre(datos.getNombre());
        nuevo.setApellido(datos.getApellido());
        nuevo.setDocumento(datos.getDocumento());
        nuevo.setEmail(datos.getEmail());
        nuevo.setContrasenia(passwordEncoder.encode(datos.getContrasenia()));

        usuarioClienteRepo.save(nuevo);

        String token = jwtService.generateToken(nuevo.getEmail());

        return new AuthResponseDTO(
                token,
                "CLIENTE",
                nuevo.getId(),
                nuevo.getEmail(),
                nuevo.getNombre()
        );
    }




    @Override
    public AuthResponseDTO login(LoginDTO dto) {
        UsuarioCliente cliente = usuarioClienteRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no encontrado"));

        if (!passwordEncoder.matches(dto.getContrasenia(), cliente.getContrasenia())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(cliente.getEmail());
        return new AuthResponseDTO(
                token,
                "CLIENTE",
                cliente.getId(),
                cliente.getEmail(),
                cliente.getNombre()
        );
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioClienteRepo.existsByEmail(email);
    }

    @Override
    public void actualizarUsuario(Long id, EditarUsuarioDTO datos) {
        UsuarioCliente usuario = usuarioClienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario cliente no encontrado."));

        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setContrasenia(datos.getContrasenia());

        usuarioClienteRepo.save(usuario);
    }

    @Override
    public UsuarioClienteResponseDTO obtenerPorId(Long id) {
        UsuarioCliente user = usuarioClienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario cliente no encontrado"));
        return new UsuarioClienteResponseDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getDocumento());
    }
    @Override
    public void agregarTallerFavorito(Long clienteId, Long tallerId) {
        UsuarioCliente cliente = usuarioClienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Taller taller = tallerRepo.findById(tallerId)
                .orElseThrow(() -> new IllegalArgumentException("Taller no encontrado"));

        if (!cliente.getTalleresFavoritos().contains(taller)) {
            cliente.getTalleresFavoritos().add(taller);
            usuarioClienteRepo.save(cliente);
        }
    }

    @Override
    public void removerTallerFavorito(Long clienteId, Long tallerId) {
        UsuarioCliente cliente = usuarioClienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Taller taller = tallerRepo.findById(tallerId)
                .orElseThrow(() -> new IllegalArgumentException("Taller no encontrado"));

        cliente.getTalleresFavoritos().remove(taller);
        usuarioClienteRepo.save(cliente);
    }

    @Override
    public List<TallerDTO> obtenerTalleresFavoritos(Long clienteId) {
        UsuarioCliente cliente = usuarioClienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        return cliente.getTalleresFavoritos().stream()
                .map(t -> new TallerDTO(
                        t.getId(),
                        t.getNombre(),
                        t.getDescripcion(),
                        t.getUbicacion(),
                        t.getImagenLogo(),
                        t.getPromedioCalificacion(),
                        t.getCiudad(),
                        null
                ))
                .toList();
    }







}

package com.equipo1.fix_manager.controller;

import com.equipo1.fix_manager.dto.AuthResponseDTO;
import com.equipo1.fix_manager.model.UsuarioCliente;
import com.equipo1.fix_manager.model.UsuarioTaller;
import com.equipo1.fix_manager.repository.IUsuarioClienteRepository;
import com.equipo1.fix_manager.repository.IUsuarioTallerRepository;
import com.equipo1.fix_manager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUsuarioClienteRepository usuarioClienteRepo;

    @Autowired
    private IUsuarioTallerRepository usuarioTallerRepo;




    @GetMapping("/check-status")
    public ResponseEntity<?> checkStatus(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no enviado");
        }

        String token = authHeader.substring(7);
        try {
            String email = jwtService.extractEmail(token);
            boolean valido = jwtService.isTokenValid(token, email);

            if (!valido) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }

            // Buscar el usuario en ambas tablas
            Optional<UsuarioCliente> clienteOpt = usuarioClienteRepo.findByEmail(email);
            if (clienteOpt.isPresent()) {
                UsuarioCliente c = clienteOpt.get();
                return ResponseEntity.ok(new AuthResponseDTO(
                        token,
                        "CLIENTE",
                        c.getId(),
                        c.getEmail(),
                        c.getNombre()
                ));
            }

            Optional<UsuarioTaller> tallerOpt = usuarioTallerRepo.findByEmail(email);
            if (tallerOpt.isPresent()) {
                UsuarioTaller t = tallerOpt.get();
                return ResponseEntity.ok(new AuthResponseDTO(
                        token,
                        "TALLER",
                        t.getId(),
                        t.getEmail(),
                        t.getNombre()
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
    }

}

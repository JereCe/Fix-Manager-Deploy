package com.equipo1.fix_manager.controller;

import com.equipo1.fix_manager.dto.*;
import com.equipo1.fix_manager.model.UsuarioCliente;
import com.equipo1.fix_manager.service.IUsuarioClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class UsuarioClienteController {

    @Autowired
    private IUsuarioClienteService usuarioService;


    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroUsuarioClienteDTO datos) {
        usuarioService.registrarUsuario(datos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO datos) {
        AuthResponseDTO response = usuarioService.login(datos);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable Long id, @RequestBody EditarUsuarioDTO datos) {
        usuarioService.actualizarUsuario(id, datos);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioClienteResponseDTO> obtenerCliente(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }


    @PutMapping("/{clienteId}/favoritos/{tallerId}")
    public ResponseEntity<?> agregarFavorito(@PathVariable Long clienteId, @PathVariable Long tallerId) {
        usuarioService.agregarTallerFavorito(clienteId, tallerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{clienteId}/favoritos/{tallerId}")
    public ResponseEntity<?> removerFavorito(@PathVariable Long clienteId, @PathVariable Long tallerId) {
        usuarioService.removerTallerFavorito(clienteId, tallerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clienteId}/favoritos")
    public ResponseEntity<List<TallerDTO>> listarFavoritos(@PathVariable Long clienteId) {
        List<TallerDTO> favoritos = usuarioService.obtenerTalleresFavoritos(clienteId);
        return ResponseEntity.ok(favoritos);
    }


}

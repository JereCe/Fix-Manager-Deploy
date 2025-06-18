package com.equipo1.fix_manager.controller;

import com.equipo1.fix_manager.dto.*;

import com.equipo1.fix_manager.model.UsuarioTaller;
import com.equipo1.fix_manager.service.IUsuarioTallerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/talleres")
@CrossOrigin(origins = "*")
public class UsuarioTallerController {

    @Autowired
    private IUsuarioTallerService usuarioTallerService;

    @Autowired
    private IUsuarioTallerService tallerService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroUsuarioTallerDTO datos) {
        usuarioTallerService.registrarUsuario(datos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO datos) {
        AuthResponseDTO response = usuarioTallerService.login(datos);
        return ResponseEntity.ok(response);
    }
    @PostMapping(path = "/{id}/crear-taller", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> crearTaller(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("ubicacion") String ubicacion,
            @RequestParam("ciudad") String ciudad,
            @RequestPart("imagen") MultipartFile imagenLogo) {

        CrearTallerDTO datos = new CrearTallerDTO();
        datos.setNombre(nombre);
        datos.setDescripcion(descripcion);
        datos.setUbicacion(ubicacion);
        datos.setCiudad(ciudad); // 👈 nuevo campo

        usuarioTallerService.crearTallerParaUsuario(id, datos, imagenLogo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("/{id}/taller")
    public ResponseEntity<?> obtenerTaller(@PathVariable Long id) {
        TallerResponseDTO dto = usuarioTallerService.obtenerOTallerDeUsuario(id);

        if (dto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> actualizarUsuTaller(@PathVariable Long id, @RequestBody EditarUsuarioDTO datos) {
        usuarioTallerService.actualizarUsuario(id, datos);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/modificar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> actualizarTallerConImagen(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("ubicacion") String ubicacion,
            @RequestParam("ciudad") String ciudad, // ← NUEVO
            @RequestPart(name = "imagen", required = false) MultipartFile imagenLogo) {

        CrearTallerDTO datos = new CrearTallerDTO();
        datos.setNombre(nombre);
        datos.setDescripcion(descripcion);
        datos.setUbicacion(ubicacion);
        datos.setCiudad(ciudad); // ← NUEVO

        usuarioTallerService.actualizarTaller(id, datos, imagenLogo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioTallerResponseDTO> obtenerUsuTaller(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTallerService.obtenerPorId(id));
    }

}

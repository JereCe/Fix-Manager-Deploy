package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.dto.CrearTallerDTO;
import com.equipo1.fix_manager.dto.TallerDTO;
import com.equipo1.fix_manager.dto.TallerResponseDTO;
import com.equipo1.fix_manager.model.Agenda;
import com.equipo1.fix_manager.model.Taller;
import com.equipo1.fix_manager.model.TipoReparacion;
import com.equipo1.fix_manager.model.UsuarioTaller;
import com.equipo1.fix_manager.repository.ITallerRepository;
import com.equipo1.fix_manager.repository.IUsuarioTallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TallerService implements ITallerService {

    @Autowired
    private ITallerRepository tallerRepository;

    @Autowired
    private IUsuarioTallerRepository usuarioTallerRepository;

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
                taller.getCantidadCalificaciones(),
                taller.getCiudad()
        );
    }

    public Taller crearTallerConImagen(Long usuarioId, CrearTallerDTO datos, MultipartFile imagen) throws IOException {
        UsuarioTaller usuario = usuarioTallerRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.getTaller() != null) {
            throw new IllegalStateException("El usuario ya tiene un taller registrado.");
        }

        Taller taller = new Taller();
        taller.setNombre(datos.getNombre());
        taller.setDescripcion(datos.getDescripcion());
        taller.setUbicacion(datos.getUbicacion());
        taller.setCiudad(datos.getCiudad());

        if (imagen != null && !imagen.isEmpty()) {
            // Ruta fija dentro del contenedor
            String rutaBase = "/app/uploads";
            File directorio = new File(rutaBase);
            if (!directorio.exists()) {
                boolean creado = directorio.mkdirs();
                System.out.println("Directorio '/app/uploads' creado: " + creado);
            }

            String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            String rutaCompleta = Paths.get(rutaBase, nombreArchivo).toString();
            imagen.transferTo(new File(rutaCompleta));

            // Ruta pública para acceder desde frontend
            taller.setImagenLogo("/uploads/" + nombreArchivo);
        }

        Agenda agenda = new Agenda();
        agenda.setTaller(taller);
        taller.setAgenda(agenda);

        Taller guardado = tallerRepository.save(taller);
        usuario.setTaller(guardado);
        usuarioTallerRepository.save(usuario);

        return guardado;
    }

    @Override
    public List<TallerResponseDTO> obtenerTodos() {
        return tallerRepository.findAll()
                .stream()
                .map(t -> new TallerResponseDTO(
                        t.getId(),
                        t.getNombre(),
                        t.getDescripcion(),
                        t.getUbicacion(),
                        t.getImagenLogo(),
                        t.getPromedioCalificacion(),
                        t.getCantidadCalificaciones(),
                        t.getCiudad()
                ))
                .toList();
    }

    @Override
    public List<TallerDTO> filtrar(String ciudad, TipoReparacion tipo) {
        List<Taller> talleres;

        if (ciudad != null && tipo != null) {
            talleres = tallerRepository.findByCiudadAndTipoReparacionesContaining(ciudad, tipo);
        } else if (ciudad != null) {
            talleres = tallerRepository.findByCiudad(ciudad);
        } else if (tipo != null) {
            talleres = tallerRepository.findByTipoReparacionesContaining(tipo);
        } else {
            talleres = tallerRepository.findAll();
        }

        return talleres.stream().map(t -> {
            TallerDTO dto = new TallerDTO();
            dto.setId(t.getId());
            dto.setNombre(t.getNombre());
            dto.setDescripcion(t.getDescripcion());
            dto.setCiudad(t.getCiudad());
            dto.setUbicacion(t.getUbicacion());
            dto.setImagenLogo(t.getImagenLogo());
            dto.setTipoReparaciones(t.getTipoReparaciones());
            dto.setPromedioCalificacion(t.getPromedioCalificacion());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void actualizarTiposReparacion(Long tallerId, List<String> tipos) {
        UsuarioTaller usuario = usuarioTallerRepository.findById(tallerId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Taller taller = usuario.getTaller();
        if (taller == null) {
            throw new RuntimeException("El usuario no tiene taller");
        }

        Set<TipoReparacion> tipoSet = tipos.stream()
                .map(String::toUpperCase)
                .map(TipoReparacion::valueOf)
                .collect(Collectors.toSet());

        taller.setTipoReparaciones(tipoSet);
        tallerRepository.save(taller);
    }

    @Override
    public TallerResponseDTO obtenerTallerPorId(Long id) {
        Optional<Taller> optional = tallerRepository.findById(id);
        if (optional.isEmpty()) return null;

        Taller taller = optional.get();

        return new TallerResponseDTO(
                taller.getId(),
                taller.getNombre(),
                taller.getDescripcion(),
                taller.getUbicacion(),
                taller.getImagenLogo(),
                taller.getPromedioCalificacion(),
                taller.getCantidadCalificaciones(),
                taller.getCiudad()
        );
    }
}

package com.equipo1.fix_manager.security;

import com.equipo1.fix_manager.model.UsuarioCliente;
import com.equipo1.fix_manager.model.UsuarioTaller;
import com.equipo1.fix_manager.repository.IUsuarioClienteRepository;
import com.equipo1.fix_manager.repository.IUsuarioTallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioClienteRepository clienteRepo;

    @Autowired
    private IUsuarioTallerRepository tallerRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioCliente cliente = clienteRepo.findByEmail(email).orElse(null);
        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getContrasenia())
                    .roles("CLIENTE")
                    .build();
        }

        UsuarioTaller taller = tallerRepo.findByEmail(email).orElse(null);
        if (taller != null) {
            return User.builder()
                    .username(taller.getEmail())
                    .password(taller.getContrasenia())
                    .roles("TALLER")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}

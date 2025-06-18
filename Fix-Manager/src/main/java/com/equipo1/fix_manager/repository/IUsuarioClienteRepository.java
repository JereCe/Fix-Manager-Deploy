package com.equipo1.fix_manager.repository;

import com.equipo1.fix_manager.model.UsuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioClienteRepository extends JpaRepository<UsuarioCliente,Long> {


    boolean existsByEmail(String email);

    Optional<UsuarioCliente> findByEmail(String email);
}

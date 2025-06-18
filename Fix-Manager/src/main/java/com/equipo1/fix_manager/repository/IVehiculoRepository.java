package com.equipo1.fix_manager.repository;

import com.equipo1.fix_manager.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo,Long>{

   List<Vehiculo> findByUsuarioCliente_Id(Long usuarioId);
}

package com.equipo1.fix_manager.repository;


import com.equipo1.fix_manager.model.Taller;
import com.equipo1.fix_manager.model.TipoReparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITallerRepository extends JpaRepository<Taller,Long> {


    List<Taller> findByCiudad(String ciudad);
    List<Taller> findByTipoReparacionesContaining(TipoReparacion tipo);
    List<Taller> findByCiudadAndTipoReparacionesContaining(String ciudad, TipoReparacion tipo);
}

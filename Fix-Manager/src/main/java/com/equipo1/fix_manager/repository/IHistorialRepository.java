package com.equipo1.fix_manager.repository;


import com.equipo1.fix_manager.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistorialRepository extends JpaRepository<Historial,Long> {
}

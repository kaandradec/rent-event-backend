package com.rentevent.repository;

import com.rentevent.model.patrocinador.Patrocinador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatrocinadorRepository extends JpaRepository<Patrocinador, Integer> {

}

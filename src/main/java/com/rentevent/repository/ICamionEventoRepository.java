package com.rentevent.repository;

import com.rentevent.model.camion.CamionEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICamionEventoRepository extends JpaRepository<CamionEvento,Integer> {
}

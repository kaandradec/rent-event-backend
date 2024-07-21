package com.rentevent.repository;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.transporte.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransporteRepository extends JpaRepository<Transporte, Integer> {
}

package com.rentevent.repository;

import com.rentevent.model.tarjeta.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITarjetaRepository extends JpaRepository<Tarjeta, Integer> {
}

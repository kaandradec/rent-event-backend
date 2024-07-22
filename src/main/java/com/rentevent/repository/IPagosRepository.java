package com.rentevent.repository;

import com.rentevent.model.pago.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagosRepository extends JpaRepository<Pago, Integer> {
}

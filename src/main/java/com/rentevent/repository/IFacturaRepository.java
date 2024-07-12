package com.rentevent.repository;

import com.rentevent.model.factura.Factura;
import com.rentevent.model.pago.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IFacturaRepository extends JpaRepository<Factura, Integer> {

    Optional<Factura> findFacturasByPagos(Pago pago);
}

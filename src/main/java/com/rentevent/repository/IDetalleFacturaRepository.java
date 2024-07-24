package com.rentevent.repository;

import com.rentevent.model.detalle_factura.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer> {
}

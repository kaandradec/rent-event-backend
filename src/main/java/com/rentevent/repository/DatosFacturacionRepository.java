package com.rentevent.repository;

import com.rentevent.model.datos_facturacion.DatosFacturacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatosFacturacionRepository extends JpaRepository<DatosFacturacion, Integer> {
}

package com.rentevent.repository;

import com.rentevent.model.datos_facturacion.DatosFacturacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDatosFacuturacionRepository extends JpaRepository<DatosFacturacion, Integer> {
}

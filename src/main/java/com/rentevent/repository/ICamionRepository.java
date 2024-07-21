package com.rentevent.repository;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.datos_facturacion.DatosFacturacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICamionRepository extends JpaRepository<Camion, Integer> {
}

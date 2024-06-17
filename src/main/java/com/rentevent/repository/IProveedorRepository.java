package com.rentevent.repository;

import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.servicio.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByNombre(String nombre);
}

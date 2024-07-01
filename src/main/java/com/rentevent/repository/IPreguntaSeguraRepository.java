package com.rentevent.repository;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.pregunta_segura.PreguntaSegura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPreguntaSeguraRepository extends JpaRepository<PreguntaSegura, Integer> {
    Optional<PreguntaSegura> findPreguntaSegurasByClienteAndPregunta(Cliente cliente, String pregunta);
}

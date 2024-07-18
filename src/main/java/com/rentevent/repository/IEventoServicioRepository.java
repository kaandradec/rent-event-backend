package com.rentevent.repository;

import com.rentevent.model.evento.EventoServicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventoServicioRepository extends JpaRepository<EventoServicio,Integer> {
}

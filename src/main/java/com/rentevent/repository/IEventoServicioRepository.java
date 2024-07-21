package com.rentevent.repository;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.servicio.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventoServicioRepository extends JpaRepository<EventoServicio, Integer> {
    List<EventoServicio> getAllByEvento(Evento evento);
}

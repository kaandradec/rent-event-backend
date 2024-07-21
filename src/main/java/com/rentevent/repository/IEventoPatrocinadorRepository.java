package com.rentevent.repository;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoPatrocinador;
import com.rentevent.model.evento.EventoServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventoPatrocinadorRepository extends JpaRepository<EventoPatrocinador, Integer> {
}

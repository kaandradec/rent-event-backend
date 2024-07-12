package com.rentevent.service;

import com.rentevent.model.evento.Evento;
import com.rentevent.repository.IEventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    @Autowired
    private IEventoRepository iEventoRepository;

    public List<Evento> listarUltimosEventos(){
        return iEventoRepository.findAllByOrderByIdDesc();
    }
}

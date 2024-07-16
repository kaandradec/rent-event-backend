package com.rentevent.service;

import com.rentevent.dto.request.CorreoRequest;
import com.rentevent.dto.response.EventoResponse;
import com.rentevent.model.evento.Evento;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IEventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    @Autowired
    private IEventoRepository iEventoRepository;
    @Autowired
    private IClienteRepository iClienteRepository;

    public EventoResponse listarUltimosEventos(CorreoRequest request){
        List<String> eventos= new ArrayList<>();
        List<LocalDate> fechas= new ArrayList<>();
        iEventoRepository.findByClienteOrderByIdDesc(
                iClienteRepository.findByCorreo(request.getCorreo()).orElseThrow()
        ).stream().forEach(evento -> {
            eventos.add(evento.getNombre());
            fechas.add(evento.getFecha());
        });

        return EventoResponse.builder().nombreEvento(eventos).fechaEvento(fechas).build();
    }
}

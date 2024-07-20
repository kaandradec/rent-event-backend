package com.rentevent.service;

import com.rentevent.dto.request.CarritoRequest;
import com.rentevent.dto.request.CorreoRequest;
import com.rentevent.dto.request.EventoRequest;
import com.rentevent.dto.response.EventoResponse;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.pago.Pago;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    @Autowired
    private IEventoRepository iEventoRepository;
    @Autowired
    private IClienteRepository iClienteRepository;
    @Autowired
    private IPagoRepository iPagoRepository;
    @Autowired
    private IServicioRepository iServicioRepository;
    @Autowired
    private IEventoServicioRepository iEventoServicioRepository;

    public EventoResponse listarUltimosEventos(CorreoRequest request) {
        List<String> eventos = new ArrayList<>();
        List<LocalDate> fechas = new ArrayList<>();
        iEventoRepository.findByClienteOrderByIdDesc(
                iClienteRepository.findByCorreo(request.getCorreo()).orElseThrow()
        ).stream().forEach(evento -> {
            eventos.add(evento.getNombre());
            fechas.add(evento.getFecha());
        });

        return EventoResponse.builder().nombreEvento(eventos).fechaEvento(fechas).build();
    }

    @Transactional
    public void generarEvento(EventoRequest request) {
        String fecha = (request.getFecha().substring(0,
                request.getFecha().indexOf("T")
        ));
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getFecha(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        // Extraer el objeto LocalTime
        LocalTime localTime = zonedDateTime.toLocalTime();

        // Encontrar cliente por correo
        Cliente cliente = iClienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

//         Calcular precio
        BigDecimal precio = Arrays.stream(request.getCart())
                .map(CarritoRequest::getCosto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//         Calcular IVA
        BigDecimal iva = precio.multiply(new BigDecimal("0.15"));

        // Crear lista de EventoServicio
        List<EventoServicio> eventoServicioList = new ArrayList<>();

        for (CarritoRequest carritoRequest : request.getCart()) {
            // Buscar el servicio correspondiente en el repositorio
            Servicio servicio = iServicioRepository.findByCodigo(carritoRequest.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

            // Crear un EventoServicio y asignar el servicio y la cantidad
            EventoServicio eventoServicio = EventoServicio.builder()
                    .servicio(servicio)
                    .cantidad(carritoRequest.getQuantity())
                    .build();

            // Agregar el EventoServicio a la lista
            eventoServicioList.add(eventoServicio);
        }

//         Crear y guardar evento
        Evento evento = Evento.builder()
                .nombre(request.getNombreEvento())
                .callePrincipal(request.getCallePrincipal())
                .calleSecundaria(request.getCalleSecundaria())
                .fecha(LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE))
                .pais(request.getPais())
                .referenciaDireccion(request.getReferencia())
                .region(request.getCiudad())
                .precio(precio.add(iva))
                .iva(iva)
                .hora(localTime)
                .eventoServicios(eventoServicioList)
                .cliente(cliente)
                .build();

        this.iEventoRepository.save(evento);
        this.iEventoServicioRepository.saveAll(eventoServicioList);

        List<Evento> list = cliente.getEventos();
        list.add(evento);
        cliente.setEventos(list);
        this.iClienteRepository.save(cliente);

//         Crear y guardar pago
        Pago pago = Pago.builder()
                .fecha(Date.valueOf(LocalDate.now()))
                .monto(precio.add(iva))
                .evento(evento)
                .tarjeta(cliente.getTarjetas().get(0))
                .build();
        iPagoRepository.save(pago);
    }

}

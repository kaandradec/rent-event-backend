package com.rentevent.service;

import com.rentevent.dto.request.CarritoRequest;
import com.rentevent.dto.request.EventoRequest;
import com.rentevent.dto.response.EventoResponse;
import com.rentevent.dto.response.PagoResponse;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoPatrocinador;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.pago.Pago;
import com.rentevent.model.patrocinador.Patrocinador;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.model.servicio.ServicioResponse;
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
import java.util.UUID;

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
    @Autowired
    private CamionService camionService;
    @Autowired
    private TransporteService transporteService;
    @Autowired
    private IPatrocinadorRepository iPatrocinadorRepository;
    @Autowired
    private IEventoPatrocinadorRepository iEventoPatrocinadorRepository;


//    public EventoResponse listarUltimosEventos(CorreoRequest request) {
//        List<String> eventos = new ArrayList<>();
//        List<LocalDate> fechas = new ArrayList<>();
//        iEventoRepository.findByClienteOrderByIdDesc(
//                iClienteRepository.findByCorreo(request.getCorreo()).orElseThrow()
//        ).stream().forEach(evento -> {
//            eventos.add(evento.getNombre());
//            fechas.add(evento.getFecha());
//        });
//
//        return EventoResponse.builder().nombreEvento(eventos).fechaEvento(fechas).build();
//    }

    public EventoResponse obtenerEventoPorCodigo(String codigo) {
        Evento evento = this.iEventoRepository.findByCodigo(codigo).orElseThrow();

        List<Pago> pagos = evento.getPagos();
        List<PagoResponse> listaPagosResponse = new ArrayList<>();

        List<EventoServicio> eventosServiciosTablaRompimiento = this.iEventoServicioRepository.getAllByEvento(evento);
        List<Servicio> servicios = new ArrayList<>();
        List<ServicioResponse> listaServiciosResponse = new ArrayList<>();

        eventosServiciosTablaRompimiento.forEach(eventServ -> {
            Integer idServicio = eventServ.getId();
            Servicio serv = this.iServicioRepository.findById(idServicio).orElseThrow();
            servicios.add(serv);
        });

        servicios.forEach(serv -> {
            listaServiciosResponse.add(
                    ServicioResponse.builder()
                            .costo(serv.getCosto())
                            .tipo(serv.getTipo())
                            .descripcion(serv.getDescripcion())
                            .imagenes(serv.getImagenes())
                            .build()
            );
        });

        pagos.forEach(pago -> {
            listaPagosResponse.add(
                    PagoResponse.builder()
                            .monto(pago.getMonto())
                            .fecha(pago.getFecha())
                            .build()
            );

        });

        return EventoResponse.builder()
                .codigo(evento.getCodigo())
                .nombre(evento.getNombre())
                .pais(evento.getPais())
                .region(evento.getRegion())
                .callePrincipal(evento.getCallePrincipal())
                .calleSecundaria(evento.getCalleSecundaria())
                .referenciaDireccion(evento.getReferenciaDireccion())
                .fecha(evento.getFecha())
                .hora(evento.getHora())
                .iva(evento.getIva())
                .precio(evento.getPrecio())
                .pagos(listaPagosResponse)
                .servicios(listaServiciosResponse)
                .build();
    }

    public List<EventoResponse> listarEventosDeCliente(String correo) {
        List<EventoResponse> listaEventoResposes = new ArrayList<>();
        Cliente cliente = iClienteRepository.findByCorreo(correo).orElseThrow();
        List<Evento> eventos = iEventoRepository.getAllByCliente(cliente);


        eventos.forEach(evento -> {
            List<Pago> pagos = iPagoRepository.findByEvento(evento);
            List<PagoResponse> pagoResponses = new ArrayList<>();

            pagos.forEach(pago -> {
                pagoResponses.add(PagoResponse.builder()
                        .fecha(pago.getFecha())
                        .monto(pago.getMonto())
                        .build());
            });

            EventoResponse response = EventoResponse.builder()
                    .codigo(evento.getCodigo())
                    .nombre(evento.getNombre())
                    .pais(evento.getPais())
                    .region(evento.getRegion())
                    .callePrincipal(evento.getCallePrincipal())
                    .calleSecundaria(evento.getCalleSecundaria())
                    .referenciaDireccion(evento.getReferenciaDireccion())
                    .fecha(evento.getFecha())
                    .hora(evento.getHora())
                    .iva(evento.getIva())
                    .precio(evento.getPrecio())
                    .pagos(pagoResponses)
                    .build();

            listaEventoResposes.add(response);
        });

        return listaEventoResposes;
    }

    @Transactional
    public void generarEvento(EventoRequest request) {
        Integer totalServicios = 0;

        String fecha = (request.getFecha().substring(0,
                request.getFecha().indexOf("T")
        ));
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getFecha(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        // Extraer el objeto LocalTime
        LocalTime localTime = zonedDateTime.toLocalTime();

        // Encontrar cliente por correo
        Cliente cliente = this.iClienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Evento> list = cliente.getEventos();

        // Calcular precio
        BigDecimal precio = Arrays.stream(request.getCart())
                .map(CarritoRequest::getCosto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calcular IVA
        BigDecimal iva = precio.multiply(new BigDecimal("0.15"));

        // Crear lista de EventoServicio
        List<EventoServicio> eventoServicioList = new ArrayList<>();


        //Codigo para evento
        UUID uuid = UUID.randomUUID();

        // Crear y guardar evento
        Evento evento = Evento.builder()
                .codigo("EVENT-" + uuid.toString().substring(0, 8))
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
                .cliente(cliente)
                .build();

        this.iEventoRepository.save(evento);

        // Crear lista de EventoServicio
        for (CarritoRequest carritoRequest : request.getCart()) {
            // Buscar el servicio correspondiente en el repositorio
            Servicio servicio = this.iServicioRepository.findByCodigo(carritoRequest.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            totalServicios = totalServicios + carritoRequest.getQuantity();

            // Crear un EventoServicio y asignar el servicio y la cantidad
            EventoServicio eventoServicio = EventoServicio.builder()
                    .evento(evento) // Asignar el evento
                    .servicio(servicio)
                    .cantidad(carritoRequest.getQuantity())
                    .build();

            // Agregar el EventoServicio a la lista
            eventoServicioList.add(eventoServicio);
        }

        //todo : valido para 1 patrocinador xd
        if (Integer.valueOf(request.getAsistentes()) > 100) {
            Patrocinador patrocinador;
            patrocinador = iPatrocinadorRepository.findAll().get(0);
            iEventoPatrocinadorRepository.save(
                    EventoPatrocinador.builder()
                            .evento(evento)
                            .patrocinador(patrocinador)
                            .build());
        }


        this.iEventoServicioRepository.saveAll(eventoServicioList);

        list.add(evento);
        cliente.setEventos(list);
        this.iClienteRepository.save(cliente);

        // Crear y guardar pago
        Pago pago = Pago.builder()
                .fecha(Date.valueOf(LocalDate.now()))
                .monto(precio.add(iva))
                .evento(evento)
                .tarjeta(cliente.getTarjetas().get(0))
                .build();
        this.iPagoRepository.save(pago);

        try {
            camionService.reservarCamion(totalServicios, evento);
            transporteService.reservarTransporte(Integer.valueOf(request.getAsistentes()), evento);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
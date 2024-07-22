package com.rentevent.service;

import com.rentevent.dto.request.CarritoRequest;
import com.rentevent.dto.request.EventoRequest;
import com.rentevent.dto.response.EventoResponse;
import com.rentevent.dto.response.PagoResponse;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.enums.MetodoPago;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoPatrocinador;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.incidencia.Incidencia;
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
import java.util.*;

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

    /**
     * Obtiene la respuesta detallada de un evento por su código.
     * Este método recupera un evento específico por su código y construye una respuesta detallada que incluye
     * información sobre el evento, los pagos asociados y los servicios incluidos en el evento.
     *
     * @param codigo El código único del evento a buscar.
     * @return EventoResponse Una representación detallada del evento, incluyendo pagos y servicios.
     * @throws NoSuchElementException Si el evento con el código proporcionado no se encuentra.
     */
    public EventoResponse obtenerEventoPorCodigo(String codigo) {
        System.out.println("CODIGO" + codigo);
        Evento evento = this.iEventoRepository.findByCodigo(codigo).orElseThrow();

        List<Pago> pagos = evento.getPagos();
        List<PagoResponse> listaPagosResponse = new ArrayList<>();

        List<EventoServicio> eventosServiciosTablaRompimiento = this.iEventoServicioRepository.findAllByEventoEquals(evento);
//        System.out.println("SERVICIOS" + eventosServiciosTablaRompimiento);

        List<Servicio> servicios = new ArrayList<>();
        List<ServicioResponse> listaServiciosResponse = new ArrayList<>();

        eventosServiciosTablaRompimiento.forEach(eventServ -> {
            Servicio serv = eventServ.getServicio();
            servicios.add(serv);
        });

        servicios.forEach(serv -> {
            listaServiciosResponse.add(
                    ServicioResponse.builder()
                            .nombre(serv.getNombre())
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
                .estado(evento.getEstado())
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

    /**
     * Lista los eventos asociados a un cliente específico.
     * Este método recupera todos los eventos vinculados a un cliente, identificado por su correo electrónico,
     * y construye una lista de respuestas detalladas de cada evento. La información incluye detalles del evento,
     * así como los pagos asociados a cada uno.
     *
     * @param correo El correo electrónico del cliente cuyos eventos se quieren listar.
     * @return Una lista de {@link EventoResponse} con los detalles de cada evento asociado al cliente.
     * @throws NoSuchElementException Si no se encuentra el cliente con el correo proporcionado.
     */
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
                    .estado(evento.getEstado())
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

        System.out.println(listaEventoResposes);
        return listaEventoResposes;
    }

    /**
     * Genera un nuevo evento basado en la solicitud proporcionada.
     * Este método procesa la solicitud de creación de un evento, calculando el precio total, el IVA,
     * y generando los servicios asociados al evento. También maneja la asignación de patrocinadores
     * para eventos con un gran número de asistentes y realiza el registro de pagos correspondientes.
     * Finalmente, reserva los recursos necesarios como camiones y transporte.
     *
     * @param request La solicitud que contiene los detalles del evento a generar.
     * @throws RuntimeException Si ocurre un error durante la generación del evento, como cliente no encontrado,
     *                          servicio no encontrado, o fallos en la reserva de recursos.
     */
    @Transactional
    public void generarEvento(EventoRequest request) {
        int totalServicios = 0;

        String fecha = request.getFecha().substring(0, request.getFecha().indexOf("T"));
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getFecha(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        // Extraer el objeto LocalTime
        LocalTime localTime = zonedDateTime.toLocalTime();

        // Encontrar cliente por correo
        Cliente cliente = iClienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Evento> list = cliente.getEventos();

        // Calcular precio
        BigDecimal precio = Arrays.stream(request.getCart())
                .map(CarritoRequest::getCosto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular IVA
        BigDecimal iva = precio.multiply(new BigDecimal("0.15"));
        BigDecimal totalPrecio = precio.add(iva);

        // Crear lista de EventoServicio
        List<EventoServicio> eventoServicioList = new ArrayList<>();

        //Codigo para evento
        UUID uuid = UUID.randomUUID();

        // Crear y guardar evento
        Evento evento = Evento.builder()
                .codigo("EVENT-" + uuid.toString().substring(0, 8))
                .estado("ACTIVO")
                .nombre(request.getNombreEvento())
                .callePrincipal(request.getCallePrincipal())
                .calleSecundaria(request.getCalleSecundaria())
                .fecha(LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE))
                .pais(request.getPais())
                .referenciaDireccion(request.getReferencia())
                .region(request.getCiudad())
                .precio(request.getTotal())
                .iva(iva)
                .hora(localTime)
                .cliente(cliente)
                .build();

        iEventoRepository.save(evento);

        // Crear lista de EventoServicio
        for (CarritoRequest carritoRequest : request.getCart()) {
            Servicio servicio = iServicioRepository.findByCodigo(carritoRequest.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            totalServicios += carritoRequest.getQuantity();

            EventoServicio eventoServicio = EventoServicio.builder()
                    .evento(evento)
                    .servicio(servicio)
                    .cantidad(carritoRequest.getQuantity())
                    .build();

            eventoServicioList.add(eventoServicio);
        }

        if (Integer.parseInt(request.getAsistentes()) > 100) {
            Patrocinador patrocinador = iPatrocinadorRepository.findAll().get(0);
            iEventoPatrocinadorRepository.save(EventoPatrocinador.builder()
                    .evento(evento)
                    .patrocinador(patrocinador)
                    .build());
        }

        iEventoServicioRepository.saveAll(eventoServicioList);

        list.add(evento);
        cliente.setEventos(list);
        iClienteRepository.save(cliente);

        BigDecimal pagoMonto = request.getPago();

        if (Objects.equals(pagoMonto, request.getTotal())) {
            // Crear y guardar el primer pago
            Pago pago = Pago.builder()
                    .fecha(Date.valueOf(LocalDate.now()))
                    .monto(pagoMonto)
                    .evento(evento)
                    .metodoPago(MetodoPago.Tarjeta)
                    .tarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1))
                    .build();
            iPagoRepository.save(pago);

            // Crear y guardar el segundo pago
            Pago pago2 = Pago.builder()
                    .fecha(Date.valueOf(LocalDate.now()))
                    .monto(pagoMonto)
                    .evento(evento)
                    .metodoPago(MetodoPago.Tarjeta)
                    .tarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1))
                    .build();
            iPagoRepository.save(pago2);
        } else {
            // Crear y guardar un solo pago
            Pago pago = Pago.builder()
                    .fecha(Date.valueOf(LocalDate.now()))
                    .monto(pagoMonto)
                    .evento(evento)
                    .metodoPago(MetodoPago.Tarjeta)
                    .tarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1))
                    .build();
            iPagoRepository.save(pago);
        }

        try {
            camionService.reservarCamion(totalServicios, evento);
            transporteService.reservarTransporte(Integer.parseInt(request.getAsistentes()), evento);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancela un evento específico.
     * Este método busca un evento por su código y cambia su estado a "CANCELADO".
     * Luego, guarda los cambios en el repositorio. Si el evento no se encuentra, se lanza una excepción.
     *
     * @param codigo El código único del evento a cancelar.
     * @throws NoSuchElementException Si el evento con el código proporcionado no se encuentra.
     */
    public void cancelarEvento(String codigo) {
        Evento evento = this.iEventoRepository.findByCodigo(codigo).orElseThrow();
        evento.setEstado("CANCELADO");
        this.iEventoRepository.save(evento);
    }

    /**
     * Guarda una incidencia relacionada con un evento específico.
     * Este método busca un evento por su código y le asigna una nueva incidencia con la descripción proporcionada.
     * Luego, guarda los cambios en el repositorio. Si el evento no se encuentra, se lanza una excepción.
     *
     * @param codigoEvento El código único del evento al que se le asignará la incidencia.
     * @param descripcion  La descripción de la incidencia a guardar.
     * @throws NoSuchElementException Si el evento con el código proporcionado no se encuentra.
     */
    public void guardarIncidencia(String codigoEvento, String descripcion) {
        Evento evento = this.iEventoRepository.findByCodigo(codigoEvento).orElseThrow();
        Incidencia incidencia = Incidencia.builder()
                .descripcion(descripcion)
                .build();
        evento.setIncidencia(incidencia);

        this.iEventoRepository.save(evento);

    }

}
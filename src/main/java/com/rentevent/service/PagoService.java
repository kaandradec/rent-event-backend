package com.rentevent.service;

import com.rentevent.dto.request.PagoRequest;
import com.rentevent.dto.request.ValidarPagoRequest;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.enums.MetodoPago;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.factura.Factura;
import com.rentevent.model.pago.Pago;
import com.rentevent.model.tarjeta.Tarjeta;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IEventoRepository;
import com.rentevent.repository.IFacturaRepository;
import com.rentevent.repository.IPagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PagoService {
    @Autowired
    private final IClienteRepository iClienteRepository;
    private final IEventoRepository iEventoRepository;
    private final IPagoRepository iPagoRepository;
    private final IFacturaRepository iFacturaRepository;
    private final FacturaService facturaService;

    /**
     * Valida si un evento ha sido completamente pagado basándose en los pagos realizados.
     * Este método recupera el cliente y el evento basado en los detalles proporcionados en la solicitud.
     * Luego, suma el monto total pagado de todos los pagos asociados. Si el monto total pagado coincide
     * con el precio del evento, indica que el evento está completamente pagado.
     *
     * @param request La solicitud que contiene los detalles para identificar al cliente y al evento.
     * @return {@code Boolean} indicando si el evento está completamente pagado ({@code true}) o no ({@code false}).
     * @throws SecurityException Si el cliente o el evento no pueden ser encontrados basado en los detalles proporcionados.
     */
    public Boolean validarEventoPagado(ValidarPagoRequest request) {
        Cliente cliente = iClienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new SecurityException("Cliente no encontrado - validarGenerarFactura"));

        Evento evento = iEventoRepository.findByNombreAndCliente(request.getNombreEvento(), cliente)
                .orElseThrow(() -> new SecurityException("Evento no encontrado - validarGenerarFactura"));

        List<Pago> pagoList = evento.getPagos();
        BigDecimal totalPagado = new BigDecimal("0.00");
        pagoList.forEach(pago -> totalPagado.add(pago.getMonto()));

        //devuelve True si esta pagado
        return totalPagado.compareTo(evento.getPrecio()) == 0;

    }

//    @Transactional
//    public void generarPago(PagoRequest request) throws Exception {
//        Evento evento = iEventoRepository.findByCodigo(request.getEvento())
//                .orElseThrow();
//        Cliente cliente = evento.getCliente();
//        DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();
//        UUID uuid = UUID.randomUUID();
//
//        iPagosRepository.save(Pago.builder()
//                .tarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1))
//                .monto(request.getPago())
//                .fecha(Date.valueOf(LocalDate.now()))
//                .metodoPago(MetodoPago.Tarjeta)
//                .evento(evento)
//                .build());

    //        List<DetalleFactura> detalleFacturas = new ArrayList<>();
//        Set<Servicio> servicioSet = new HashSet<>();
//
//        evento.getEventoServicios().forEach(eventoServicio -> {
//            servicioSet.add(eventoServicio.getServicio());
//        });
//        detalleFacturas.add(DetalleFactura.builder().servicios(servicioSet)
//                .cantidad(1).precioUnitario(new BigDecimal(2)).build());
//
//        iFacturaRepository.save(Factura.builder()
//                .cedulaCliente(datosFacturacion.getCedulaCliente())
//                .direccionCliente(datosFacturacion.getDireccionCliente())
//                .direccionEmpresa("UCE, Av America")
//                .empresa("RentEvent")
//                .fechaEmision(Date.valueOf(LocalDate.now()))
//                .iva(evento.getIva())
//                .nombreCliente(request.getNombreFactura())
//                .numero("f-" + uuid.toString().substring(0, 8))
//                .rucEmpresa("1717171771001")
//                .pagos(evento.getPagos())
//                .cliente(cliente)
//                .detalleFacturas(detalleFacturas)
//                .total(evento.getPrecio()).build());
//
//    }

    /**
     * Genera un pago para un evento específico.
     * Este método intenta crear y guardar un pago basado en la información proporcionada en la solicitud.
     * Primero, busca el evento por su código y recupera los datos de facturación del cliente asociado al evento.
     * Luego, construye un objeto de pago con los detalles proporcionados, incluyendo el método de pago como efectivo,
     * y lo guarda en la base de datos. Si el proceso es exitoso, imprime un mensaje de confirmación.
     * En caso de error, como no encontrar el evento, se captura la excepción, se imprime un mensaje de error y se lanza
     * la excepción para manejo externo.
     *
     * @param request La solicitud que contiene los detalles necesarios para generar el pago, incluyendo el código del evento
     *                y el monto del pago.
     * @throws Exception Si ocurre un error durante el proceso, como no encontrar el evento especificado.
     */
//    @Transactional
//    public void generarPago(PagoRequest request) throws Exception {
//        try {
//            Evento evento = iEventoRepository.findByCodigo(request.getEvento())
//                    .orElseThrow(() -> new Exception("Evento no encontrado"));
//            Cliente cliente = evento.getCliente();
//            DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();
//            UUID uuid = UUID.randomUUID();
//
//            Pago pago = Pago.builder()
//                    .tarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1))
//                    .monto(new BigDecimal(String.valueOf(request.getPago())))
//                    .fecha(Date.valueOf(LocalDate.now()))
//                    .metodoPago(MetodoPago.Efectivo)
//                    .evento(evento)
//                    .build();
//
//            iPagosRepository.save(pago);
//            System.out.println("Pago guardado exitosamente: " + pago);
//        } catch (Exception e) {
//            System.err.println("Error al generar el pago: " + e.getMessage());
//            throw e;
//        }
//    }
    @Transactional
    public void generarPago(Evento evento, BigDecimal monto, boolean esPagoCompleto) throws Exception {
        Pago pago1 = Pago.builder()
                .fecha(Date.valueOf(LocalDate.now()))
                .monto(esPagoCompleto ? BigDecimal.ZERO : monto)
                .evento(evento)
                .metodoPago(MetodoPago.Tarjeta)
                .tarjeta(evento.getCliente().getTarjetas().get(0))
                .build();
        iPagoRepository.save(pago1);

        Pago pago2 = Pago.builder()
                .fecha(Date.valueOf(LocalDate.now()))
                .monto(monto)
                .evento(evento)
                .metodoPago(MetodoPago.Tarjeta)
                .tarjeta(evento.getCliente().getTarjetas().get(0))
                .build();
        iPagoRepository.save(pago2);

        if (esPagoCompleto) {
            Factura factura = facturaService.generarFactura(evento, List.of(pago1, pago2));
            pago1.setFactura(factura);
            pago2.setFactura(factura);
            iPagoRepository.save(pago1);
            iPagoRepository.save(pago2);
        }
    }


}

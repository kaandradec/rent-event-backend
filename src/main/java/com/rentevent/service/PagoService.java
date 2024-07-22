package com.rentevent.service;

import com.rentevent.dto.request.PagoRequest;
import com.rentevent.dto.request.ValidarPagoRequest;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.datos_facturacion.DatosFacturacion;
import com.rentevent.model.detalle_factura.DetalleFactura;
import com.rentevent.model.enums.MetodoPago;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.factura.Factura;
import com.rentevent.model.pago.Pago;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IEventoRepository;
import com.rentevent.repository.IFacturaRepository;
import com.rentevent.repository.IPagosRepository;
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
    private final IPagosRepository iPagosRepository;
    private final IFacturaRepository iFacturaRepository;

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
    @Transactional
    public void generarPago(PagoRequest request) throws Exception {
        try {
            Evento evento = iEventoRepository.findByCodigo(request.getEvento())
                    .orElseThrow(() -> new Exception("Evento no encontrado"));
            Cliente cliente = evento.getCliente();
            DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();
            UUID uuid = UUID.randomUUID();

            Pago pago = Pago.builder()
                    .tarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1))
                    .monto(new BigDecimal(String.valueOf(request.getPago())))
                    .fecha(Date.valueOf(LocalDate.now()))
                    .metodoPago(MetodoPago.Efectivo)
                    .evento(evento)
                    .build();

            iPagosRepository.save(pago);
            System.out.println("Pago guardado exitosamente: " + pago);
        } catch (Exception e) {
            System.err.println("Error al generar el pago: " + e.getMessage());
            throw e;
        }
    }
}

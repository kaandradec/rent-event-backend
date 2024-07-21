package com.rentevent.service;

import com.rentevent.dto.request.DatosFacturacionRequest;
import com.rentevent.dto.request.ValidarPagoRequest;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.datos_facturacion.DatosFacturacion;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.factura.Factura;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IDatosFacuturacionRepository;
import com.rentevent.repository.IEventoRepository;
import com.rentevent.repository.IFacturaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturaService {
    @Autowired
    private final IFacturaRepository iFacturaRepository;
    private final IClienteRepository iClienteRepository;
    private final IDatosFacuturacionRepository iDatosFacuturacionRepository;
    private final IEventoRepository iEventoRepository;

    @Transactional
    public void generarFactura(DatosFacturacionRequest request) {
        Cliente cliente = this.iClienteRepository.findByCorreo(
                        request.getCorreo())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        Factura factura;
        DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();

        if (datosFacturacion != null) {
            factura = Factura.builder()
                    .rucEmpresa("100000001")
                    .empresa("Rent-Event S.A.")
                    .direccionEmpresa("Universidad Central del Ecuador, Av. Universitaria, Quito 170129")
                    .cedulaCliente(datosFacturacion.getCedulaCliente())
                    .nombreCliente(datosFacturacion.getNombreCliente())
                    .direccionCliente(datosFacturacion.getDireccionCliente())
                    .build();
        } else {
            datosFacturacion = DatosFacturacion.builder()
                    .cedulaCliente(request.getCedula())
                    .nombreCliente(request.getNombre())
                    .direccionCliente(request.getDireccion())
                    .cliente(cliente)
                    .build();
            iDatosFacuturacionRepository.save(datosFacturacion);

            factura = Factura.builder()
                    .rucEmpresa("100000001")
                    .empresa("Rent-Event S.A.")
                    .direccionEmpresa("Universidad Central del Ecuador, Av. Universitaria, Quito 170129")
                    .cedulaCliente(request.getCedula())
                    .nombreCliente(request.getNombre())
                    .direccionCliente(request.getDireccion())
                    .build();
        }

        iFacturaRepository.save(factura);

    }

    public Boolean validarExisteFactura(ValidarPagoRequest request) {
        //devuelve True si esta pagado
        return this.pedirFactura(request).isPresent();
    }

    public Optional<Factura> pedirFactura(ValidarPagoRequest request) {
        Cliente cliente = iClienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new SecurityException("Cliente no encontrado - validarGenerarFactura"));

        Evento evento = iEventoRepository.findByNombreAndCliente(request.getNombreEvento(), cliente)
                .orElseThrow(() -> new SecurityException("Evento no encontrado - validarGenerarFactura"));

            iFacturaRepository.findFacturasByPagos(evento.getPagos().get(0));//todo: esto debe dar problemas xd

        return iFacturaRepository.findFacturasByPagos(
                evento.getPagos().get(0)//todo : x2
        );
    }


}

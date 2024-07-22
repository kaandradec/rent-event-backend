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

    /**
     * Genera una factura para un cliente basado en los datos de facturación proporcionados.
     * Si el cliente ya tiene datos de facturación, se utiliza esa información para generar la factura.
     * De lo contrario, se crean nuevos datos de facturación con la información proporcionada y luego se genera la factura.
     *
     * @param request Los datos de facturación proporcionados para generar la factura.
     */
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

    /**
     * Solicita la factura asociada a un pago específico de un evento.
     * Este método busca un cliente y un evento basados en los datos proporcionados en el request.
     * Luego, intenta encontrar una factura asociada al primer pago del evento.
     * Nota: Este método asume que siempre hay al menos un pago asociado al evento y que la factura se puede encontrar
     * basándose en este primer pago. Sin embargo, esta implementación puede causar problemas si el evento no tiene pagos
     * asociados o si la factura no se encuentra por algún motivo.
     *
     * @param request Contiene los datos necesarios para identificar el cliente y el evento, incluyendo el correo del cliente
     *                y el nombre del evento.
     * @return Un {@link Optional<Factura>} que contiene la factura asociada al primer pago del evento si se encuentra,
     * o un {@link Optional#empty()} si no se encuentra la factura.
     * @throws SecurityException Si el cliente o el evento no se encuentran basados en los criterios proporcionados.
     */
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

package com.rentevent.service;

import com.rentevent.dto.request.ValidarPagoRequest;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.pago.Pago;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IDatosFacuturacionRepository;
import com.rentevent.repository.IEventoRepository;
import com.rentevent.repository.IFacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {
    @Autowired
    private final IFacturaRepository ifacturaRepository;
    private final IClienteRepository iClienteRepository;
    private final IDatosFacuturacionRepository iDatosFacuturacionRepository;
    private final IEventoRepository iEventoRepository;
    public Boolean validarEventoPagado(ValidarPagoRequest request) {
        Cliente cliente = iClienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new SecurityException("Cliente no encontrado - validarGenerarFactura"));

        Evento evento = iEventoRepository.findByNombreAndCliente(request.getNombreEvento(), cliente)
                .orElseThrow(() -> new SecurityException("Evento no encontrado - validarGenerarFactura"));

        List<Pago> pagoList = evento.getPagos();
        BigDecimal totalPagado = new BigDecimal("0.00");
        pagoList.stream().forEach(pago -> totalPagado.add(pago.getMonto()));

        //devuelve True si esta pagado
        return totalPagado.compareTo(evento.getPrecio()) == 0 ;

    }
}

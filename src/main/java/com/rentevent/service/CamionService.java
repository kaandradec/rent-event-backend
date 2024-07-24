package com.rentevent.service;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.camion.CamionEvento;
import com.rentevent.model.detalle_factura.DetalleFactura;
import com.rentevent.model.evento.Evento;
import com.rentevent.repository.ICamionEventoRepository;
import com.rentevent.repository.ICamionRepository;
import com.rentevent.repository.IDetalleFacturaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CamionService {
    @Autowired
    private final ICamionRepository iCamionRepository;
    private final ICamionEventoRepository iCamionEventoRepository;
    private final IDetalleFacturaRepository iDetalleFacturaRepository;


    /**
     * Reserva camiones para un evento específico basado en la cantidad de servicios solicitados.
     * Este método calcula el número de camiones necesarios basándose en una regla de 1 camión por cada 2 servicios.
     * Selecciona aleatoriamente los camiones disponibles y los asocia con el evento.
     *
     * @param servicios La cantidad de servicios solicitados para el evento.
     * @param evento    El evento para el cual se reservan los camiones.
     * @throws Exception Si no hay camiones registrados o no hay suficientes camiones disponibles.
     */
    @Transactional
    public void reservarCamion(int totalServicios, Evento evento) throws Exception {
        List<Camion> camionList = iCamionRepository.findAll();
        int totalCamionesDisponibles = camionList.size();

        if (totalCamionesDisponibles <= 0) {
            throw new Exception("No hay camiones registrados.");
        }

        int camionesNecesarios = (int) Math.ceil(totalServicios / 2.0);

        if (camionesNecesarios > totalCamionesDisponibles) {
            throw new Exception("No hay suficientes camiones disponibles para la cantidad de utilería solicitada.");
        }

        Set<Camion> camionesReservados = new HashSet<>();

        while (camionesReservados.size() < camionesNecesarios) {
            int camionRandomIndex = (int) (Math.random() * totalCamionesDisponibles);
            camionesReservados.add(camionList.get(camionRandomIndex));
        }

        for (Camion camion : camionesReservados) {
            iCamionEventoRepository.save(
                    CamionEvento.builder()
                            .camion(camion)
                            .evento(evento)
                            .build()
            );

            DetalleFactura detalleFactura = DetalleFactura.builder()
                    .precioUnitario(new BigDecimal(5.5))
                    .cantidad(1)
                    .evento(evento)
                    .subTotal(new BigDecimal(5.5))
                    .factura(null)  // La factura se asignará más tarde
                    .camiones(new HashSet<>(Collections.singletonList(camion)))
                    .build();
            iDetalleFacturaRepository.save(detalleFactura);

            System.out.println("Camión reservado: " + camion.getId());
        }
    }

}

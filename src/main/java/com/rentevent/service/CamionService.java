package com.rentevent.service;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.camion.CamionEvento;
import com.rentevent.model.evento.Evento;
import com.rentevent.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CamionService {
    @Autowired
    private final ICamionRepository iCamionRepository;
    private final ICamionEventoRepository iCamionEventoRepository;
    @Transactional
    public void reservarCamion(Integer request, Evento evento) throws Exception {
        List<Camion> camionList = iCamionRepository.findAll();
        int totalCamionesDisponibles = camionList.size();

        if (totalCamionesDisponibles <= 0) {
            throw new Exception("No hay camiones registrados.");
        }

        // Calcula el número de camiones necesarios (1 camión por cada 20 sillas)
        int camionesNecesarios = (int) Math.ceil(request / 20.0);

        if (camionesNecesarios > totalCamionesDisponibles) {
            throw new Exception("No hay suficientes camiones disponibles para la cantidad de utileria solicitada.");
        }

        // Conjunto para almacenar los camiones seleccionados sin repetir
        Set<Camion> camionesReservados = new HashSet<>();

        while (camionesReservados.size() < camionesNecesarios) {
            int camionRandomIndex = (int) (Math.random() * totalCamionesDisponibles);
            camionesReservados.add(camionList.get(camionRandomIndex));
        }

        // Aquí puedes procesar la reserva de los camiones en el evento
        for (Camion camion : camionesReservados) {
            // Lógica para asociar el camión con el evento, por ejemplo:
            camionesReservados.forEach(cami ->
                    iCamionEventoRepository.save(
                            CamionEvento.builder()
                                    .camion(cami)
                                    .evento(evento)
                                    .build())
            );

            System.out.println("Camión reservado: " + camion.getId());
        }
    }
}

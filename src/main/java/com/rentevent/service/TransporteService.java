package com.rentevent.service;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoTransporte;
import com.rentevent.model.transporte.Transporte;
import com.rentevent.repository.IEventoTransporteRepository;
import com.rentevent.repository.ITransporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransporteService {
    @Autowired
    private final ITransporteRepository iTransporteRepository;
    private final IEventoTransporteRepository iEventoTransporteRepository;

    /**
     * Reserva transportes para un evento basado en el número de personas asistentes.
     * Este método busca todos los transportes disponibles y calcula cuántos son necesarios
     * para acomodar a un número dado de personas, asumiendo una capacidad de 20 personas por transporte.
     * Si no hay suficientes transportes disponibles, se lanza una excepción.
     * Los transportes son seleccionados aleatoriamente y reservados para el evento.
     *
     * @param personas El número de personas que asistirán al evento y que necesitan transporte.
     * @param evento   El evento para el cual se está reservando el transporte.
     * @throws Exception Si no hay transportes registrados o no hay suficientes transportes disponibles.
     */
    public void reservarTransporte(Integer personas, Evento evento) throws Exception {
        List<Transporte> transporteList = iTransporteRepository.findAll();
        int totalTransportesDisponibles = transporteList.size();

        if (totalTransportesDisponibles <= 0) {
            throw new Exception("No hay transportes registrados.");
        }

        // Calcula el número de transportes necesarios (1 transporte por cada 20 personas)
        int transportesNecesarios = (int) Math.ceil(personas / 20.0);

        if (transportesNecesarios > totalTransportesDisponibles) {
            throw new Exception("No hay suficientes transportes disponibles para la cantidad de personas solicitadas.");
        }

        // Conjunto para almacenar los transportes seleccionados sin repetir
        Set<Transporte> transportesReservados = new HashSet<>();

        while (transportesReservados.size() < transportesNecesarios) {
            int transporteRandomIndex = (int) (Math.random() * totalTransportesDisponibles);
            transportesReservados.add(transporteList.get(transporteRandomIndex));
        }

        // Aquí puedes procesar la reserva de los transportes en el evento
        transportesReservados.forEach(transporte ->
                iEventoTransporteRepository.save(
                        EventoTransporte.builder()
                                .transporte(transporte)
                                .evento(evento)
                                .build()
                )
        );

        transportesReservados.forEach(transporte -> System.out.println("Transporte reservado: " + transporte.getId()));
    }
}

package com.rentevent.service;

import com.rentevent.dto.request.PreguntaSeguraRequest;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.pregunta_segura.PreguntaSegura;
import com.rentevent.repository.IClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreguntaSeguraService {
    @Autowired
    IClienteRepository clienteRepository;
    public List<String> pedirPreguntasSeguras() {
        return List.of(PreguntaSegura.preguntasSeguras);
    }

    public void actualizarPreguntasSeguras(PreguntaSeguraRequest preguntaSeguraRequest) {
        Cliente cliente = this.clienteRepository.findByCorreo(
                        preguntaSeguraRequest.getCorreo())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        List<PreguntaSegura> preguntaSeguras = cliente.getPreguntaSeguras();
        preguntaSeguras.clear();  // Limpiar la lista existente

        PreguntaSegura preguntaSegura1 = PreguntaSegura.builder()
                .pregunta(preguntaSeguraRequest.getPregunta1())
                .respuesta(preguntaSeguraRequest.getRespuesta1())
                .cliente(cliente)  // Asegurarse de asignar el cliente
                .build();
        PreguntaSegura preguntaSegura2 = PreguntaSegura.builder()
                .pregunta(preguntaSeguraRequest.getPregunta2())
                .respuesta(preguntaSeguraRequest.getRespuesta2())
                .cliente(cliente)  // Asegurarse de asignar el cliente
                .build();
        PreguntaSegura preguntaSegura3 = PreguntaSegura.builder()
                .pregunta(preguntaSeguraRequest.getPregunta3())
                .respuesta(preguntaSeguraRequest.getRespuesta3())
                .cliente(cliente)  // Asegurarse de asignar el cliente
                .build();

        preguntaSeguras.add(preguntaSegura1);
        preguntaSeguras.add(preguntaSegura2);
        preguntaSeguras.add(preguntaSegura3);

        clienteRepository.save(cliente);
    }
}

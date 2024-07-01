package com.rentevent.service;

import com.rentevent.dto.request.ListaPreguntasSegurasRequest;
import com.rentevent.dto.request.ValidarPreguntaSeguraRequest;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.pregunta_segura.PreguntaSegura;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IPreguntaSeguraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreguntaSeguraService {
    @Autowired
    private final IClienteRepository clienteRepository;
    private final IPreguntaSeguraRepository iPreguntaSegura;
    private final PasswordEncoder passwordEncoder;


    public List<String> listarPreguntasSeguras() {
        return List.of(PreguntaSegura.preguntasSeguras);
    }

    public String pedirPreguntaSeguraRandom(String correo) {
        int rand = (int) (Math.random() * 3);
        return clienteRepository
                .findByCorreo(correo).get().getPreguntaSeguras().get(rand).getPregunta();
    }


    public void actualizarPreguntasSeguras(ListaPreguntasSegurasRequest preguntaSeguraRequest) {
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

    public void comprobarRespuestaSegura(ValidarPreguntaSeguraRequest validarPreguntaSeguraRequest) {
        Optional<Cliente> cliente = clienteRepository.findByCorreo(validarPreguntaSeguraRequest.getCorreo());
        if (!cliente.isPresent()) {
            throw new NotFoundException("Usuario Incorrecto");
        }
        Optional<PreguntaSegura> respuestaSegura = iPreguntaSegura
                .findPreguntaSegurasByClienteAndPregunta(cliente.get(), validarPreguntaSeguraRequest.getPregunta());
        if (!respuestaSegura.isPresent()) {
            throw new NotFoundException("Preguntas no existen");
        }

        if (!passwordEncoder.matches(validarPreguntaSeguraRequest.getRespuesta(), respuestaSegura.get().getRespuesta())) {
            throw new SecurityException();
        }



    }
}

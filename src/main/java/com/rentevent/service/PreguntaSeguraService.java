package com.rentevent.service;

import com.rentevent.dto.request.ListaPreguntasSegurasRequest;
import com.rentevent.dto.request.ValidarPreguntaSeguraRequest;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.pregunta_segura.PreguntaSegura;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IPreguntaSeguraRepository;
import jakarta.transaction.Transactional;
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

    /**
     * Selecciona aleatoriamente una pregunta de seguridad de un cliente basado en su correo electrónico.
     * Este método genera un índice aleatorio dentro del rango del número de preguntas seguras disponibles
     * para el cliente especificado y devuelve la pregunta correspondiente a ese índice. Es crucial manejar
     * los casos en los que no se encuentre el cliente o que este no tenga preguntas de seguridad asociadas
     * para evitar lanzar excepciones inesperadas.
     *
     * @param correo El correo electrónico del cliente del cual se desea obtener una pregunta de seguridad aleatoria.
     * @return La pregunta de seguridad seleccionada de manera aleatoria.
     */
    public String pedirPreguntaSeguraRandom(String correo) {
        int rand = (int) (Math.random() * 3);
        return clienteRepository
                .findByCorreo(correo).get().getPreguntaSeguras().get(rand).getPregunta();
    }

    /**
     * Actualiza las preguntas seguras de un cliente específico.
     * Este método primero busca al cliente por su correo electrónico y, si lo encuentra, limpia su lista actual
     * de preguntas seguras. Luego, crea nuevas instancias de {@link PreguntaSegura} con las preguntas y respuestas
     * proporcionadas en la solicitud. Estas nuevas preguntas seguras se asocian al cliente y se añaden a su lista.
     * Finalmente, se guarda el cliente en la base de datos con sus preguntas seguras actualizadas.
     *
     * @param preguntaSeguraRequest La solicitud que contiene el correo del cliente y las nuevas preguntas y respuestas seguras.
     * @throws NotFoundException Si no se encuentra un cliente con el correo proporcionado.
     */
    @Transactional
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

    /**
     * Comprueba la respuesta a una pregunta de seguridad para un cliente específico.
     * Este método verifica si la respuesta proporcionada a una pregunta de seguridad específica es correcta.
     * Primero, busca al cliente por su correo electrónico. Si el cliente no existe, lanza una excepción.
     * Luego, busca la pregunta de seguridad específica para ese cliente. Si la pregunta no existe, lanza otra excepción.
     * Finalmente, compara la respuesta proporcionada con la respuesta almacenada utilizando un codificador de contraseñas.
     * Si la comparación falla, lanza una excepción de seguridad, indicando que la respuesta es incorrecta.
     *
     * @param validarPreguntaSeguraRequest La solicitud que contiene el correo del cliente, la pregunta de seguridad y la respuesta a validar.
     * @throws NotFoundException Si el cliente no se encuentra o si la pregunta de seguridad no existe.
     * @throws SecurityException Si la respuesta proporcionada no coincide con la respuesta almacenada.
     */
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

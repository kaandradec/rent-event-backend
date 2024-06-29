package com.rentevent.controller;

import com.rentevent.dto.request.CorreoRequest;
import com.rentevent.dto.request.ListaPreguntasSegurasRequest;
import com.rentevent.dto.response.ListaPreguntasSegurasResponse;
import com.rentevent.model.pregunta_segura.PreguntaSegura;
import com.rentevent.service.PreguntaSeguraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/security/preguntasseguras")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PreguntasSegurasController {
    @Autowired
    PreguntaSeguraService preguntaSeguraService;

    //todo: encriptar respuesta de preguntas y hacer la validacion

    @GetMapping(value = "/get")
    public ResponseEntity<ListaPreguntasSegurasResponse> obtenerCliente() {
        List<String> listaPreguntas = preguntaSeguraService.listarPreguntasSeguras();

        ListaPreguntasSegurasResponse preguntaSeguraResponse = ListaPreguntasSegurasResponse.builder()
                .preguntasSeguras(listaPreguntas).build();

        return ResponseEntity.ok(preguntaSeguraResponse);
    }
    @PutMapping(value = "/get")
    public ResponseEntity<ListaPreguntasSegurasResponse> obtenerPreguntaRandomCliente(@RequestBody CorreoRequest request) {
        String preguntaSegura = preguntaSeguraService.pedirPreguntaSeguraRandom(request.getCorreo());

        ListaPreguntasSegurasResponse preguntaSeguraResponse = ListaPreguntasSegurasResponse.builder()
                .preguntasSeguras(List.of(preguntaSegura)).build();

        return ResponseEntity.ok(preguntaSeguraResponse);
    }
    @PutMapping("/actualizar")
    public ResponseEntity<?> updateService(@RequestBody ListaPreguntasSegurasRequest request) {

        System.out.println(request);

        ListaPreguntasSegurasRequest preguntaSeguraRequest =
                ListaPreguntasSegurasRequest.builder()
                        .correo(request.getCorreo())
                        .pregunta1(request.getPregunta1())
                        .respuesta1(request.getRespuesta1())
                        .pregunta2(request.getPregunta2())
                        .respuesta2(request.getRespuesta2())
                        .pregunta3(request.getPregunta3())
                        .respuesta3(request.getRespuesta3())
                        .build();
        preguntaSeguraService.actualizarPreguntasSeguras(preguntaSeguraRequest);

        return ResponseEntity.ok("Servicio actualizado satisfactoriamente");
    }
}

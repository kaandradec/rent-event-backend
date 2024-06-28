package com.rentevent.controller;

import com.rentevent.dto.request.ClienteRegionRequest;
import com.rentevent.dto.request.PreguntaSeguraRequest;
import com.rentevent.dto.response.GeneroResponse;
import com.rentevent.dto.response.PreguntaSeguraResponse;
import com.rentevent.service.GeneroService;
import com.rentevent.service.PreguntaSeguraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/security/preguntasseguras")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PreguntasSegurasController {
    @Autowired
    PreguntaSeguraService preguntaSeguraService;

    @GetMapping(value = "/get")
    public ResponseEntity<PreguntaSeguraResponse> obtenerCliente() {
        List<String> listaPreguntas = preguntaSeguraService.pedirPreguntasSeguras();

        PreguntaSeguraResponse preguntaSeguraResponse = PreguntaSeguraResponse.builder()
                .preguntasSeguras(listaPreguntas).build();

        return ResponseEntity.ok(preguntaSeguraResponse);
    }
    @PutMapping("/actualizar")
    public ResponseEntity<?> updateService(@RequestBody PreguntaSeguraRequest request) {

        System.out.println(request);

        PreguntaSeguraRequest preguntaSeguraRequest =
                PreguntaSeguraRequest.builder()
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

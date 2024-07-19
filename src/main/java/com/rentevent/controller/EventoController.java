package com.rentevent.controller;

import com.rentevent.dto.request.CorreoRequest;
import com.rentevent.dto.request.EventoRequest;
import com.rentevent.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class EventoController {
    @Autowired
    private final EventoService eventoService;

    @PutMapping(value = "/listar")
    public ResponseEntity<?> generarFacutura(@RequestBody CorreoRequest request) {
        try {
            return ResponseEntity.ok().body(eventoService.listarUltimosEventos(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/generar")
    public ResponseEntity<?> generarEvento(@RequestBody EventoRequest request) {
        try {
            System.out.println(request);
            eventoService.generarEvento(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

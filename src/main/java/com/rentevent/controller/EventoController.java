package com.rentevent.controller;

import com.rentevent.dto.request.EventoRequest;
import com.rentevent.dto.request.IncidenciaRequest;
import com.rentevent.dto.response.EventoResponse;
import com.rentevent.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class EventoController {
    @Autowired
    private final EventoService eventoService;

//    @PutMapping(value = "/listar")
//    public ResponseEntity<?> listarEventosDeCliente(@RequestBody CorreoRequest request) {
//        try {
//            return ResponseEntity.ok().body(eventoService.listarUltimosEventos(request));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping(value = "/obtener/{codigo}")
    public ResponseEntity<?> listarEventoPorCodigo(@PathVariable String codigo) {

        try {
            EventoResponse evento = this.eventoService.obtenerEventoPorCodigo(codigo);
            return ResponseEntity.ok().body(evento);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/listarTodos/{correo}")
    public ResponseEntity<?> listarEventosDeCliente(@PathVariable String correo) {
        System.out.println(correo);
        try {
            List<EventoResponse> lista = this.eventoService.listarEventosDeCliente(correo);
            return ResponseEntity.ok().body(lista);
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
            return ResponseEntity.ok("Registrado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value = "cancelar/{codigo}")
    public ResponseEntity<?> cancelarEvento(@PathVariable String codigo) {
        try {
            this.eventoService.cancelarEvento(codigo); // cambiar estado a CANCELADO
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value = "/guardarIncidencia")
    public ResponseEntity<?> guardarIncidencia(@RequestBody IncidenciaRequest request) {
        System.out.println(request);
        try {
            this.eventoService.guardarIncidencia(request.getCodigoServicio(), request.getDescripcion());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

package com.rentevent.controller.independientes;

import com.rentevent.dto.request.ClienteRegionRequest;
import com.rentevent.dto.request.CorreoRequest;
import com.rentevent.dto.response.GeneroResponse;
import com.rentevent.service.CorreoService;
import com.rentevent.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/correo")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class CorreoController {
    @Autowired
    CorreoService correoService;

    @PutMapping(value = "/validar")
    public ResponseEntity<?> obtenerCliente(@RequestBody CorreoRequest request) {
        System.out.println(request);

        return correoService.verificarCorreo(request)?
                ResponseEntity.ok().body(true):
                ResponseEntity.notFound().build();
    }
}

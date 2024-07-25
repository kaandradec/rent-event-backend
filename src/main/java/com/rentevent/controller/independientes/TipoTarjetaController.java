package com.rentevent.controller.independientes;

import com.rentevent.dto.response.TipoTarjetaResponse;
import com.rentevent.service.TipoTarjetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipos-tarjetas")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class TipoTarjetaController {
    @Autowired
    TipoTarjetaService tipoTarjetaService;

    @GetMapping(value = "/get")
    public ResponseEntity<TipoTarjetaResponse> obtenerCliente() {
        List<String> tiposTarjeta = tipoTarjetaService.getTipoTarjeta()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        TipoTarjetaResponse tarjetaResponse = TipoTarjetaResponse.builder()
                .tipoTarjeta(tiposTarjeta).build();

        return ResponseEntity.ok(tarjetaResponse);
    }
}

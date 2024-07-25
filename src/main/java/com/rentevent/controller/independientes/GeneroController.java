package com.rentevent.controller.independientes;

import com.rentevent.dto.response.GeneroResponse;
import com.rentevent.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/generos")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@CrossOrigin(origins = {"https://rent-event-frontend-msei.vercel.app", "http://localhost:3000"})
public class GeneroController {
    @Autowired
    GeneroService generoService;
    @GetMapping(value = "/get")
    public ResponseEntity<GeneroResponse> obtenerCliente() {
        List<String> generos = generoService.getGeneros()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        GeneroResponse generoResponse = GeneroResponse.builder()
                .generos(generos).build();

        return ResponseEntity.ok(generoResponse);
    }
}

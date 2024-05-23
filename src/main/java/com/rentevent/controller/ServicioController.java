package com.rentevent.controller;

import com.rentevent.model.servicio.ServicioDTO;
import com.rentevent.model.servicio.ServicioRequest;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/servicio")
@RequiredArgsConstructor // Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}) // Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class ServicioController {
    private final ServicioService servicioService;

    @GetMapping(value = "{codigo}")
    public ResponseEntity<ServicioDTO> getServicio(@PathVariable String codigo)
    {
        ServicioDTO servicioDTO = servicioService.getService(codigo);
        if (servicioDTO==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicioDTO);
    }

    @PutMapping()
    public ResponseEntity<ServicioResponse> updateService(@RequestBody ServicioRequest servicioRequest)
    {
        return ResponseEntity.ok(servicioService.updateService(servicioRequest));
    }
}

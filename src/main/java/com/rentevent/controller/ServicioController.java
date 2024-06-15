package com.rentevent.controller;

import com.rentevent.model.servicio.ServicioDTO;
import com.rentevent.model.servicio.ServicioRequest;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
// Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class ServicioController {
    private final ServicioService servicioService;

    @GetMapping(value = "{id}")
    public ResponseEntity<ServicioResponse> getServicio(@PathVariable Integer id) {
        ServicioResponse servicio = servicioService.obtenerServicioPorId(id);
        if (servicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicio);
    }

//    @PutMapping()
//    public ResponseEntity<ServicioResponse> updateService(@RequestBody ServicioRequest servicioRequest) {
//        return ResponseEntity.ok(servicioService.updateService(servicioRequest));
//    }

    @PostMapping("/imagen/{id}")
    public ResponseEntity<?> subirImagen(@PathVariable Integer id, @RequestPart final MultipartFile file) {
        System.out.println("HOLA");
        this.servicioService.subirImagen(id, file);
        System.out.println("ADIOS");
        return ResponseEntity.ok("La imagen se subió satisfactoriamente");
    }
}

package com.rentevent.controller;

import com.rentevent.model.servicio.ServicioDTO;
import com.rentevent.model.servicio.ServicioRequest;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.service.ServicioService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}) // Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class DemoController {
    private final ServicioService servicioService;

    private String apiKey;
    @GetMapping(value = "/apikey")
    public ResponseEntity<String> getApiKey() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("API_KEY");

        return ResponseEntity.ok("API-KEY desde archivo .env -> " + apiKey);
    }

//    @GetMapping(value = "{codigo}")
//    public ResponseEntity<ServicioResponse> getServicio(@PathVariable String codigo) {
//        ServicioResponse servicioResponse = servicioService.getService(codigo);
//        if (servicioResponse == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(servicioResponse);
//    }

//    @PutMapping()
//    public ResponseEntity<ServicioResponse> updateService(@RequestBody ServicioRequest servicioRequest) {
//        return ResponseEntity.ok(servicioService.updateService(servicioRequest));
//    }
}

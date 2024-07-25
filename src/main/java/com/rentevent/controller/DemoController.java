package com.rentevent.controller;

import com.rentevent.service.ServicioService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://rent-event-frontend-msei.vercel.app")
public class DemoController {
    private final ServicioService servicioService;

    private String apiKey;
    @GetMapping(value = "/apikey")
    public ResponseEntity<String> getApiKey() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("API_KEY");

        return ResponseEntity.ok("API-KEY desde archivo  -> " + apiKey);
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

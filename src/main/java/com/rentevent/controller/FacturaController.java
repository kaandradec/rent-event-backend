package com.rentevent.controller;

import com.rentevent.dto.request.DatosFacturacionRequest;
import com.rentevent.dto.response.FacturaResponse;
import com.rentevent.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
// Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class FacturaController {
    @Autowired
    private final FacturaService facturaService;

//    @GetMapping(value = "/recuperar")
//    public void generarFacutura(@RequestBody DatosFacturacionRequest request) {
//        facturaService.generarFactura(request);
//    }

    @GetMapping("/obtener/{codigoEvento}")
    private ResponseEntity<?> obtenerFacturaPorCodigoEvento(@PathVariable String codigoEvento) {
        try {

            FacturaResponse facturaResponse = this.facturaService.pedirFactura2(codigoEvento);
            return ResponseEntity.ok().body(facturaResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

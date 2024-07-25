package com.rentevent.controller;

import com.rentevent.dto.request.PagoRequest;
import com.rentevent.dto.request.ValidarPagoRequest;
import com.rentevent.service.FacturaService;
import com.rentevent.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://rent-event-frontend-msei.vercel.app")
public class PagoController {
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private PagoService pagoService;

    @PostMapping(value = "/nuevo")
    public ResponseEntity<?> generarPagosCompletados(@RequestBody PagoRequest request) {
        try {
//            pagoService.generarPago(request);
            return ResponseEntity.accepted().body("hola");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Errorrrrrr");
        }
    }

    @PutMapping(value = "/validar")
    public ResponseEntity<?> validarPagosCompletados(@RequestBody ValidarPagoRequest request) {

        try {
            if (pagoService.validarEventoPagado(request)) {
                return (facturaService.validarExisteFactura(request)) ?
                        //TODO: HACER
                        ResponseEntity.ok().body("fdfd") :
                        ResponseEntity.accepted().body("hola");
            } else {
                return ResponseEntity.status(402).body("Error: Falta completar el pago");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Errorrrrrr");
        }
    }


}

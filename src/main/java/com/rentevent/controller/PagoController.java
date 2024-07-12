package com.rentevent.controller;

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
@CrossOrigin(origins = {"http://localhost:5173"})
public class PagoController {
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private PagoService pagoService;

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
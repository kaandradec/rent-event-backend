package com.rentevent.controller;

import com.rentevent.dto.request.DatosFacturacionRequest;
import com.rentevent.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

}

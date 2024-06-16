package com.rentevent.controller;

import com.rentevent.dto.response.ClienteResponse;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.service.ClienteService;
import com.rentevent.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
// Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class ClienteController {
    private final ClienteService clienteService;
    @GetMapping(value = "/{usuario}")
    public ResponseEntity<ClienteResponse> obtenerCliente(@PathVariable String usuario) {
        Cliente cliente = clienteService.obtenerClientePorUsername(usuario).orElseThrow();

        ClienteResponse clienteResponse = ClienteResponse.builder()
                .correo(cliente.getUsername())
                .nombre(cliente.getFirstname())
                .apellido(cliente.getLastname())
                .nacionalidad(cliente.getNacionalidad())
                .genero(cliente.getGenero())
                .direccion(cliente.getDireccion())
                .build();

        return ResponseEntity.ok(clienteResponse);
    }
}

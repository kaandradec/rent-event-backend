package com.rentevent.controller;

import com.rentevent.auth.AuthService;
import com.rentevent.dto.request.ClientePassRequest;
import com.rentevent.dto.request.ClienteTelefonoRequest;
import com.rentevent.dto.response.ClienteDetallesResponse;
import com.rentevent.dto.response.ClienteResponse;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
@CrossOrigin(origins = {"http://localhost:5173"})
// Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class ClienteController {
    @Autowired
    private final AuthService authService;
    private final ClienteService clienteService;

    @GetMapping(value = "/{user}")
    public ResponseEntity<ClienteResponse> obtenerCliente(@PathVariable String user) {
        Cliente cliente = clienteService.obtenerClientePorUsername(user).orElseThrow();

        ClienteResponse clienteResponse = ClienteResponse.builder()
                .correo(cliente.getUsername())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .build();

        return ResponseEntity.ok(clienteResponse);
    }

    @GetMapping(value = "/detalles/{usuario}")
    public ResponseEntity<ClienteDetallesResponse> obtenerDetallesCliente(@PathVariable String usuario) {
        Cliente cliente = clienteService.obtenerClientePorUsername(usuario).orElseThrow();

        ClienteDetallesResponse clienteDetallesResponse = ClienteDetallesResponse.builder()
                .prefijo(cliente.getPrefijo())
                .telefono(cliente.getTelefono())
                .genero(cliente.getGenero())
                .pais(cliente.getPais())
                .build();

        return ResponseEntity.ok(clienteDetallesResponse);
    }

    @PutMapping("/actualizar/telefono/")
    public ResponseEntity<?> updateService(
            @RequestPart("correo") String correo,
            @RequestPart("prefijo") String prefijo,
            @RequestPart("telefono") String telefono
    ) {

        ClienteTelefonoRequest clienteTelefonoReq = ClienteTelefonoRequest.builder()
                .correo(correo)
                .prefijo(prefijo)
                .telefono(telefono)
                .build();
        clienteService.actualizarClienteTelefono(clienteTelefonoReq);

        return ResponseEntity.ok("Servicio actualizado satisfactoriamente");
    }

    @PutMapping(value = "/account/password")
    public ResponseEntity<?> changePasswordClient(@RequestBody ClientePassRequest request) {
        System.out.println(request);
        try {
            boolean success;
            authService.cambiarContraseniaCliente(request);
            success=true;
            if (success) {
                return ResponseEntity.ok().body("Contraseña cambiada con éxito");
            } else {
                return ResponseEntity.status(400).body("Error al cambiar la contraseña");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}

package com.rentevent.controller;

import com.rentevent.dto.response.UsuarioResponse;
import com.rentevent.model.usuario.Usuario;
import com.rentevent.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @GetMapping(value = "/{user}")
    public ResponseEntity<UsuarioResponse> obtenerCliente(@PathVariable String user) {
        Usuario usuario = usuarioService.buscarUsuarioCorreo(user).orElseThrow();

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .correo(usuario.getCorreo())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .build();

        return ResponseEntity.ok(usuarioResponse);
    }
}

package com.rentevent.service;

import com.rentevent.model.usuario.Usuario;
import com.rentevent.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    public Optional<Usuario> buscarUsuarioCorreo(String correo){
        return usuarioRepository.findByCorreo(correo);
    }

}

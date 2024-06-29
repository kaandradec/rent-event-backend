package com.rentevent.service;

import com.rentevent.dto.request.CorreoRequest;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorreoService {
    @Autowired
    IClienteRepository clienteRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;

    public Boolean verificarCorreo(CorreoRequest request){
        return (clienteRepository.findByCorreo(request.getCorreo()).isPresent()
        || usuarioRepository.findByCorreo(request.getCorreo()).isPresent());
    }
}

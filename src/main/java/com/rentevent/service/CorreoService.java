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

    /**
     * Verifica la existencia de un correo electrónico en los repositorios de clientes y usuarios.
     * Este método busca el correo proporcionado en los repositorios de clientes y usuarios.
     * Si el correo existe en alguno de los dos repositorios, devuelve verdadero, indicando que el correo ya está en uso.
     * De lo contrario, devuelve falso, indicando que el correo no está registrado y puede ser utilizado.
     *
     * @param request La solicitud que contiene el correo electrónico a verificar.
     * @return Boolean Verdadero si el correo ya está en uso, falso si está disponible.
     */
    public Boolean verificarCorreo(CorreoRequest request) {
        return (clienteRepository.findByCorreo(request.getCorreo()).isPresent()
                || usuarioRepository.findByCorreo(request.getCorreo()).isPresent());
    }
}

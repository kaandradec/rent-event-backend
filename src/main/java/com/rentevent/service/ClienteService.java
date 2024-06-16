package com.rentevent.service;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.dto.response.ClienteResponse;
import com.rentevent.repository.IClienteRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final IClienteRepository clienteRepository;

    public Optional<Cliente> obtenerClientePorUsername(String username) {
        return clienteRepository.findByCorreo(username);
    }
}

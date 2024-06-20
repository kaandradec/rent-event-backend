package com.rentevent.service;

import com.rentevent.dto.request.ClienteTelefonoRequest;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.repository.IClienteRepository;
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

    public void actualizarClienteTelefono(ClienteTelefonoRequest clienteTelefonoRequest) {
        Cliente temp = this.clienteRepository.findByCorreo(
                clienteTelefonoRequest.getCorreo())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        temp.setTelefono(clienteTelefonoRequest.getTelefono());
        temp.setPrefijo(clienteTelefonoRequest.getPrefijo());
        clienteRepository.save(temp);
    }
}

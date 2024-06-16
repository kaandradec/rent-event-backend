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
    private final EntityManager entityManager;
    private final IClienteRepository clienteRepository;

//    public ClienteResponse getUserByUsername(String username) {
//        System.out.println(username);
//
//        return new ClienteResponse(this.entityManager
//                .createQuery("SELECT v FROM Cliente v WHERE v.username=:username", Cliente.class)
//                .setParameter("username", username)
//                .getResultList().get(0));
//    }

    public Optional<Cliente> obtenerClientePorUsername(String username) {
        return clienteRepository.findByUsername(username);
    }
}

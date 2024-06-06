package com.rentevent.repository;

import com.rentevent.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Integer>{
    Optional<Cliente> findByUsername(String username);
}

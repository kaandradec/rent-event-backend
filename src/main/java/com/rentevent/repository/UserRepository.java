package com.rentevent.repository;

import com.rentevent.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {
    // JpaRepository contiene metodos para realizar operaciones CRUD basicas como save, findById, delete, etc.
    Optional<Usuario> findByUsername(String username);
}

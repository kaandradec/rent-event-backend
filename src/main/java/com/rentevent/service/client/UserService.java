package com.rentevent.service.client;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.cliente.UserDTO;
import com.rentevent.model.usuario.Usuario;
import com.rentevent.repository.IUsuarioRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUsuarioRepository userRepository;
    private final EntityManager entityManager;

    public UserDTO getUserByUsername(String username) {
        System.out.println(username);

        return new UserDTO(this.entityManager
                .createQuery("SELECT v FROM Cliente v WHERE v.username=:username", Cliente.class)
                .setParameter("username", username)
                .getResultList().get(0));
    }
}

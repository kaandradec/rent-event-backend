package com.rentevent.model.cliente;

import com.rentevent.model.usuario.Usuario;

public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;

    public UserDTO(Cliente user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.email = user.getCorreo();
        this.avatar = user.getCorreo();
    }

    // Getters y setters
}


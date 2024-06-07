package com.rentevent.auth;

import com.rentevent.jwt.JwtService;
import com.rentevent.model.Role;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.usuario.Usuario;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepository userRepository;
    private final IClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        Usuario usuario = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return AuthResponse.builder()
                .token(token)
                .role(usuario.getRole().name())
                .build();
    }

    public AuthResponse loginCliente(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails client = clienteRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(client);

        Cliente cliente = clienteRepository.findByUsername(request.getUsername()).orElseThrow();
        return AuthResponse.builder()
                .token(token)
                .role(cliente.getRole().name())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.lastname)
                .fechaIncormporacion(LocalDate.now())
                .role(Role.USUARIO)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }


    public AuthResponse registerCliente(RegisterRequest request) {
        Cliente cliente = Cliente.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.lastname)
                .role(Role.CLIENTE)
                .build();

        clienteRepository.save(cliente);

        return AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();

    }

}

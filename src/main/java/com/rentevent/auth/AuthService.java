package com.rentevent.auth;

import com.rentevent.dto.request.LoginRequest;
import com.rentevent.dto.request.RegisterRequest;
import com.rentevent.dto.response.AuthResponse;
import com.rentevent.jwt.JwtService;
import com.rentevent.model.enums.Rol;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasenia()));
        UserDetails user = userRepository.findByCorreo(request.getCorreo()).orElseThrow();
        String token = jwtService.getToken(user);

        Usuario usuario = userRepository.findByCorreo(request.getCorreo()).orElseThrow();
        return AuthResponse.builder()
                .token(token)
                .rol(usuario.getRol().name())
                .correo(usuario.getUsername())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .build();
    }

    public AuthResponse loginCliente(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasenia()));
        UserDetails client = clienteRepository.findByCorreo(request.getCorreo()).orElseThrow();
        String token = jwtService.getToken(client);

        Cliente cliente = clienteRepository.findByCorreo(request.getCorreo()).orElseThrow();
        return AuthResponse.builder()
                .token(token)
                .rol(cliente.getRol().name())
                .correo(cliente.getCorreo())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .correo(request.getCorreo())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .fechaIncormporacion(LocalDate.now())
                .rol(Rol.USUARIO)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }


    public AuthResponse registerCliente(RegisterRequest request) {

        System.out.println(request.toString());
        Cliente cliente = Cliente.builder()
                .correo(request.getCorreo())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .prefijo(request.getPrefijo())
                .telefono(request.getTelefono())
                .genero(request.getGenero())
                .rol(Rol.CLIENTE)
                .build();

        clienteRepository.save(cliente);

        return AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();

    }

}

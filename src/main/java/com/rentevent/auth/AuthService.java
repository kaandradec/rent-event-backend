package com.rentevent.auth;

import com.rentevent.dto.request.*;
import com.rentevent.dto.response.AuthResponse;
import com.rentevent.exception.NotFoundException;
import com.rentevent.jwt.JwtService;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.enums.Rol;
import com.rentevent.model.usuario.Usuario;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IUsuarioRepository;
import com.rentevent.service.ClienteService;
import com.rentevent.service.CorreoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final IUsuarioRepository userRepository;
    private final IClienteRepository clienteRepository;
    private final CorreoService correoService;
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

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        System.out.println(request.toString());
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


    @Transactional
    public AuthResponse registerCliente(RegisterRequest request) {

        System.out.println(request.toString());
        if (correoService.verificarCorreo(CorreoRequest.builder().correo(request.getCorreo()).build())) {
            return null;
        }

        Cliente cliente = Cliente.builder()
                .correo(request.getCorreo())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .prefijo(request.getPrefijo())
                .telefono(request.getTelefono())
                .pais(request.getPais())
                .region(request.getCiudad())
                .genero(request.getGenero())
                .rol(Rol.CLIENTE)
                .build();

        clienteRepository.save(cliente);

        return AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();

    }

    @Transactional
    public boolean cambiarContraseniaCliente(ClientePassRequest request) {
        //si la contrasenia da error termina, en lugar de retornar true
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContraseniaActual()));
        Cliente cliente = clienteRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() -> new SecurityException("Cliente no encontrado"));
        cliente.setContrasenia(passwordEncoder.encode(request.getContraseniaNueva()));
        clienteRepository.save(cliente);
        return true;
    }

    @Transactional
    public boolean cambiarContraseniaPorPreguntaCliente(ClientePassPreguntaRequest request) {
        Cliente cliente = clienteRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() -> new SecurityException("Cliente no encontrado"));
        cliente.setContrasenia(passwordEncoder.encode(request.getContraseniaNueva()));

        clienteRepository.save(cliente);
        return true;
    }
}

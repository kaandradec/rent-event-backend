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

    /**
     * Autentica a un usuario y genera una respuesta de autenticación que incluye un token JWT.
     * Este método autentica al usuario utilizando su correo y contraseña. Si la autenticación es exitosa,
     * se genera un token JWT para el usuario. Luego, se construye y devuelve una respuesta de autenticación
     * que incluye el token JWT y detalles del usuario como su rol, correo, nombre y apellido.
     *
     * @param request La solicitud de inicio de sesión que contiene el correo y la contraseña del usuario.
     * @return Una respuesta de autenticación que incluye el token JWT y detalles del usuario.
     * @throws NotFoundException si el usuario no se encuentra en el repositorio.
     */
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
                .build();
    }

    /**
     * Autentica a un cliente y genera una respuesta de autenticación que incluye un token JWT.
     * Este método autentica al cliente utilizando su correo y contraseña. Si la autenticación es exitosa,
     * se genera un token JWT para el cliente. Luego, se construye y devuelve una respuesta de autenticación
     * que incluye el token JWT y detalles del cliente como su rol, correo, nombre y apellido.
     *
     * @param request La solicitud de inicio de sesión que contiene el correo y la contraseña del cliente.
     * @return Una respuesta de autenticación que incluye el token JWT y detalles del cliente.
     * @throws NotFoundException si el cliente no se encuentra en el repositorio.
     */
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

    /**
     * Registra un nuevo usuario en el sistema y genera una respuesta de autenticación que incluye un token JWT.
     * Este método crea un nuevo usuario con los datos proporcionados en la solicitud de registro. La contraseña del usuario
     * se codifica antes de guardarla en la base de datos. Una vez creado el usuario, se genera un token JWT para él.
     * Finalmente, se construye y devuelve una respuesta de autenticación que incluye el token JWT.
     *
     * @param request La solicitud de registro que contiene los datos del nuevo usuario.
     * @return Una respuesta de autenticación que incluye el token JWT del nuevo usuario.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        System.out.println(request.toString());
        Usuario user = Usuario.builder()
                .correo(request.getCorreo())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .nombre(request.getNombre())
                .fechaIncormporacion(LocalDate.now())
                .rol(Rol.USUARIO)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    /**
     * Registra un nuevo cliente en el sistema y genera una respuesta de autenticación que incluye un token JWT.
     * Este método verifica primero si el correo electrónico proporcionado ya está en uso. Si el correo está disponible,
     * crea un nuevo cliente con los datos proporcionados en la solicitud de registro. La contraseña del cliente
     * se codifica antes de guardarla en la base de datos. Una vez creado el cliente, se genera un token JWT para él.
     * Finalmente, se construye y devuelve una respuesta de autenticación que incluye el token JWT.
     *
     * @param request La solicitud de registro que contiene los datos del nuevo cliente.
     * @return Una respuesta de autenticación que incluye el token JWT del nuevo cliente, o null si el correo ya está en uso.
     */
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

    /**
     * Cambia la contraseña de un cliente después de autenticar su contraseña actual.
     * Este método intenta autenticar al cliente con su correo electrónico y contraseña actual.
     * Si la autenticación es exitosa y el cliente es encontrado, se procede a cambiar su contraseña
     * por la nueva proporcionada en la solicitud. La nueva contraseña se codifica antes de guardarla.
     *
     * @param request Contiene el correo electrónico del cliente, su contraseña actual y la nueva contraseña.
     * @return true si la contraseña se cambió exitosamente.
     * @throws SecurityException si el cliente no se encuentra o si la autenticación falla.
     */
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

    /**
     * Cambia la contraseña de un cliente basándose en una pregunta de seguridad.
     * Este método busca al cliente por su correo electrónico y, si lo encuentra, actualiza su contraseña
     * con la nueva proporcionada en la solicitud. La nueva contraseña se codifica antes de ser guardada.
     * Este enfoque proporciona una capa adicional de seguridad permitiendo el cambio de contraseña
     * solo si el cliente puede responder correctamente a una pregunta de seguridad (implícita en el proceso de validación previo a la llamada de este método).
     *
     * @param request Contiene el correo electrónico del cliente y la nueva contraseña a establecer.
     * @return true si la contraseña se cambió exitosamente.
     * @throws SecurityException si el cliente no se encuentra con el correo proporcionado.
     */
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

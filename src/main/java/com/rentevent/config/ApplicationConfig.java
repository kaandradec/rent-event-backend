package com.rentevent.config;

import com.rentevent.model.usuario.Usuario;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IUsuarioRepository userRepository;
    private final IClienteRepository clienteRepository;

    /**
     * Proporciona el AuthenticationManager de Spring Security.
     * Este método configura y retorna el AuthenticationManager, que es un componente central en Spring Security
     * para manejar la autenticación. Se utiliza para autenticar las credenciales de un usuario durante el proceso de login.
     * Al ser declarado como un bean, Spring gestiona su ciclo de vida y permite su inyección en otros componentes.
     *
     * @param config Configuración de autenticación de Spring Security.
     * @return El AuthenticationManager configurado.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean // Para que el contenedor de Spring pueda administrar las instancias
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura y proporciona un proveedor de autenticación para Spring Security.
     * Este método crea una instancia de DaoAuthenticationProvider, que es un proveedor de autenticación basado en DAO.
     * Se configura con un servicio de detalles de usuario personalizado y un codificador de contraseñas para la autenticación.
     * El servicio de detalles de usuario carga los datos del usuario por nombre de usuario (correo electrónico en este caso),
     * y el codificador de contraseñas se utiliza para verificar las contraseñas durante el proceso de autenticación.
     *
     * @return Un proveedor de autenticación configurado para usar en la autenticación de Spring Security.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Proporciona un codificador de contraseñas para la aplicación.
     * Este método configura y retorna una instancia de BCryptPasswordEncoder, que es una implementación de PasswordEncoder
     * que utiliza el algoritmo BCrypt. Se utiliza para codificar las contraseñas de manera segura antes de almacenarlas
     * en la base de datos y para verificar las contraseñas de los usuarios durante el proceso de autenticación.
     *
     * @return Un codificador de contraseñas basado en BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
//    @Primary
//    public UserDetailsService usuarioDetailsService() {
//        return (username) -> clienteRepository.findByCorreo(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

    /**
     * Proporciona un servicio de detalles de usuario para Spring Security.
     * Este método define un UserDetailsService personalizado que intenta cargar un usuario primero desde el repositorio de usuarios.
     * Si no se encuentra un usuario, intenta cargar un cliente desde el repositorio de clientes.
     * Si ninguna búsqueda tiene éxito, lanza una excepción indicando que el usuario no se encontró.
     *
     * @return Un UserDetailsService que carga los detalles del usuario o cliente por correo electrónico.
     */
    @Bean
    public UserDetailsService userDetailService() {

        return (username) -> {
            Usuario usuario = userRepository.findByCorreo(username).orElse(null);
            if (usuario != null) {
                return usuario;
            }
            return clienteRepository.findByCorreo(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }
}
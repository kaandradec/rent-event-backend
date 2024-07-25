package com.rentevent.config;

import com.rentevent.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * Este método define la configuración de seguridad utilizando HttpSecurity para especificar qué
     * peticiones requieren autenticación y cuáles están permitidas sin autenticación. Se deshabilita CSRF
     * ya que se utilizará JWT para la autenticación. Se configura el manejo de sesiones para ser sin estado,
     * adecuado para APIs REST donde no se requiere mantener estado de sesión en el servidor.
     * Se especifican los patrones de URL y las autoridades requeridas para acceder a ellos.
     * Finalmente, se añade un filtro personalizado para JWT antes del filtro de autenticación por nombre de usuario y contraseña.
     *
     * @param http Configuración de seguridad HTTP.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Autenticación por valor csrf deshabilitado (usaremos jwt)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                //.requestMatchers(HttpMethod.GET).permitAll() // Permitir todos los métodos GET
                                //.requestMatchers(HttpMethod.OPTIONS).permitAll() // Permitir todos los métodos OPTIONS
                                .requestMatchers("/servicios/**").permitAll()
                                .requestMatchers("/clientes/**").permitAll()
                                .requestMatchers("/account/**").permitAll()
                                .requestMatchers("/correo/**").permitAll()
                                .requestMatchers("/pagos/**").hasAnyAuthority("CLIENTE")
                                .requestMatchers("/facturas/**").permitAll()
                                .requestMatchers("/eventos/**").hasAnyAuthority("CLIENTE", "USUARIO")
                                .requestMatchers("/security/**").permitAll()
                                .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/generos/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }

    /**
     * Configura las políticas de CORS (Cross-Origin Resource Sharing) para la aplicación.
     * Este método permite especificar qué orígenes pueden acceder a los recursos de la aplicación,
     * qué métodos HTTP están permitidos y si se permiten credenciales en las solicitudes.
     * La configuración se aplica a todas las rutas (/**).
     *
     * @return Un configurador de CORS que se registra como un bean en el contexto de Spring.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")  // Permite todos los orígenes
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}

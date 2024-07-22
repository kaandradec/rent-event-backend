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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

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

}

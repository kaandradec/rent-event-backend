package com.rentevent.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Este método se sobrescribe del OncePerRequestFilter de Spring Security para interceptar todas las solicitudes HTTP.
     * Su propósito es extraer el token JWT de la cabecera de autorización, validar dicho token, y establecer la autenticación
     * en el contexto de seguridad de Spring si el token es válido. Esto permite proteger los endpoints de la aplicación
     * basándose en la validez de los tokens JWT proporcionados en las solicitudes.
     * <p>
     * El flujo de operaciones es el siguiente:
     * 1. Extrae el token JWT de la cabecera de autorización de la solicitud HTTP.
     * 2. Si el token es nulo, simplemente deja pasar la solicitud al siguiente filtro sin hacer nada (puede ser una solicitud que no requiere autenticación).
     * 3. Si el token no es nulo, intenta extraer el nombre de usuario del token.
     * 4. Si se obtiene un nombre de usuario y no existe una autenticación previa en el contexto de seguridad, carga los detalles del usuario.
     * 5. Verifica si el token es válido para el usuario cargado. Si es así, crea un objeto de autenticación (UsernamePasswordAuthenticationToken) con los detalles del usuario y las autoridades.
     * 6. Establece el objeto de autenticación en el contexto de seguridad de Spring, permitiendo que el usuario esté autenticado durante el procesamiento de la solicitud.
     * 7. Finalmente, deja pasar la solicitud al siguiente filtro en la cadena.
     * <p>
     * Este proceso asegura que solo los usuarios con tokens JWT válidos puedan acceder a los recursos protegidos de la aplicación.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token == null) {
            filterChain.doFilter(request, response);
            System.out.println("Token is null");
            return;
        } else {
            System.out.println("TOKEN:" + token);
        }

        username = jwtService.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT de la cabecera de autorización de una solicitud HTTP.
     * Este método busca la cabecera 'Authorization' en la solicitud entrante, verifica si el valor
     * comienza con 'Bearer ' (indicando un token JWT), y en caso afirmativo, extrae y retorna el token,
     * omitiendo el prefijo 'Bearer '.
     *
     * @param request La solicitud HTTP de la cual extraer el token JWT.
     * @return El token JWT sin el prefijo 'Bearer ', o null si la cabecera no está presente o no tiene el formato esperado.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }


}

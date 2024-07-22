package com.rentevent.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924"; // clave secreta

    /**
     * Genera un token JWT para un usuario sin reclamaciones adicionales.
     * Este método es un conveniente atajo que llama al método principal de generación de tokens con un mapa de reclamaciones vacío.
     * Es útil cuando se necesita generar un token para un usuario sin necesidad de especificar reclamaciones adicionales.
     *
     * @param user Los detalles del usuario para quien se está generando el token.
     * @return Un token JWT compacto y firmado para el usuario.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Genera un token JWT para un usuario con reclamaciones adicionales.
     * Este método crea un token JWT para un usuario específico, incluyendo cualquier reclamación adicional proporcionada.
     * El token incluye el nombre de usuario como sujeto, la fecha de emisión actual, y una fecha de expiración 30 minutos después.
     * Se firma el token utilizando el algoritmo HS256 y una clave secreta.
     *
     * @param extraClaims Un mapa de objetos que representa las reclamaciones adicionales a incluir en el token.
     * @param user        Los detalles del usuario para quien se está generando el token.
     * @return Un token JWT compacto y firmado para el usuario.
     */
    public String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Genera una clave de firma para ser utilizada en la creación y verificación de tokens JWT.
     * Este método decodifica la clave secreta codificada en Base64 almacenada en la constante SECRET_KEY,
     * y luego utiliza esta clave decodificada para generar una Key específica para el algoritmo HMAC SHA-256.
     * Esta clave se utiliza para firmar los tokens JWT generados y verificar su firma durante la autenticación.
     *
     * @return Una Key generada a partir de la clave secreta decodificada, adecuada para el algoritmo HS256.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el nombre de usuario del sujeto del token JWT.
     * Este método utiliza la función getClaim para extraer el sujeto (subject) del token, que se espera sea el nombre de usuario.
     * Utiliza una referencia a método para pasar la función específica de extracción de Claims::getSubject a getClaim.
     *
     * @param token El token JWT del cual se desea obtener el nombre de usuario.
     * @return El nombre de usuario extraído del sujeto del token, o null si el token no es válido o el sujeto no está presente.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Valida si un token JWT es válido para un usuario específico.
     * Este método verifica si el nombre de usuario extraído del token coincide con el nombre de usuario de los detalles del usuario proporcionados,
     * y además, comprueba si el token no ha expirado.
     *
     * @param token       El token JWT a validar.
     * @param userDetails Los detalles del usuario contra los cuales validar el token.
     * @return true si el token es válido para el usuario y no ha expirado, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extrae todas las reclamaciones de un token JWT.
     * Este método utiliza el token proporcionado para extraer todas las reclamaciones contenidas en él.
     * Se construye un parser JWT especificando la clave de firma utilizada para verificar la firma del token.
     * Luego, se parsea el token para extraer su cuerpo, que contiene las reclamaciones.
     *
     * @param token El token JWT del cual se desean extraer las reclamaciones.
     * @return Las reclamaciones contenidas en el cuerpo del token JWT.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrae una reclamación específica de un token JWT utilizando una función de resolución de reclamaciones.
     * Este método genérico permite extraer cualquier tipo de información (reclamación) contenida en el cuerpo del token JWT,
     * aplicando una función de resolución proporcionada como argumento. Esto ofrece flexibilidad para extraer diferentes tipos
     * de reclamaciones sin necesidad de métodos específicos para cada una.
     *
     * @param token          El token JWT del cual se desea extraer la reclamación.
     * @param claimsResolver Una función que toma las reclamaciones del token y devuelve un valor específico de tipo T.
     * @param <T>            El tipo de dato de la reclamación que se desea extraer.
     * @return El valor de la reclamación extraída, del tipo especificado por T, o null si el token no es válido o la reclamación no está presente.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene la fecha de expiración de un token JWT.
     * Este método utiliza la función getClaim para extraer la fecha de expiración (expiration) del token.
     * La fecha de expiración es utilizada para determinar si el token ha expirado o no.
     *
     * @param token El token JWT del cual se desea obtener la fecha de expiración.
     * @return La fecha de expiración del token, o null si el token no es válido o la fecha de expiración no está presente.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica si un token JWT ha expirado.
     * Este método compara la fecha de expiración del token con la fecha y hora actual.
     * Si la fecha de expiración es anterior a la fecha y hora actual, el token se considera expirado.
     *
     * @param token El token JWT del cual se desea verificar la expiración.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
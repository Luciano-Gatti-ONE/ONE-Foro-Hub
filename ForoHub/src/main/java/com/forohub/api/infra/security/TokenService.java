package com.forohub.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forohub.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Servicio para la generación y validación de tokens JWT.
 * 
 * Utiliza un secreto configurado en las propiedades para firmar y validar los tokens.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    /**
     * Genera un token JWT para un usuario dado.
     * 
     * El token incluye el correo electrónico como subject, el id del usuario como claim,
     * un emisor fijo y una fecha de expiración de 2 horas.
     * 
     * @param usuario Usuario para quien se genera el token.
     * @return Token JWT firmado.
     */
    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro hub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al crear el token JWT", exception);
        }
    }

    /**
     * Obtiene el subject (correo electrónico) de un token JWT válido.
     * 
     * Valida la firma y el emisor del token. Si el token es inválido o nulo,
     * lanza RuntimeException.
     * 
     * @param token Token JWT a validar y del cual extraer el subject.
     * @return El correo electrónico contenido en el token.
     */
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token es nulo");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validar firma
            verifier = JWT.require(algorithm)
                    .withIssuer("foro hub")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            System.out.println("Error validando token JWT: " + exception.toString());
        }
        if (verifier == null || verifier.getSubject() == null) {
            throw new RuntimeException("Token JWT inválido o sin subject");
        }
        return verifier.getSubject();
    }

    /**
     * Genera la fecha de expiración para un token.
     * 
     * Actualmente es la fecha y hora actual más 2 horas con zona horaria -03:00.
     * 
     * @return Instant que representa la fecha de expiración.
     */
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
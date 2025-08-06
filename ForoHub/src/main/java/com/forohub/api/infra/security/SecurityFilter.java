package com.forohub.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.forohub.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de seguridad que se ejecuta una vez por cada petición HTTP.
 * 
 * Este filtro intercepta las solicitudes para extraer y validar un token JWT enviado en el header "Authorization".
 * Si el token es válido, se obtiene el usuario correspondiente y se establece la autenticación en el contexto de seguridad de Spring,
 * permitiendo que el usuario acceda a los recursos protegidos sin necesidad de iniciar sesión nuevamente.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Filtra cada petición HTTP para autenticar al usuario basado en el token JWT.
     * 
     * @param request la petición HTTP entrante
     * @param response la respuesta HTTP
     * @param filterChain la cadena de filtros que continúa el procesamiento
     * @throws ServletException si ocurre un error en el procesamiento del filtro
     * @throws IOException si ocurre un error de entrada/salida durante el filtrado
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtener el valor del header Authorization
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            // Extraer el token removiendo el prefijo "Bearer "
            var token = authHeader.replace("Bearer ", "");

            // Obtener el usuario (correo) del token
            var correo = tokenService.getSubject(token);

            if (correo != null) {
                // Buscar el usuario en la base de datos
                var usuario = usuarioRepository.findByCorreoElectronico(correo);

                // Crear el objeto de autenticación con los roles del usuario
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
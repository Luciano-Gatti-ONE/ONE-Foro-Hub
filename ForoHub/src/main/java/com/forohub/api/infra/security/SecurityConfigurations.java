package com.forohub.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación usando Spring Security.
 * 
 * Define las reglas de autorización, deshabilita CSRF, configura el manejo de sesiones sin estado
 * y agrega el filtro de seguridad personalizado para la autenticación JWT.
 * 
 * @autor Luciano Emmanuel Gatti Flekenstein
 */

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    /**
     * Configura la cadena de filtros de seguridad HTTP para la aplicación.
     *
     * Esta configuración realiza las siguientes acciones:
     * - Deshabilita la protección CSRF, ya que se usa un API REST sin sesiones tradicionales.
     * - Establece que la gestión de sesión es sin estado (stateless), es decir, no se mantienen sesiones en servidor.
     * - Permite acceso público (sin autenticación) a:
     *   • El endpoint POST "/auth/login" para iniciar sesión.
     *   • El endpoint POST "/auth/registrar" para registrar usuarios.
     *   • Los endpoints relacionados con la documentación OpenAPI y Swagger UI.
     * - Requiere autenticación para cualquier otra solicitud.
     * - Añade un filtro personalizado de seguridad (securityFilter) antes del filtro de autenticación estándar de Spring (UsernamePasswordAuthenticationFilter),
     *   para manejar validación de tokens JWT.
     *
     * @param httpSecurity Objeto de configuración de seguridad HTTP provisto por Spring Security.
     * @return La cadena de filtros de seguridad configurada y lista para ser usada en la aplicación.
     * @throws Exception En caso de errores durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Provee el manejador de autenticaciones de Spring Security.
     *
     * @param authenticationConfiguration configuración de autenticación.
     * @return el {@link AuthenticationManager}.
     * @throws Exception si ocurre un error al obtener el manejador.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean encargado de codificar contraseñas utilizando BCrypt.
     *
     * @return una instancia de {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
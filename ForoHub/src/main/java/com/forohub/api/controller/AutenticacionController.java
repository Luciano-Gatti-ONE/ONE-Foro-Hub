package com.forohub.api.controller;

import jakarta.validation.Valid;
import com.forohub.api.domain.usuarios.DatosAutenticacionUsuario;
import com.forohub.api.domain.usuarios.Usuario;
import com.forohub.api.infra.security.DatosJWTToken;
import com.forohub.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import com.forohub.api.domain.usuarios.UsuarioService;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import com.forohub.api.domain.usuarios.DatosRegistrarUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuario;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;


/**
 * Controlador REST que gestiona la autenticación y el registro de usuarios en la API.
 *
 * Expone dos endpoints principales:
 * - /auth/login: permite autenticar un usuario y devolver un token JWT.
 * - /auth/registrar: permite registrar un nuevo usuario y devolver su información.
 *
 * Utiliza servicios de Spring Security y servicios personalizados para manejar
 * la lógica de seguridad y persistencia de usuarios.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Autenticar usuario",
        description = "Autentica un usuario y devuelve un token JWT válido.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token JWT generado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosJWTToken.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de autenticación del usuario", 
            required = true,
            content = @Content(schema = @Schema(implementation = DatosAutenticacionUsuario.class))
        )
        @RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(
            datosAutenticacionUsuario.login(),
            datosAutenticacionUsuario.clave()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Registra un nuevo usuario y devuelve sus datos.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosRespuestaUsuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para el registro")
        }
    )
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para registrar un nuevo usuario",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosRegistrarUsuario.class))
        )
        @RequestBody @Valid DatosRegistrarUsuario datosRegistrarUsuario,
        UriComponentsBuilder uriComponentsBuilder) {
        
        var detalleCreacion = usuarioService.registrarUsuario(datosRegistrarUsuario);

        URI url = uriComponentsBuilder
                    .path("/usuarios/{id}")
                    .buildAndExpand(detalleCreacion.id())
                    .toUri();

        return ResponseEntity.created(url).body(detalleCreacion);
    }
}
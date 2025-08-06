package com.forohub.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;

import com.forohub.api.domain.usuarios.UsuarioService;
import com.forohub.api.domain.usuarios.DatosRegistrarUsuario;
import com.forohub.api.domain.usuarios.DatosMostrarUsuario;
import com.forohub.api.domain.usuarios.DatosActualizarUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuarioActualizado;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los usuarios.
 *
 * Proporciona endpoints para listar todos los usuarios, obtener un usuario por ID,
 * actualizar datos de un usuario y realizar un borrado lógico (desactivación).
 * 
 * Todos los endpoints requieren autenticación mediante token Bearer.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key") // Requiere token JWT para acceder
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Listar todos los usuarios",
        description = "Devuelve una lista de todos los usuarios activos.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosMostrarUsuario.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<DatosMostrarUsuario>> listadoUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Devuelve los datos de un usuario a partir de su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosMostrarUsuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarUsuario> usuarioPorId(
        @Parameter(description = "ID del usuario a consultar") @PathVariable Long id) {

        return ResponseEntity.ok(usuarioService.mostrarUsuarioPorId(id));
    }

    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosRespuestaUsuarioActualizado.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuarioActualizado> actualizarUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para actualizar el usuario",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosActualizarUsuario.class))
        )
        @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {

        var respuesta = usuarioService.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(
        summary = "Eliminar (desactivar) un usuario",
        description = "Realiza un borrado lógico de un usuario, marcándolo como inactivo.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Usuario desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(
        @Parameter(description = "ID del usuario a desactivar") @PathVariable Long id) {

        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
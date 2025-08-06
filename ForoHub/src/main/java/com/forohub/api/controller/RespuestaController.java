package com.forohub.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import com.forohub.api.domain.respuesta.RespuestaService;
import com.forohub.api.domain.respuesta.DatosDetalleRespuesta;
import com.forohub.api.domain.respuesta.DatosCreacionRespuesta;
import com.forohub.api.domain.respuesta.DatosMostrarRespuesta;
import com.forohub.api.domain.respuesta.DatosDetalleRespuestaActualizada;
import com.forohub.api.domain.respuesta.DatosActualizarRespuesta;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las respuestas en el foro.
 *
 * Permite crear, listar, buscar, actualizar, marcar como solución y eliminar respuestas.
 * Todos los endpoints requieren autenticación mediante token Bearer.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@RestController
@RequestMapping("/respuesta")
@SecurityRequirement(name = "bearer-key") // Requiere token JWT para acceder
@Tag(name = "Respuestas", description = "Operaciones relacionadas con respuestas del foro")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Operation(
        summary = "Crear una nueva respuesta",
        description = "Crea una respuesta en un tópico y devuelve los datos de la misma.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Respuesta creada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosDetalleRespuesta.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> crearRespuesta(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para crear una respuesta",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosCreacionRespuesta.class))
        )
        @RequestBody @Valid DatosCreacionRespuesta datosCreacionRespuesta,
        UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = respuestaService.crearRespuesta(datosCreacionRespuesta);
        URI url = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }

    @Operation(
        summary = "Listar respuestas por tópico",
        description = "Lista todas las respuestas activas asociadas a un tópico.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Respuestas listadas exitosamente",
                content = @Content(mediaType = "application/json"))
        }
    )
    @GetMapping
    public ResponseEntity<List<DatosMostrarRespuesta>> listadoRespuestasPorTopico(
        @Parameter(description = "ID del tópico para filtrar las respuestas") @RequestParam Long id) {
        return ResponseEntity.ok(respuestaService.listarRespuestasPorTopico(id));
    }

    @Operation(
        summary = "Buscar respuesta por ID",
        description = "Devuelve los datos de una respuesta dada su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Respuesta encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosMostrarRespuesta.class))),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarRespuesta> respuestaPorId(
        @Parameter(description = "ID de la respuesta") @PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.respuestaPorId(id));
    }

    @Operation(
        summary = "Actualizar respuesta",
        description = "Actualiza los datos de una respuesta existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Respuesta actualizada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosDetalleRespuestaActualizada.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PutMapping
    @Transactional
    public ResponseEntity<DatosDetalleRespuestaActualizada> actualizarRespuesta(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para actualizar una respuesta",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosActualizarRespuesta.class))
        )
        @RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        var respuesta = respuestaService.actualizarRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(
        summary = "Marcar respuesta como solución",
        description = "Marca una respuesta como solución del tópico correspondiente.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Respuesta marcada como solución"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada", content = @Content)
        }
    )
    @PatchMapping("/solucion/{id}")
    @Transactional
    public ResponseEntity<Void> marcarRespuestaComoSolucion(
        @Parameter(description = "ID de la respuesta a marcar como solución") @PathVariable Long id) {
        respuestaService.marcarComoSolucion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Eliminar respuesta",
        description = "Realiza un borrado lógico (desactivación) de una respuesta.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Respuesta desactivada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarRespuesta(
        @Parameter(description = "ID de la respuesta a eliminar") @PathVariable Long id) {
        respuestaService.desactivarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
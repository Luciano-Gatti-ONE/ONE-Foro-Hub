package com.forohub.api.controller;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosRespuestaTopico;
import com.forohub.api.domain.topico.DatosRespuestaTopicoActualizado;
import com.forohub.api.domain.topico.DatosMostrarTopico;
import com.forohub.api.domain.topico.DatosActualizarTopico;
import com.forohub.api.domain.topico.DatosBusquedaTopico;
import com.forohub.api.domain.topico.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controlador REST que gestiona las operaciones sobre los tópicos en el foro.
 *
 * Proporciona endpoints para crear, listar (con paginación y ordenamiento),
 * filtrar por fecha o por curso y año, obtener un tópico por ID,
 * actualizar y eliminar (borrado lógico) tópicos.
 * 
 * Todos los endpoints requieren autenticación mediante token Bearer.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key") // Requiere token JWT para acceder
@Tag(name = "Tópicos", description = "Operaciones relacionadas con los tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Operation(
        summary = "Crear un nuevo tópico",
        description = "Crea un nuevo tópico en el foro y devuelve los datos del mismo.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Tópico creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosRespuestaTopico.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para crear un tópico",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosCreacionTopico.class))
        )
        @RequestBody @Valid DatosCreacionTopico datosCreacionTopico,
        UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = topicoService.crearTopico(datosCreacionTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }

    @Operation(
        summary = "Listar tópicos con paginación",
        description = "Devuelve una página de tópicos con soporte para ordenamiento.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tópicos listados exitosamente")
        }
    )
    @GetMapping
    public ResponseEntity<Page<DatosMostrarTopico>> listadoTopicos(
        @Parameter(description = "Número de página (default: 0)") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Cantidad de elementos por página (default: 10)") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Campo por el que se ordena (default: nombre)") @RequestParam(defaultValue = "nombre") String sort) {

        return ResponseEntity.ok(topicoService.listarTodosLosTopicos(page, size, sort));
    }

    @Operation(
        summary = "Listar tópicos por fecha",
        description = "Devuelve una lista de tópicos ordenados por fecha.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tópicos listados por fecha exitosamente")
        }
    )
    @GetMapping("/por-fecha")
    public ResponseEntity<List<DatosRespuestaTopico>> listadoTopicosPorFecha() {
        return ResponseEntity.ok(topicoService.topicosPorFecha());
    }

    @Operation(
        summary = "Listar tópicos por curso y año",
        description = "Devuelve una lista de tópicos filtrados por curso y año.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tópicos filtrados exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @GetMapping("/por-curso-y-año")
    public ResponseEntity<List<DatosRespuestaTopico>> listadoTopicosPorCursoYAño(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Filtros de búsqueda por curso y año",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosBusquedaTopico.class))
        )
        @RequestBody @Valid DatosBusquedaTopico datosBusquedaTopico) {

        return ResponseEntity.ok(topicoService.topicosPorCursoyAño(datosBusquedaTopico));
    }

    @Operation(
        summary = "Buscar tópico por ID",
        description = "Devuelve los datos de un tópico a partir de su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tópico encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosMostrarTopico.class))),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarTopico> topicoPorId(
        @Parameter(description = "ID del tópico a consultar") @PathVariable Long id) {
        return ResponseEntity.ok(topicoService.mostrarTopicoPorId(id));
    }

    @Operation(
        summary = "Actualizar un tópico",
        description = "Actualiza los datos de un tópico existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tópico actualizado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosRespuestaTopicoActualizado.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopicoActualizado> actualizarTopico(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para actualizar un tópico",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosActualizarTopico.class))
        )
        @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

        var respuesta = topicoService.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(
        summary = "Eliminar un tópico",
        description = "Realiza un borrado lógico de un tópico (lo desactiva).",
        responses = {
            @ApiResponse(responseCode = "204", description = "Tópico eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(
        @Parameter(description = "ID del tópico a eliminar") @PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
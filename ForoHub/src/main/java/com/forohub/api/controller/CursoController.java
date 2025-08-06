package com.forohub.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.util.UriComponentsBuilder;
import com.forohub.api.domain.curso.Curso;
import com.forohub.api.domain.curso.DatosCreacionCurso;
import com.forohub.api.domain.curso.DatosRespuestaCurso;
import com.forohub.api.domain.curso.CursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * Controlador REST que maneja las operaciones relacionadas con los cursos.
 *
 * Expone endpoints para:
 * - Crear un nuevo curso.
 * - Listar todos los cursos con paginación y ordenamiento.
 * - Buscar un curso por su ID.
 *
 * Todos los endpoints requieren autenticación mediante token Bearer.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key") // Requiere autenticación con Bearer Token
@Tag(name = "Cursos", description = "Gestión de cursos: creación, listado y búsqueda")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Operation(
        summary = "Crear un nuevo curso",
        description = "Crea un nuevo curso y devuelve sus datos.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Curso creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosRespuestaCurso.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para creación")
        }
    )
    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> crearCurso(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para crear un nuevo curso",
            required = true,
            content = @Content(schema = @Schema(implementation = DatosCreacionCurso.class))
        )
        @RequestBody @Valid DatosCreacionCurso datosCreacionCurso,
        UriComponentsBuilder uriComponentsBuilder) {

        var curso = cursoService.crearCurso(datosCreacionCurso);

        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.id()).toUri();

        return ResponseEntity.created(url).body(curso);
    }

    @Operation(
        summary = "Listar cursos",
        description = "Lista todos los cursos con paginación y ordenamiento.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de cursos",
                content = @Content(mediaType = "application/json"))
        }
    )
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listadoCursos(
        @Parameter(description = "Número de página (default 0)") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Cantidad de elementos por página (default 10)") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Campo para ordenar resultados (default 'nombre')") @RequestParam(defaultValue = "nombre") String sort) {

        var cursos = cursoService.listarTodosLosCursos(page, size, sort);
        return ResponseEntity.ok(cursos);
    }

    @Operation(
        summary = "Buscar curso por ID",
        description = "Obtiene los datos de un curso dado su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosRespuestaCurso.class))),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> buscarCursoPorId(
        @Parameter(description = "ID del curso a buscar") @PathVariable Long id) {

        var datosCurso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(datosCurso);
    }
}
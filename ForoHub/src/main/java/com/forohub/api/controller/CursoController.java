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

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> crearCurso(@RequestBody @Valid DatosCreacionCurso datosCreacionCurso,
                                                           UriComponentsBuilder uriComponentsBuilder) {
        
        var curso = cursoService.crearCurso(datosCreacionCurso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.id()).toUri();
        return ResponseEntity.created(url).body(curso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listadoCursos( 
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "nombre") String sort) {
        var cursos = cursoService.listarTodosLosCursos(page, size, sort);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> buscarCursoPorId(@PathVariable Long id) {
        var datosCurso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(datosCurso);
    }
}

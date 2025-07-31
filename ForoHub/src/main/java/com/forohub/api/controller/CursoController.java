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
import com.forohub.api.domain.curso.CursoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
    
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> crearCurso(@RequestBody @Valid DatosCreacionCurso datosCreacionCurso,
                                                           UriComponentsBuilder uriComponentsBuilder) {
        var curso = cursoRepository.save(new Curso(datosCreacionCurso));
        var datosRespuestaCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().getDescripcion());

        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listadoCursos( 
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "nombre") String sort) {
        Pageable paginacion = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(cursoRepository.findAll(paginacion).map(DatosRespuestaCurso::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> buscarCursoPorId(@PathVariable Long id) {
        var curso = cursoRepository.getReferenceById(id);
        var datosCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().getDescripcion());
        return ResponseEntity.ok(datosCurso);
    }
}

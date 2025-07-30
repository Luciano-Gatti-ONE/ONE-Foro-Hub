/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package com.forohub.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> crearCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        var curso = cursoRepository.save(new Curso(datosRegistroCurso));
        var datosRespuestaCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().getDescripcion());

        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listadoCursos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(cursoRepository.findAll(paginacion).map(DatosRespuestaCurso::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> buscarCursoPorId(@PathVariable Long id) {
        var curso = cursoRepository.getReferenceById(id);
        var datosCurso = new datosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().getDescripcion());
        return ResponseEntity.ok(datosCurso);
    }
}

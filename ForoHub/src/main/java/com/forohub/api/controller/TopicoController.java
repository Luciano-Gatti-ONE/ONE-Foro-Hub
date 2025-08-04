package com.forohub.api.controller;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosRespuestaTopico;
import com.forohub.api.domain.topico.DatosMostrarTopico;
import com.forohub.api.domain.topico.DatosActualizarTopico;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import java.util.List;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosCreacionTopico datosCreacionTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = topicoService.crearTopico(datosCreacionTopico);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosMostrarTopico>> listadoTopicos( 
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sort) {
        return ResponseEntity.ok(topicoService.listarTodosLosTopicos(page, size, sort));
    }
    
    @GetMapping("/por-fecha")
    public ResponseEntity<List<DatosMostrarTopico>> listadoTopicosPorFecha() {
        return ResponseEntity.ok(topicoService.topicosPorFecha());
    }
    
    @GetMapping("/por-curso-y-año")
    public ResponseEntity<List<DatosMostrarTopico>> listadoTopicosPorCursoYAño (@RequestBody @Valid DatosBusquedaTopico datosBusquedaTopico) {
        return ResponseEntity.ok(topicoService.topicosPorCursoyAño(datosBusquedaTopico));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarTopico> topicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.mostrarTopicoPorId(id));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        var respuesta = topicoService.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(respuesta);
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}


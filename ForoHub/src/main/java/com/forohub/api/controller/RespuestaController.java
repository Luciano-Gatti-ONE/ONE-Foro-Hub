package com.forohub.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author usuario
 */

@RestController
@RequestMapping("/respuesta")
public class RespuestaController {
    
    @Autowired
    private RespuestaService respuestaService;
    
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosCreacionRespuesta datosCreacionRespuesta,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = respuestaService.crearRespuesta(datosCreacionRespuesta);

        URI url = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }
    
    @GetMapping
    public ResponseEntity<List<DatosMostrarRespuestas>> listadoRespuestasPorTopico(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.listarRespuestasPorTopico());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarRespuestas> respuestaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.mostrarRespuestaPorId(id));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        var respuesta = respuestaService.actualizarRespuesta(datosActualizarRespuesta);
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

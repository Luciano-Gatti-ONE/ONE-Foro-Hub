package com.forohub.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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

/**
 *
 * @author usuario
 */

@RestController
@RequestMapping("/respuesta")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
    
    @Autowired
    private RespuestaService respuestaService;
    
    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> crearRespuesta(@RequestBody @Valid DatosCreacionRespuesta datosCreacionRespuesta,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = respuestaService.crearRespuesta(datosCreacionRespuesta);

        URI url = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }
    
    @GetMapping
    public ResponseEntity<List<DatosMostrarRespuesta>> listadoRespuestasPorTopico(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.listarRespuestasPorTopico(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarRespuesta> respuestaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.respuestaPorId(id));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity<DatosDetalleRespuestaActualizada> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        var respuesta = respuestaService.actualizarRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(respuesta);
    }
    
    @PatchMapping("/solucion/{id}")
    @Transactional
    public ResponseEntity marcarRespuestaComoSolucion(@PathVariable Long id) {
        respuestaService.marcarComoSolucion(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id) {
        respuestaService.desactivarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}

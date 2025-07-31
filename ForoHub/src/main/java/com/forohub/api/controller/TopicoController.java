package com.forohub.api.controller;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosRespuestaTopico;
import com.forohub.api.domain.topico.CreacionTopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private CreacionTopicoService creacion;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosCreacionTopico datosCreacionTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = creacion.crearTopico(datosCreacionTopico);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }
}


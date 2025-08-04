package com.forohub.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datosRegistrarUsuario,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        var detalleCreacion = usuarioService.registrarUsuario(datosRegistrarUsuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }
    
    @GetMapping
    public ResponseEntity<List<DatosMostrarUsuario>> listadoUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarUsuario> usuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.mostrarUsuarioPorId(id));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        var respuesta = usuarioService.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(respuesta);
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

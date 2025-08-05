package com.forohub.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;

import com.forohub.api.domain.usuarios.UsuarioService;
import com.forohub.api.domain.usuarios.DatosRegistrarUsuario;
import com.forohub.api.domain.usuarios.DatosMostrarUsuario;
import com.forohub.api.domain.usuarios.DatosActualizarUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuarioActualizado;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
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
    public ResponseEntity<DatosRespuestaUsuarioActualizado> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
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

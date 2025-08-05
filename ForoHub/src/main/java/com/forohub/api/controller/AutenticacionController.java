package com.forohub.api.controller;

import jakarta.validation.Valid;
import com.forohub.api.domain.usuarios.DatosAutenticacionUsuario;
import com.forohub.api.domain.usuarios.Usuario;
import com.forohub.api.infra.security.DatosJWTToken;
import com.forohub.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import com.forohub.api.domain.usuarios.UsuarioService;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import com.forohub.api.domain.usuarios.DatosRegistrarUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuario;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
    //    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    //    System.out.println(encoder.encode("12345678"));
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
    
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datosRegistrarUsuario,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        var detalleCreacion = usuarioService.registrarUsuario(datosRegistrarUsuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(detalleCreacion.id()).toUri();
        return ResponseEntity.created(url).body(detalleCreacion);
    }
}

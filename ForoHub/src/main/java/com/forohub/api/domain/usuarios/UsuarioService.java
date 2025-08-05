package com.forohub.api.domain.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.forohub.api.domain.usuarios.DatosRegistrarUsuario;
import com.forohub.api.domain.usuarios.DatosActualizarUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuario;
import com.forohub.api.domain.usuarios.DatosRespuestaUsuarioActualizado;
import com.forohub.api.domain.usuarios.DatosMostrarUsuario;

import com.forohub.api.domain.usuarios.Usuario;
import com.forohub.api.domain.usuarios.UsuarioRepository;

import com.forohub.api.domain.ValidacionException;

/**
 *
 * @author usuario
 */

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public DatosRespuestaUsuario registrarUsuario(DatosRegistrarUsuario datos){
        String contrasenaHasheada = passwordEncoder.encode(datos.contrasena());
        var usuario = new Usuario(
            datos.nombre(),
            datos.correoElectronico(),
            contrasenaHasheada
        );
        usuarioRepository.save(usuario);
        return new DatosRespuestaUsuario(usuario);
    }
    
    public List<DatosMostrarUsuario> listarTodosLosUsuarios(){
        var usuario = usuarioRepository.findByActivoTrue();
        var respuesta = usuario.stream()
                .map(DatosMostrarUsuario::new)
                .toList();
        return respuesta;
    }
    
    public DatosMostrarUsuario mostrarUsuarioPorId(Long id){
        if(!usuarioRepository.findByActivoTrueAndId(id).isPresent()){
            throw new ValidacionException("El usuario no existe o no se encuentra activo");
        }
        var usuario = usuarioRepository.getReferenceById(id);
        var respuesta = new DatosMostrarUsuario(usuario);
        return respuesta;
    }
    
    public DatosRespuestaUsuarioActualizado actualizarUsuario(DatosActualizarUsuario datos){
        var usuario = usuarioRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));
        var avisos = actualizarDatos(datos, usuario);
        usuarioRepository.save(usuario); 
        return new DatosRespuestaUsuarioActualizado(usuario, avisos);
    }
    
    public void desactivarUsuario(Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();
    }
    
    public Map<String, String> actualizarDatos(DatosActualizarUsuario datos, Usuario usuario){
        Map<String, String> avisos = new HashMap<>();

        if (datos.nombre() != null && !datos.nombre().isBlank()) {
            usuario.setNombre(datos.nombre());
        } else {
            avisos.put("Nombre", "El nombre no fue actualizado porque está vacío o en blanco.");
        }

        if (datos.correoElectronico() != null && !datos.correoElectronico().isBlank()) {
            usuario.setCorreoElectronico(datos.correoElectronico());
        } else {
            avisos.put("Correo Electronico", "El correo no fue actualizado porque está vacío o en blanco.");
        }
        
        if (datos.contrasena() != null && !datos.contrasena().isBlank()) {
            if (datos.contrasena().length() >= 8
                && datos.contrasena().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")) {
                usuario.setContrasena(passwordEncoder.encode(datos.contrasena()));
            } else {
                avisos.put("Contraseña", "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial.");
            }
        } else {
            avisos.put("Contraseña", "La contraseña no fue actualizada porque está vacía o en blanco.");
        }
        
        return avisos;
    }
}

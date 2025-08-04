package com.forohub.api.domain.usuarios;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));
        if(!usuarioRepository.findByActivoTrueAndId(id).isPresent()){
            throw new ValidacionException("El usuario no se encuentra activo"));
        }
        
        var respuesta = new DatosMostrarUsuario(usuario);
        return respuesta;
    }
    
    public DatosRespuestaUsuarioActualizado actualizarUsuario(DatosActualizarUsuario datos){
        var usuario = usuarioRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado");
        var avisos = actualizarDatos(datos, usuario);
        usuarioRepository.save(usuario); 
        return new DatosRespuestaUsuarioActualizado(usuario, avisos);
    }
    
    public void desactivarUsuario(Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();
    }
    
    public void actualizarDatos(DatosActualizarUsuario datos, Usuario usuario){
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
            usuario.setContrasena(datos.contrasena());
        } else {
            avisos.put("Contraseña", "La contraseña no fue actualizada porque está vacía o en blanco.");
        }
    }
}

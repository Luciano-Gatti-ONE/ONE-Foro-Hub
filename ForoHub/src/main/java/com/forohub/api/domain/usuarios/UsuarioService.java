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
 * Servicio encargado de la gestión de usuarios en el sistema.
 * Proporciona operaciones para registrar, listar, consultar, actualizar y desactivar usuarios.
 * También maneja la validación y codificación de contraseñas.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema.
     * Codifica la contraseña antes de persistir el usuario.
     * 
     * @param datos datos necesarios para registrar el usuario
     * @return datos del usuario registrado
     */
    public DatosRespuestaUsuario registrarUsuario(DatosRegistrarUsuario datos) {
        String contrasenaHasheada = passwordEncoder.encode(datos.contrasena());
        var usuario = new Usuario(
            datos.nombre(),
            datos.correoElectronico(),
            contrasenaHasheada
        );
        usuarioRepository.save(usuario);
        return new DatosRespuestaUsuario(usuario);
    }

    /**
     * Lista todos los usuarios activos del sistema.
     * 
     * @return lista de usuarios activos
     */
    public List<DatosMostrarUsuario> listarTodosLosUsuarios() {
        var usuario = usuarioRepository.findByActivoTrue();
        var respuesta = usuario.stream()
                .map(DatosMostrarUsuario::new)
                .toList();
        return respuesta;
    }

    /**
     * Muestra los datos de un usuario activo según su ID.
     * 
     * @param id identificador del usuario
     * @return datos del usuario solicitado
     * @throws ValidacionException si el usuario no existe o está inactivo
     */
    public DatosMostrarUsuario mostrarUsuarioPorId(Long id) {
        if (!usuarioRepository.findByActivoTrueAndId(id).isPresent()) {
            throw new ValidacionException("El usuario no existe o no se encuentra activo");
        }
        var usuario = usuarioRepository.getReferenceById(id);
        var respuesta = new DatosMostrarUsuario(usuario);
        return respuesta;
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Realiza validaciones y devuelve un mapa de avisos si algunos campos no fueron modificados.
     * 
     * @param datos datos nuevos del usuario
     * @return objeto con los datos actualizados y posibles avisos
     * @throws ValidacionException si el usuario no se encuentra
     */
    public DatosRespuestaUsuarioActualizado actualizarUsuario(DatosActualizarUsuario datos) {
        var usuario = usuarioRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));
        var avisos = actualizarDatos(datos, usuario);
        usuarioRepository.save(usuario); 
        return new DatosRespuestaUsuarioActualizado(usuario, avisos);
    }

    /**
     * Realiza una eliminación lógica del usuario, marcándolo como inactivo.
     * 
     * @param id identificador del usuario
     */
    public void desactivarUsuario(Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();
    }

    /**
     * Método auxiliar que actualiza los campos del usuario validando su contenido.
     * Retorna un mapa con los avisos si algún campo no fue actualizado.
     * 
     * @param datos datos a actualizar
     * @param usuario entidad a modificar
     * @return mapa con avisos sobre los campos no modificados
     */
    public Map<String, String> actualizarDatos(DatosActualizarUsuario datos, Usuario usuario) {
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
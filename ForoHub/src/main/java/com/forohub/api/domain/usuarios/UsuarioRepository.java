package com.forohub.api.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;
/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona métodos para operaciones CRUD y consultas específicas para usuarios.
 * 
 * Métodos adicionales incluyen:
 * - Buscar un usuario activo por su correo electrónico, retornando un UserDetails para integración con Spring Security.
 * - Listar todos los usuarios activos.
 * - Buscar un usuario activo por su ID.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario activo por su correo electrónico.
     * 
     * @param correo el correo electrónico del usuario
     * @return el UserDetails del usuario, para uso en autenticación
     */
    UserDetails findByCorreoElectronico(String correo);

    /**
     * Lista todos los usuarios que están activos.
     * 
     * @return lista de usuarios activos
     */
    List<Usuario> findByActivoTrue();

    /**
     * Busca un usuario activo por su ID.
     * 
     * @param id el ID del usuario
     * @return un Optional con el usuario activo si existe
     */
    Optional<Usuario> findByActivoTrueAndId(Long id);
}
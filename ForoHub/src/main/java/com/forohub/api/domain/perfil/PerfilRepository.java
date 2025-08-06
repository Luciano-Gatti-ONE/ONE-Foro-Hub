package com.forohub.api.domain.perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forohub.api.domain.perfil.Perfil;

/**
 * Repositorio JPA para la entidad Perfil.
 * 
 * Proporciona operaciones CRUD y de consulta para los perfiles almacenados en la base de datos.
 * Extiende JpaRepository para aprovechar la implementación estándar de Spring Data JPA.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}

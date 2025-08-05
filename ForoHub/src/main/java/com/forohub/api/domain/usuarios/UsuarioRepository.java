package com.forohub.api.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByCorreoElectronico(String correo);
    List<Usuario> findByActivoTrue();
    Optional<Usuario> findByActivoTrueAndId(Long id);
}

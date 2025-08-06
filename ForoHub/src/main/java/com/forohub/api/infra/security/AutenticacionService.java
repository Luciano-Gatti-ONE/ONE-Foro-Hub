package com.forohub.api.infra.security;

import com.forohub.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de cargar los detalles de usuario para el proceso de autenticación.
 * Implementa la interfaz UserDetailsService de Spring Security.
 * 
 * Este servicio se utiliza automáticamente por Spring Security para validar usuarios en el login.
 * Busca un usuario por su correo electrónico.
 */

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga un usuario a partir del correo electrónico (usado como nombre de usuario).
     * 
     * @param correo El correo electrónico del usuario.
     * @return Un objeto UserDetails con la información del usuario.
     * @throws UsernameNotFoundException si no se encuentra un usuario con ese correo.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronico(correo);
    }
}

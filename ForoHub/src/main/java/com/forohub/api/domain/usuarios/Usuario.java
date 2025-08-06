package com.forohub.api.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import com.forohub.api.domain.perfil.Perfil;
import com.forohub.api.domain.topico.Topico;
import com.forohub.api.domain.usuarios.DatosRegistrarUsuario;

/**
 * Representa un usuario del sistema.
 * Implementa la interfaz UserDetails de Spring Security para integrarse con el sistema de autenticación y autorización.
 * 
 * Un usuario tiene un nombre, correo electrónico, contraseña y estado de activación.
 * Además, puede tener múltiples perfiles asociados y puede ser autor de múltiples tópicos.
 * 
 * Métodos de UserDetails proporcionan información sobre el estado de la cuenta para seguridad.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correoElectronico;

    private String contrasena;
    
    private Boolean activo;
        
    public Usuario(String nombre, String correoElectronico, String contrasena) {
        this.activo = true;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Perfil> perfiles;
    
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Topico> topicos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Aquí podrías agregar lógica real si implementas expiración de cuentas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Aquí podrías agregar lógica real si implementas bloqueo de cuentas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Aquí podrías agregar lógica real si implementas expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return activo != null && activo;  // Retorna si el usuario está activo o no
    }
    
    /**
     * Desactiva al usuario, estableciendo su estado como inactivo.
     */
    public void desactivarUsuario() {
        this.activo = false;
    }
}
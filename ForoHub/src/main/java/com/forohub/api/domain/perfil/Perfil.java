package com.forohub.api.domain.perfil;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.forohub.api.domain.usuarios.Usuario;

/**
 * Representa un perfil de usuario dentro del sistema.
 * 
 * Contiene un identificador único, un nombre descriptivo del perfil
 * y la asociación al usuario al que pertenece.
 * 
 * Esta entidad permite definir diferentes perfiles o roles que un usuario puede tener.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Entity(name = "Perfil")
@Table(name = "perfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    
    @ManyToOne
    private Usuario usuario;
}
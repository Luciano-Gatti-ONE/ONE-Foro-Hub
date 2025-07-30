package com.forohub.api.domain.perfil;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


/**
* Representa un perfil dentro del sistema.
* Contiene un identificador único y un nombre descriptivo del perfil.
* 
* @author Luciano Emmanuel Gatti Flekenstein
*/

@Entity(name = "Perfil")
@Table(name = "perfiles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}
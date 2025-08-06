package com.forohub.api.domain.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.forohub.api.domain.ValidacionException;
import com.forohub.api.domain.curso.DatosRespuestaCurso;
import com.forohub.api.domain.curso.DatosCreacionCurso;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los cursos.
 * 
 * Proporciona métodos para crear un curso, listar todos los cursos con paginación
 * y ordenamiento, y buscar un curso por su ID.
 * 
 * Utiliza el repositorio CursoRepository para acceder a la base de datos.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    /**
     * Crea un nuevo curso a partir de los datos recibidos, lo guarda en la base de datos
     * y devuelve un DTO con la información del curso creado.
     * 
     * @param datos DTO con los datos necesarios para crear un curso
     * @return DatosRespuestaCurso con la información del curso creado
     */
    public DatosRespuestaCurso crearCurso(DatosCreacionCurso datos){
        var curso = new Curso(datos);
        cursoRepository.save(curso);
        return new DatosRespuestaCurso(curso);
    }
    
    /**
     * Lista todos los cursos con soporte para paginación y ordenamiento.
     * 
     * @param page número de página (0-based)
     * @param size cantidad de elementos por página
     * @param sort campo por el cual se ordenan los cursos
     * @return página con DTOs de los cursos encontrados
     */
    public Page<DatosRespuestaCurso> listarTodosLosCursos(int page, int size, String sort){
        Pageable paginacion = PageRequest.of(page, size, Sort.by(sort));
        var cursos = cursoRepository.findAll(paginacion)
                .map(DatosRespuestaCurso::new);
        return cursos;
    }
    
    /**
     * Busca un curso por su ID.
     * 
     * @param id identificador único del curso
     * @return DTO con la información del curso encontrado
     * @throws ValidacionException si no existe el curso con el ID dado
     */
    public DatosRespuestaCurso buscarCursoPorId(Long id){
        var cursoEntity = cursoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un curso con el id informado"));
        var curso = new DatosRespuestaCurso(cursoEntity);
        return curso;
    }
}
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
 *
 * @author usuario
 */
@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    public DatosRespuestaCurso crearCurso(DatosCreacionCurso datos){
        var curso = new Curso(datos);
        cursoRepository.save(curso);
        return new DatosRespuestaCurso(curso);
    }
    
    public Page<DatosRespuestaCurso> listarTodosLosCursos(int page, int size, String sort){
        Pageable paginacion = PageRequest.of(page, size, Sort.by(sort));
        var cursos = cursoRepository.findAll(paginacion)
                .map(DatosRespuestaCurso::new);
        return cursos;
    }
    
    public DatosRespuestaCurso buscarCursoPorId(Long id){
        var cursoEntity = cursoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un curso con el id informado"));
        var curso = new DatosRespuestaCurso(cursoEntity);
        return curso;
    }
}

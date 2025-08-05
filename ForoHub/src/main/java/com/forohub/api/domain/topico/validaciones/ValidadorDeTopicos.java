package com.forohub.api.domain.topico.validaciones;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosActualizarTopico;

/**
 *
 * @author lucia
 */
public interface ValidadorDeTopicos {
    public void validarCreacion(DatosCreacionTopico datos);
    public void validarActualizacion(DatosActualizarTopico datos);
}

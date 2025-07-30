/**
 * Author:  Luciano Emmanuel Gatti Flekenstein
 * Created: 30 jul 2025
 */

CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_de_creacion DATETIME,
    status VARCHAR(255),
    autor_id BIGINT,
    curso_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES cursos(id),
    INDEX idx_autor_id (autor_id),
    INDEX idx_curso_id (curso_id)
);
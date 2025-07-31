/**
 * Author:  Luciano Emmanuel Gatti Flekenstein
 * Created: 30 jul 2025
 */

CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    topico_id BIGINT,
    fecha_creacion DATETIME,
    autor_id BIGINT,
    solucion BOOLEAN,
    PRIMARY KEY (id),
    CONSTRAINT fk_respuesta_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_respuesta_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    INDEX idx_topico_id (topico_id),
    INDEX idx_autor_id_resp (autor_id)
);
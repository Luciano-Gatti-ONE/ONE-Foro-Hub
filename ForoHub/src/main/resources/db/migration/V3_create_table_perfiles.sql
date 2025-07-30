/**
 * Author:  Luciano Emmanuel Gatti Flekenstein
 * Created: 30 jul 2025
 */

CREATE TABLE perfiles (
    id BIGINT AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    INDEX idx_usuario_id (usuario_id)
);
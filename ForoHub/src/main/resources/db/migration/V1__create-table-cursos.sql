/**
 * Author:  Luciano Emmanuel Gatti Flekenstein
 * Created: 30 jul 2025
 */

CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
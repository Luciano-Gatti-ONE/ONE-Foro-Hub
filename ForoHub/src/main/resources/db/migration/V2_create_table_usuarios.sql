/**
 * Author:  Luciano Emmanuel Gatti Flekenstein
 * Created: 30 jul 2025
 */

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_correo (correo_electronico)
);
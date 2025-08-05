/**
 * Author:  usuario
 * Created: 1 ago 2025
 */

alter table usuarios add activo tinyint;
update usuarios set activo = 1
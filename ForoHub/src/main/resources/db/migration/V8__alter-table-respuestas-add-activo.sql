/**
 * Author:  usuario
 * Created: 1 ago 2025
 */

alter table respuestas add activo tinyint;
update respuestas set activo = 1
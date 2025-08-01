/**
 * Author:  usuario
 * Created: 1 ago 2025
 */

alter table topicos add activo tinyint;
update topicos set activo = 1
# Collections API

Microservicio realizado con Spring Boot

En el enunciado de la prueba se pedia implementar la siguiente llamada:

> GET /v1/collections/all

Parámetros:

> filter: Opcional. Ejemplo: id 1

Dado que existe un solo parámetro pero se pide filtrar por 4 campos, el parametro filter contiene el campo a filtrar y el valor por el que se filtra separado por un espacio.

Esta llamada resulta poco útil, por lo tanto se ha implementado una segunda llamada intentando seguir las buenas prácticas:

> GET /v1/collections

Parámetros:
> id: Opcional Ejemplo: 1
> title: Opcional Ejemplo: título
> description: Opcional Ejemplo: descripcion
> cover_photo_id: Opcional Ejemplo: id foto

---

#### Docker

Para poder hacer uso de la API hay que ejecutar:

> docker pull skeet15/collections-api:latest
> docker run -p 8080:8080 skeet15/collections-api:latest

--- 

#### Documentación

Es posible acceder al Swagger de la API accediendo a la siguiente URL en local:

> http://localhost:8080/swagger-ui.html



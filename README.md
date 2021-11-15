# Collections API

Microservicio realizado con Spring Boot

En el enunciado de la prueba se pedia implementar la siguiente llamada:

> GET /v1/collection/all

Parámetros:

> filter: Opcional. Ejemplo: 1

#### Autenticacion

Dado que se pide obtener un token de acceso previamente, es necesario realizar el flujo OAuth para realizar las llamadas autenticadas

Para ello, es necesario hacer login accediendo al siguiente path:

> http://localhost:8080/login

Una vez hecho login, la API devolverá un JSON informando que el usuario se encuentra autenticado

A partir de ese momento las llamadas a Unsplash quedarán autenticadas con el token de acceso

#### Docker

Para poder hacer uso de la API hay que ejecutar:

> docker pull skeet15/collections-api:latest

> docker run -p 8080:8080 skeet15/collections-api:latest

--- 

#### Documentación

Es posible acceder al Swagger de la API accediendo a la siguiente URL en local:

> http://localhost:8080/swagger-ui.html



![Java](https://img.shields.io/badge/JAVA-11-blue)
![Spring](https://img.shields.io/badge/SPRING--BOOT-2.6.3-blue)
![Gradle](https://img.shields.io/badge/MAVEN-3-blue)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_api-superheroes&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fjspitz_api-superheroes)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_api-superheroes&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=fjspitz_api-superheroes)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_api-superheroes&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fjspitz_api-superheroes)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_api-superheroes&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=fjspitz_api-superheroes)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_api-superheroes&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=fjspitz_api-superheroes)

# Star Wars Heroes! API

Este proyecto de prueba esta basado en la API de héroes sobre el universo de **Star Wars** para tomar algunos datos de muestra y popular la base. Más información [aquí](https://swapi.dev/).

Los héroes incluídos en la base de datos de prueba del proyecto son:

- Luke Skywalker
- C-3PO
- Anakin Skywalker
- Han Solo
- Chewbacca
- Obi-Wan Kenobi
- Leia Organa
- Darth Vader
- R2-D2
- Yoda
- Palpatine

## Ejecución en ambiente local

### Crear la imagen

Desde el directorio raíz de la aplicación ejecutar:
```
DOCKER_BUILDKIT=1 docker build -t api-superheroes .
```

Se utiliza **Buildkit** para optimizar los tiempos de generación de las imágenes. 

### Ejecutar la aplicación

```
docker run --rm -d -p 8080:8080 --name api-superheroes api-superheroes:latest
```

### Seguridad

Para este ejemplo se implementa mediante **Spring Security** seguridad básica para el API mediante un harcodeo. 

Claramente no es un mecanismo para apis productivas, pero lo uso acá para simplificar el ejemplo. De hecho, se especifica la opción **noop** para no encriptar la password para facilitar las pruebas.

```
user: user
pwd: password
```
Ejemplo:

```
curl -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" http://localhost:8080/api/superheroes | jq
```

Para acceder a la documentación del API mediante Open API 3 se puede acceder a [éste](http://localhost:8080/swagger-ui.html) link.

## Tecnologías

- Docker 20.10.7
- Springboot
- Spring Data JPA
- H2 database
- Flyway
- Lombok
- Spring Cache
- Spring Security
- jq


---
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=fjspitz_api-superheroes)

# Proyecto AAD_25_26 -- Migración a Spring Boot, JdbcTemplate y PL/pgSQL

Este proyecto corresponde a la actividad práctica en la que se migró una
aplicación previa (Actividad 2_1) hacia una arquitectura basada en
**Spring Boot 3.x**, utilizando:

- **JdbcTemplate** para el acceso a datos
- **SimpleJdbcCall** para funciones almacenadas
- **Transacciones declarativas (@Transactional)**
- **Carga manual de scripts SQL** (schema, procedures y datos)
- PostgreSQL en **Docker** como servidor

------------------------------------------------------------------------

## 1. Objetivo del ejercicio

El objetivo principal es adaptar el proyecto previo para aprovechar el
ecosistema de Spring Boot:

1. Reemplazar el uso manual de conexiones JDBC por **JdbcTemplate**.
2. Sustituir las llamadas a procedimientos usando **CallableStatement**
   por **SimpleJdbcCall**.
3. Incorporar **transacciones declarativas** para garantizar la
   integridad de las operaciones.
4. Gestionar automáticamente el ciclo de vida del **DataSource** y del
   pool de conexiones (**HikariCP**).
5. Ejecutar los scripts SQL completos desde código, evitando que Spring
   los fragmente.

------------------------------------------------------------------------

## 2. Arquitectura aplicada

### 2.1. Capa de Modelo (model/)

Clases que representan las entidades del dominio: - `Student` -
`Module` - `Enrollment`

### 2.2. Capa de Acceso a Datos (repository/)

Implementada con: - `JdbcTemplate` - RowMapper mediante expresiones
lambda

Además, se añadió un mecanismo personalizado para ejecutar scripts SQL
completos:

``` java
@PostConstruct
private void initScripts() {
    runScript("sql/01_schema.sql");
    runScript("sql/02_procedures.sql");
    runScript("sql/03_sample_data.sql");
}
```

### 2.3. Capa de Servicio (application/)

Encapsula reglas de negocio como: - matricular alumnos - gestionar
entidades - aplicar transacciones con `@Transactional`

------------------------------------------------------------------------

## 3. Configuración del DataSource

El archivo `application.yml` se ajustó para que Spring **no ejecute los
scripts automáticamente**, evitando conflictos:

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/aad_db
    username: user
    password: pass
    driver-class-name: org.postgresql.Driver

  sql:
    init:
      mode: never
```

Esto permite que los scripts se ejecuten solo mediante la clase
personalizada del repositorio.

------------------------------------------------------------------------

## 4. Scripts SQL utilizados

### `01_schema.sql`

Define la estructura de tablas: - alumno - modulo - matricula

### `02_procedures.sql`

Incluye funciones PL/pgSQL como:

``` sql
CREATE OR REPLACE FUNCTION count_enrollments(student_id INT)
RETURNS INT
LANGUAGE plpgsql
AS $$
DECLARE
    total INT;
BEGIN
    SELECT COUNT(*) INTO total 
    FROM matricula
    WHERE id_alumno = student_id;

    RETURN total;
END;
$$;
```

### `03_sample_data.sql`

Inserta datos de prueba para poder iniciar la aplicación con contenido.

------------------------------------------------------------------------

## 5. Ejecución del proyecto

1. Levantar contenedor Docker de PostgreSQL:\
   `docker compose up -d`

2. Ejecutar Spring Boot desde el IDE o via Maven: `mvn spring-boot:run`

3. Verificar que:

    - Se cargan las tablas
    - Se ejecutan funciones correctamente
    - Se aplican transacciones
    - La API responde sin errores

------------------------------------------------------------------------

## 6. Resultado final

Se obtuvo una aplicación: - modular - transaccional - desacoplada - con
scripts SQL ejecutados correctamente - con repositorios limpios usando
JdbcTemplate - lista para extender con endpoints REST o UI

------------------------------------------------------------------------

## 7. Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring JDBC**
- **HikariCP**
- **PostgreSQL**
- **Docker**
- **PL/pgSQL**

------------------------------------------------------------------------

## 8. Autora

Proyecto desarrollado para la asignatura de Acceso a Datos (DAM 2º).

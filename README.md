# Sistema de Gestión de Matrículas (JDBC + PostgreSQL + Docker)

## 1. Descripción general

Este proyecto implementa un **sistema básico de gestión académica** utilizando:

- **Java + Spring Boot**
- **JDBC puro**
- **PostgreSQL**
- **Docker Compose**
- **Transacciones manuales**
- **Funciones almacenadas SQL**

La inicialización de la base de datos se realiza mediante scripts SQL ejecutados automáticamente al iniciar la
aplicación.

---

## 2. Levantar PostgreSQL con Docker

Ejecutar desde la carpeta del proyecto:

```bash
docker-compose up -d
```

Acceder manualmente a PostgreSQL:

```bash
docker exec -it aad_db_container psql -U user -d aad_db
```

---

## 3. Compilación y ejecución del proyecto

### Compilar

```bash
mvn clean install
```

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

---

## 4. Ejecución automática (Paso 8)

El método `run()`:

1. Crea un estudiante
2. Crea un módulo
3. Realiza una matrícula con transacción manual
4. Ejecuta la función almacenada `count_enrollments`
5. Elimina el estudiante creado

---

## 5. Evidencias de ejecución

- Inicialización de la base de datos
- Creación de entidades
- Logs de transacción
- Resultado de `count_enrollments`
- Datos persistidos en PostgreSQL

---

## 6. Conclusión personal

Esta práctica me ha permitido comprender el uso de JDBC puro, transacciones manuales, procedimientos almacenados, Docker
y PostgreSQL, así como el flujo de trabajo de un sistema académico básico.

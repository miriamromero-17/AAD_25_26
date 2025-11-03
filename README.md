# Configuración de PostgreSQL con Docker Compose

## 1. Qué es el conector

El conector es el componente que permite que una aplicación se comunique con la base de datos.  
Por ejemplo:

- En **Python**, se usa `psycopg2`.
- En **Node.js**, se usa `pg`.
- En **Java**, se usa el *driver JDBC* de PostgreSQL.

Su función es establecer la conexión, enviar consultas SQL y recibir los resultados.

---

## 2. Levantar el servicio PostgreSQL

Ejecuta el siguiente comando en la carpeta del proyecto:

```bash
docker-compose up -d
```

Esto descargará la imagen `postgres:latest`, creará el contenedor `aad_db_container` y expondrá el puerto `5433`  
(mapeado al `5432` interno de PostgreSQL).

---

## 3. Variables de entorno

Estas variables configuran la base de datos al iniciar el contenedor:

| Variable            | Valor    | Descripción                                       |
|---------------------|----------|---------------------------------------------------|
| `POSTGRES_DB`       | `aad_db` | Nombre de la base de datos creada automáticamente |
| `POSTGRES_USER`     | `user`   | Usuario principal de la base de datos             |
| `POSTGRES_PASSWORD` | `pass`   | Contraseña del usuario                            |

---

## 4. Probar la conexión

### Opción 1: Desde la terminal del host

```bash
psql -h localhost -p 5433 -U user -d aad_db
```

### Opción 2: Desde el contenedor

```bash
docker exec -it aad_db_container psql -U user -d aad_db
```

Si aparece el prompt `aad_db=#`, la conexión se ha establecido correctamente.

---

## 5. Ampliación de funcionalidad en `PostgresqlDriver.java`

La clase `PostgresqlDriver.java` amplía la funcionalidad del proyecto al permitir la interacción directa entre la
aplicación Java y la base de datos **PostgreSQL**.

Sus principales responsabilidades son:

- Establecer la conexión con la base de datos usando el *driver JDBC* de PostgreSQL.
- Ejecutar sentencias SQL (creación de tablas, inserciones, consultas, etc.).
- Controlar los errores de conexión mediante excepciones (`SQLException`).
- Facilitar el uso de métodos reutilizables para ejecutar consultas desde otras clases.

Ejemplo de conexión:

```java
Connection conn = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5433/aad_db", "user", "pass"
);
```

---

## 6. Resumen del modelo relacional

El modelo relacional del proyecto representa la estructura de la base de datos **`aad_db`**, que almacena información de
alumnos, módulos y matrículas.

### Tablas principales

#### 🧑‍🎓 Tabla `alumno`

| Campo              | Tipo de dato | Restricciones |
|--------------------|--------------|---------------|
| `id`               | SERIAL       | PRIMARY KEY   |
| `nombre`           | VARCHAR(100) | NOT NULL      |
| `apellidos`        | VARCHAR(150) |               |
| `fecha_nacimiento` | DATE         |               |
| `nota_media`       | NUMERIC(4,2) |               |

---

#### 📘 Tabla `modulo`

| Campo    | Tipo de dato | Restricciones |
|----------|--------------|---------------|
| `id`     | SERIAL       | PRIMARY KEY   |
| `nombre` | VARCHAR(100) | NOT NULL      |

---

#### 🧾 Tabla `matricula`

| Campo             | Tipo de dato | Restricciones             |
|-------------------|--------------|---------------------------|
| `id_alumno`       | INT          | FOREIGN KEY → `alumno.id` |
| `id_modulo`       | INT          | FOREIGN KEY → `modulo.id` |
| `fecha_matricula` | DATE         |                           |

> **Clave primaria compuesta:** (`id_alumno`, `id_modulo`)

---

## 7. Diagrama relacional en texto

Representación de las relaciones entre las tablas:

```
ALUMNO (1) ──< (N) MATRICULA (N) >── (1) MODULO
```

**Interpretación:**

- Un **alumno** puede matricularse en varios módulos.
- Un **módulo** puede tener varios alumnos matriculados.
- La relación se gestiona a través de la tabla intermedia **MATRICULA**.

---

## 8. Autoría

Proyecto académico desarrollado para la asignatura de **Acceso a Datos** en el ciclo **Desarrollo de Aplicaciones
Multiplataforma (DAM)**.

Tecnologías utilizadas:

- PostgreSQL
- Docker Compose
- IntelliJ IDEA
- Java (JDBC)

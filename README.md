# Configuración de PostgreSQL con Docker Compose

## 1. Qué es el conector

El conector es el componente que permite que una aplicación se comunique con la base de datos.  
Por ejemplo:

- En **Python**, se usa `psycopg2`.
- En **Node.js**, se usa `pg`.

Su función es establecer la conexión, enviar consultas SQL y recibir los resultados.

---

## 2. Levantar el servicio PostgreSQL

Ejecuta el siguiente comando en la carpeta del proyecto:

```bash
docker-compose up -d
```

Esto descargará la imagen `postgres:latest`, creará el contenedor `aad_db_container` y expondrá el puerto `5433` (
mapeado al `5432` interno de PostgreSQL).

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

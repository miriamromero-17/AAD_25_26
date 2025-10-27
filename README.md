# Gestor de Logs en Java

Este programa permite registrar eventos en un fichero de texto (`app.log`), indicando la fecha y hora de cada acción.  
Por ejemplo, al ejecutar el programa se generan líneas como las siguientes:

```
[2025-10-27 19:44:05] Usuario Ana inició sesión
[2025-10-27 19:44:49] Usuario Pedro inició sesión
[2025-10-27 19:44:56] Usuario Pedro cerró sesión
[2025-10-27 19:49:17] Usuario Ana inició sesión
[2025-10-27 19:49:24] Usuario Ana cerró sesión
```

## Funcionalidad

- Añadir eventos con fecha y hora automática.
- Filtrar los eventos por una fecha concreta (YYYY-MM-DD).
- Guardar los logs en un archivo con codificación UTF-8.
- Mostrar mensajes informativos por consola mediante SLF4J.
- Manejo de excepciones en lectura y escritura de ficheros.

## Requisitos

- Java 17 o superior.
- Librerías necesarias:
    - Lombok
    - SLF4J API
    - Logback Classic

Si se utiliza Maven, las dependencias pueden añadirse al archivo `pom.xml`.  
Si se usa NetBeans o IntelliJ, basta con tener instaladas las librerías Lombok y SLF4J.

## Estructura del proyecto

```
src/
 └── main/
      └── java/
           └── com/example/add/
                ├── App.java
                └── LogManager.java
app.log
README.md
```

## Ejecución del programa

Al ejecutar la clase `App`, aparecerá el siguiente menú:

```
=== MENÚ DE SESIÓN ===
1. Iniciar sesión
2. Cerrar sesión
3. Ver eventos de una fecha
4. Salir
```

Ejemplo de uso:

```
Introduce el nombre del usuario: Ana
Se guardan los eventos:
[2025-10-27 19:44:05] Usuario Ana inició sesión
[2025-10-27 19:49:24] Usuario Ana cerró sesión

Introduce la fecha (YYYY-MM-DD): 2025-10-27
Eventos del 2025-10-27:
[2025-10-27 19:44:05] Usuario Ana inició sesión
[2025-10-27 19:44:49] Usuario Pedro inició sesión
[2025-10-27 19:44:56] Usuario Pedro cerró sesión
[2025-10-27 19:49:17] Usuario Ana inició sesión
[2025-10-27 19:49:24] Usuario Ana cerró sesión
```

El programa genera el archivo `app.log` si no existe y añade nuevas entradas al final del fichero cada vez que se
registran eventos.

## Contenidos trabajados

- Escritura secuencial en ficheros de texto.
- Uso de clases con recodificación (`OutputStreamWriter`, `InputStreamReader`).
- Filtrado de datos mediante cadenas.
- Manejo de excepciones (`try-with-resources`, `IOException`).
- Uso de clases para almacenar y recuperar información (`BufferedWriter`, `BufferedReader`).
- Conversión de codificación de ficheros.
- Documentación y prueba de la aplicación.

## Criterios de evaluación cumplidos

| Criterio | Descripción                                          | Evidencia en el código                               |
|----------|------------------------------------------------------|------------------------------------------------------|
| a        | Uso de clases para gestión de ficheros y directorios | `FileInputStream`, `FileOutputStream`                |
| c        | Recuperación de información desde ficheros           | Método `leerEventos()`                               |
| d        | Almacenamiento de información en ficheros            | Método `agregarEvento()`                             |
| e        | Conversión entre formatos y codificaciones           | Uso de `OutputStreamWriter` y `InputStreamReader`    |
| f        | Gestión de excepciones                               | Bloques `try-with-resources` y `catch (IOException)` |
| g        | Prueba y documentación del programa                  | Ejemplo de ejecución y este archivo `README.md`      |

## Autor

Proyecto realizado como práctica de acceso a datos en Java.  
Desarrollado por Miriam Romero para demostrar el uso de ficheros, excepciones, logging y codificación en Java.

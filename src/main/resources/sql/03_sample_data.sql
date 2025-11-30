-- -------------------------------------------
-- 03_sample_data.sql
-- Datos de prueba para la base de datos AAD
-- -------------------------------------------

-- LIMPIEZA DE TABLAS (opcional)
TRUNCATE TABLE matricula RESTART IDENTITY CASCADE;
TRUNCATE TABLE alumno RESTART IDENTITY CASCADE;
TRUNCATE TABLE modulo RESTART IDENTITY CASCADE;

-- --------------------------------------------
-- INSERTAR ALUMNOS
-- --------------------------------------------
INSERT INTO alumno (nif, nombre, email)
VALUES
    ('12345678A', 'Miriam Romero', 'miriam@example.com'),
    ('87654321B', 'Rocío Mora', 'rocio@example.com'),
    ('11223344C', 'Javier Pereira', 'javier@example.com');

-- -------------------------------------------
-- INSERTAR MÓDULOS
-- -------------------------------------------
INSERT INTO modulo (codigo, nombre, horas)
VALUES
    ('PROG', 'Programación', 256),
    ('BBDD', 'Bases de Datos', 200),
    ('LM', 'Lenguajes de Marca', 180);

-- --- ============================================
-- INSERTAR MATRÍCULAS
-- (asumiendo IDs autogenerados: alumnos 1,2,3 y módulos 1,2,3)
-- --- ============================================
INSERT INTO matricula (id_alumno, id_modulo, fecha)
VALUES
    (1, 1, CURRENT_DATE),
    (1, 2, CURRENT_DATE),
    (2, 3, CURRENT_DATE);

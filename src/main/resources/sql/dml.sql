-- ======================================================
-- INSERCIÓN DE DATOS INICIALES
-- ======================================================

-- -------------------------
-- Tabla STUDENT (para la práctica 5.8)
-- -------------------------
INSERT INTO student (first_name, last_name, birth_date, average_grade)
VALUES ('Lucía', 'Martínez', '2004-05-10', 8.7),
       ('Carlos', 'Ruiz', '2003-11-22', 7.9),
       ('Ana', 'Torres', '2005-01-15', 9.1),
       ('Laura', 'Pérez', '2004-09-03', 8.2);

-- -------------------------
-- Tabla ALUMNO
-- -------------------------
INSERT INTO alumno (nombre, email)
VALUES ('Laura Pérez', 'laura@centroeducativo.es'),
       ('Carlos Ruiz', 'carlos@centroeducativo.es'),
       ('Ana Torres', 'ana@centroeducativo.es');

-- -------------------------
-- Tabla MODULO
-- -------------------------
INSERT INTO modulo (nombre, horas)
VALUES ('Programación', 120),
       ('Bases de Datos', 100),
       ('Entornos de Desarrollo', 90);

-- -------------------------
-- Tabla MATRICULA
-- -------------------------
INSERT INTO matricula (id_alumno, id_modulo, fecha)
VALUES (1, 1, '2025-10-01'),
       (1, 2, '2025-10-02'),
       (2, 3, '2025-10-03'),
       (3, 1, '2025-10-04');


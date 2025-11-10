-- ======================================================
--  CREACIÓN DE TABLAS PRINCIPALES
-- ======================================================

-- Tabla principal usada en la práctica 5.8
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE,
    average_grade NUMERIC(3,1)
    );

-- ======================================================
--  TABLAS ADICIONALES (SI VIENES DE OTRAS PRÁCTICAS)
-- ======================================================

-- Tabla alumno (similar a student, por si usas el ejercicio anterior)
CREATE TABLE IF NOT EXISTS alumno (
    id_alumno SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100)
    );

-- Tabla modulo
CREATE TABLE IF NOT EXISTS modulo (
    id_modulo SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    horas INT
    );

-- Tabla matricula (relación N:M entre alumno y módulo)
CREATE TABLE IF NOT EXISTS matricula (
    id_alumno INT REFERENCES alumno(id_alumno) ON DELETE CASCADE,
    id_modulo INT REFERENCES modulo(id_modulo) ON DELETE CASCADE,
    fecha DATE,
    PRIMARY KEY (id_alumno, id_modulo)
    );



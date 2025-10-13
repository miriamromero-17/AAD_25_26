package com.example.act_1_2;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String FICHERO = "alumnos.dat";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int op;
            do {
                System.out.println("\n--- MENÚ DE GESTIÓN DE ALUMNOS ---");
                System.out.println("1. Añadir alumno");
                System.out.println("2. Consultar alumno");
                System.out.println("3. Modificar nota");
                System.out.println("4. Ver todos los alumnos");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1 -> anadirAlumno(sc);
                    case 2 -> consultarAlumno(sc);
                    case 3 -> modificarNota(sc);
                    case 4 -> verTodos();
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción no válida");
                }
            } while (op != 0);
        }
    }

    private static void anadirAlumno(Scanner sc) {
        try (AlumnoFile file = new AlumnoFile(FICHERO)) {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nota: ");
            double nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
            file.addAlumno(new Alumno(id, nombre, nota));
            System.out.println("Alumno guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error guardando el alumno.");
        }
    }

    private static void consultarAlumno(Scanner sc) {
        try (AlumnoFile file = new AlumnoFile(FICHERO)) {
            System.out.print("Posición del alumno: ");
            int pos = Integer.parseInt(sc.nextLine());
            Alumno a = file.leerAlumno(pos);
            if (a != null) System.out.println("Alumno en posición " + pos + ": " + a);
            else System.out.println("No existe esa posición.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    private static void modificarNota(Scanner sc) {
        try (AlumnoFile file = new AlumnoFile(FICHERO)) {
            System.out.print("Posición a modificar: ");
            int pos = Integer.parseInt(sc.nextLine());
            System.out.print("Nueva nota: ");
            double nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
            file.modificarNota(pos, nota);
            System.out.println("Nota modificada correctamente.");
        } catch (IOException e) {
            System.out.println("Error modificando la nota.");
        }
    }

    private static void verTodos() {
        try (AlumnoFile file = new AlumnoFile(FICHERO)) {
            int total = file.contar();
            if (total == 0) {
                System.out.println("No hay alumnos guardados.");
                return;
            }
            System.out.println("\n--- LISTA DE ALUMNOS ---");
            for (int i = 0; i < total; i++) {
                System.out.println(i + ": " + file.leerAlumno(i));
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero.");
        }
    }
}

package com.example.act_1_2;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String FILE_PATH = "alumnos.dat";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             AlumnoFile fichero = new AlumnoFile(FILE_PATH)) {

            int opcion;
            do {
                System.out.println("\n--- GESTIÓN DE NOTAS ---");
                System.out.println("1. Insertar nuevo alumno");
                System.out.println("2. Consultar alumno por posición");
                System.out.println("3. Modificar nota de un alumno");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 -> insertarAlumno(fichero, sc);
                    case 2 -> consultarAlumno(fichero, sc);
                    case 3 -> modificarNota(fichero, sc);
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 0);

        } catch (IOException e) {
            System.err.println("Error de acceso al fichero: " + e.getMessage());
        }
    }

    private static void insertarAlumno(AlumnoFile fichero, Scanner sc) throws IOException {
        System.out.print("ID del alumno: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nota: ");
        double nota = Double.parseDouble(sc.nextLine());

        Alumno a = new Alumno(id, nombre, nota);
        fichero.append(a);
        System.out.println("Alumno añadido correctamente.");
    }

    private static void consultarAlumno(AlumnoFile fichero, Scanner sc) throws IOException {
        System.out.print("Introduce la posición del alumno: ");
        int index = Integer.parseInt(sc.nextLine());
        try {
            Alumno a = fichero.readByIndex(index);
            System.out.println("Alumno encontrado: " + a);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No existe alumno en esa posición.");
        }
    }

    private static void modificarNota(AlumnoFile fichero, Scanner sc) throws IOException {
        System.out.print("Introduce la posición del alumno a modificar: ");
        int index = Integer.parseInt(sc.nextLine());
        System.out.print("Nueva nota: ");
        double nota = Double.parseDouble(sc.nextLine());

        try {
            fichero.updateNota(index, nota);
            System.out.println("Nota actualizada correctamente.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No existe alumno en esa posición.");
        }
    }
}




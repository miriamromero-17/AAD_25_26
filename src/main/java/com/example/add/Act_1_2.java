package com.example.add;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Act_1_2 {

    private static final int NOMBRE_MAX = 20;     // 20 caracteres fijos
    private static final int TAM_REGISTRO = 4 + (NOMBRE_MAX * 2) + 8;  // id + nombre + nota = 52 bytes

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fichero = "alumnos.dat";

        while (true) {
            System.out.println("\n--- Gestión de notas ---");
            System.out.println("1. Insertar alumno");
            System.out.println("2. Consultar alumno por posición");
            System.out.println("3. Modificar nota");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            switch (sc.nextLine()) {
                case "1" -> insertar(sc, fichero);
                case "2" -> consultar(sc, fichero);
                case "3" -> modificar(sc, fichero);
                case "0" -> { System.out.println("Adiós."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void insertar(Scanner sc, String fichero) {
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            raf.seek(raf.length()); // añadir al final
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nota: ");
            double nota = Double.parseDouble(sc.nextLine());

            raf.writeInt(id);
            escribirNombre(raf, nombre);
            raf.writeDouble(nota);

            System.out.println("Alumno guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }
    }

    private static void consultar(Scanner sc, String fichero) {
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
            System.out.print("Posición del alumno (empezando en 0): ");
            int pos = Integer.parseInt(sc.nextLine());
            long posicion = (long) pos * TAM_REGISTRO;

            if (posicion >= raf.length()) {
                System.out.println("No existe ese registro.");
                return;
            }

            raf.seek(posicion);
            int id = raf.readInt();
            String nombre = leerNombre(raf);
            double nota = raf.readDouble();

            System.out.printf("Alumno #%d → ID: %d, Nombre: %s, Nota: %.2f%n", pos, id, nombre, nota);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    private static void modificar(Scanner sc, String fichero) {
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            System.out.print("Posición del alumno a modificar: ");
            int pos = Integer.parseInt(sc.nextLine());
            long posicion = (long) pos * TAM_REGISTRO;

            if (posicion >= raf.length()) {
                System.out.println("No existe ese registro.");
                return;
            }

            raf.seek(posicion + 4 + (NOMBRE_MAX * 2)); // salta id + nombre
            System.out.print("Nueva nota: ");
            double nota = Double.parseDouble(sc.nextLine());
            raf.writeDouble(nota);
            System.out.println("Nota modificada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al modificar el fichero.");
        }
    }

    private static void escribirNombre(RandomAccessFile raf, String nombre) throws IOException {
        StringBuilder sb = new StringBuilder(nombre);
        while (sb.length() < NOMBRE_MAX) sb.append(" ");
        if (sb.length() > NOMBRE_MAX) sb.setLength(NOMBRE_MAX);
        raf.writeChars(sb.toString());
    }

    private static String leerNombre(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NOMBRE_MAX; i++) sb.append(raf.readChar());
        return sb.toString().trim();
    }
}


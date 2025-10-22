package com.example.add;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Act_1 {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());


    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Path directorio = pedirDirectorio(sc);
            listar(directorio);
            menu(sc, directorio);
        }
    }

    private static Path pedirDirectorio(Scanner sc) {
        while (true) {
            System.out.print("Introduce una ruta de directorio: ");
            String entrada = sc.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("La ruta no puede estar vacía.");
                continue;
            }
            try {
                Path dir = Paths.get(entrada).normalize();
                if (!Files.exists(dir)) {
                    System.out.println("El directorio no existe.");
                    continue;
                }
                if (!Files.isDirectory(dir)) {
                    System.out.println("La ruta no es un directorio.");
                    continue;
                }
                if (!Files.isReadable(dir)) {
                    System.out.println("No tienes permisos de lectura en este directorio.");
                    continue;
                }
                return dir;
            } catch (InvalidPathException e) {
                System.out.println("Ruta inválida: " + e.getMessage());
            }
        }
    }

    private static void listar(Path dir) {
        System.out.println("\n=== Contenido de: " + dir.toAbsolutePath() + " ===");

        List<Path> entradas = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) entradas.add(p);
        } catch (IOException e) {
            System.out.println("No se pudo leer el directorio: " + e.getMessage());
            return;
        }

        entradas.sort(Comparator
                .comparing((Path p) -> Files.isRegularFile(p) ? 1 : 0)
                .thenComparing(p -> p.getFileName().toString().toLowerCase()));

        if (entradas.isEmpty()) {
            System.out.println("(Vacío)");
            return;
        }

        for (Path p : entradas) {
            if (Files.isDirectory(p)) {
                System.out.println("[DIR]  " + p.getFileName());
            } else if (Files.isRegularFile(p)) {
                mostrarInfoFichero(p);
            } else {
                System.out.println("[OTRO] " + p.getFileName());
            }
        }
        System.out.println();
    }

    private static void mostrarInfoFichero(Path fichero) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(fichero, BasicFileAttributes.class);
            long bytes = attrs.size();
            String mod = FMT.format(attrs.lastModifiedTime().toInstant());
            System.out.printf("      %-40s  %12d bytes  (%s)%n",
                    fichero.getFileName(), bytes, mod);
        } catch (IOException e) {
            System.out.println("      " + fichero.getFileName() + "  (no se pudo leer: " + e.getMessage() + ")");
        }
    }

    private static void menu(Scanner sc, Path dirActual) {
        while (true) {
            System.out.println("=== Menú ===");
            System.out.println("1) Crear un fichero vacío");
            System.out.println("2) Mover un fichero a otra ubicación");
            System.out.println("3) Borrar un fichero existente");
            System.out.println("4) Listar de nuevo");
            System.out.println("5) Mostrar tamaño total ocupado en el directorio");
            System.out.println("0) Salir");
            System.out.print("Elige opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1" -> crearFichero(sc, dirActual);
                case "2" -> moverFichero(sc, dirActual);
                case "3" -> borrarFichero(sc, dirActual);
                case "4" -> listar(dirActual);
                case "5" -> mostrarTamanoTotal(dirActual);
                case "0" -> {
                    System.out.println("Hasta luego.");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void crearFichero(Scanner sc, Path dir) {
        System.out.print("Nombre del nuevo fichero: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Nombre vacío.");
            return;
        }
        Path destino = dir.resolve(nombre);
        try {
            if (Files.exists(destino)) throw new FileAlreadyExistsException(destino.toString());
            Files.createFile(destino);
            System.out.println("Creado: " + destino.getFileName());
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ya existe un fichero con ese nombre.");
        } catch (AccessDeniedException e) {
            System.out.println("Permiso denegado para crear el fichero.");
        } catch (IOException e) {
            System.out.println("Error creando el fichero: " + e.getMessage());
        }
    }

    private static void moverFichero(Scanner sc, Path dir) {
        System.out.print("Nombre del fichero a mover: ");
        String origenNombre = sc.nextLine().trim();
        Path origen = dir.resolve(origenNombre);
        if (!Files.exists(origen) || !Files.isRegularFile(origen)) {
            System.out.println("No existe ese fichero en el directorio actual.");
            return;
        }

        System.out.print("Ruta destino (directorio o ruta completa): ");
        String destinoEntrada = sc.nextLine().trim();
        if (destinoEntrada.isEmpty()) {
            System.out.println("Ruta destino vacía.");
            return;
        }
        try {
            Path destinoPath = Paths.get(destinoEntrada).normalize();
            Path destinoFinal = destinoPath;
            if (Files.exists(destinoPath) && Files.isDirectory(destinoPath)) {
                destinoFinal = destinoPath.resolve(origen.getFileName());
            }
            if (destinoFinal.getParent() != null) Files.createDirectories(destinoFinal.getParent());
            Files.move(origen, destinoFinal, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Movido a: " + destinoFinal.toAbsolutePath());
        } catch (InvalidPathException e) {
            System.out.println("Ruta destino inválida.");
        } catch (AccessDeniedException e) {
            System.out.println("Permiso denegado para mover.");
        } catch (IOException e) {
            System.out.println("Error moviendo el fichero: " + e.getMessage());
        }
    }

    private static void borrarFichero(Scanner sc, Path dir) {
        System.out.print("Nombre del fichero a borrar: ");
        String nombre = sc.nextLine().trim();
        Path objetivo = dir.resolve(nombre);
        try {
            if (!Files.exists(objetivo)) {
                System.out.println("El fichero no existe.");
                return;
            }
            if (!Files.isRegularFile(objetivo)) {
                System.out.println("La ruta no es un fichero regular.");
                return;
            }
            Files.delete(objetivo);
            System.out.println("Borrado: " + objetivo.getFileName());
        } catch (AccessDeniedException e) {
            System.out.println("Permiso denegado para borrar.");
        } catch (IOException e) {
            System.out.println("Error borrando el fichero: " + e.getMessage());
        }
    }

    private static void mostrarTamanoTotal(Path dirActual) {
        try {
            long totalBytes = Files.list(dirActual)
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0L;
                        }
                    }).sum();
            System.out.println("Espacio total ocupado en ficheros del directorio: " + totalBytes + " bytes");
        } catch (IOException e) {
            System.out.println("No se pudo calcular el tamaño total: " + e.getMessage());
        }
    }
}

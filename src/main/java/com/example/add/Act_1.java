package com.example.add;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Mini explorador de ficheros. Permite:
 * - Listar contenido de un directorio
 * - Mostrar información básica de cada fichero
 * - Crear, mover y borrar ficheros
 * Implementación sencilla usando la clase File.
 */
public class Act_1 {

    private static final Logger log = Logger.getLogger(Act_1.class.getName());


    //Método principal que inicia el programa
    public void iniciar() {
        Scanner sc = new Scanner(System.in);

        File directorio = pedirDirectorio(sc);

        listar(directorio);

        menu(sc, directorio);

        sc.close();
    }

    //Solicita un directorio válido al usuario
    private File pedirDirectorio(Scanner sc) {
        while (true) {
            log.info("Introduce una ruta de directorio:");
            String ruta = sc.nextLine().trim();

            File dir = new File(ruta);

            if (!dir.exists()) {
                log.warning("El directorio no existe.");
                continue;
            }
            if (!dir.isDirectory()) {
                log.warning("La ruta indicada no es un directorio.");
                continue;
            }
            if (!dir.canRead()) {
                log.warning("No tienes permisos de lectura.");
                continue;
            }
            return dir;
        }
    }

    //Lista el contenido del directorio
    private void listar(File dir) {
        log.info("Contenido de: " + dir.getAbsolutePath());

        File[] elementos = dir.listFiles();

        if (elementos == null || elementos.length == 0) {
            log.info("(Directorio vacío)");
            return;
        }

        for (File f : elementos) {
            if (f.isDirectory()) {
                log.info("[DIR]  " + f.getName());
            } else if (f.isFile()) {
                long tam = f.length();
                long mod = f.lastModified();

                log.info("[FILE] " + f.getName() +
                        " | " + tam + " bytes" +
                        " | última modificación: " + mod);
            }
        }
    }

    //Menú principal de acciones
    private void menu(Scanner sc, File dirActual) {
        while (true) {
            log.info("""
                    
                    === MENÚ ===
                    1) Crear fichero vacío
                    2) Mover fichero
                    3) Borrar fichero
                    4) Listar de nuevo
                    0) Salir
                    
                    """);

            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1" -> crearFichero(sc, dirActual);
                case "2" -> moverFichero(sc, dirActual);
                case "3" -> borrarFichero(sc, dirActual);
                case "4" -> listar(dirActual);
                case "0" -> {
                    log.info("Saliendo del programa...");
                    return;
                }
                default -> log.warning("Opción no válida.");
            }
        }
    }


    // Crea un fichero vacío
    private void crearFichero(Scanner sc, File dir) {
        log.info("Nombre del nuevo fichero:");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty()) {
            log.warning("El nombre no puede estar vacío.");
            return;
        }

        File nuevo = new File(dir, nombre);

        try {
            if (nuevo.exists()) {
                log.warning("Ya existe un fichero con ese nombre.");
                return;
            }

            boolean creado = nuevo.createNewFile();

            if (creado) {
                log.info("Fichero creado: " + nuevo.getName());
            } else {
                log.warning("No se pudo crear el fichero.");
            }

        } catch (IOException e) {
            log.severe("Error creando el fichero: " + e.getMessage());
        }
    }


    //Mueve un fichero a otra ruta
    private void moverFichero(Scanner sc, File dir) {
        log.info("Nombre del fichero a mover:");
        String nombre = sc.nextLine().trim();

        File origen = new File(dir, nombre);

        if (!origen.exists() || !origen.isFile()) {
            log.warning("El fichero no existe.");
            return;
        }

        log.info("Ruta destino:");
        String destinoRuta = sc.nextLine().trim();

        File destino = new File(destinoRuta);

        // Si es un directorio, añadimos el nombre del fichero
        if (destino.exists() && destino.isDirectory()) {
            destino = new File(destino, origen.getName());
        }

        boolean ok = origen.renameTo(destino);

        if (ok) {
            log.info("Fichero movido correctamente.");
        } else {
            log.warning("No se pudo mover el fichero.");
        }
    }

    //Borra un fichero
    private void borrarFichero(Scanner sc, File dir) {
        log.info("Nombre del fichero a borrar:");
        String nombre = sc.nextLine().trim();

        File objetivo = new File(dir, nombre);

        if (!objetivo.exists() || !objetivo.isFile()) {
            log.warning("El fichero no existe.");
            return;
        }

        boolean eliminado = objetivo.delete();

        if (eliminado) {
            log.info("Fichero borrado.");
        } else {
            log.warning("No se pudo borrar el fichero.");
        }
    }
}

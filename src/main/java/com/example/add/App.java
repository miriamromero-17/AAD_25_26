package com.example.add;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;


@Slf4j
public class App {

    public static void main(String[] args) {
        log.info("Iniciando la aplicación de gestión de logs...");

        // Creamos el gestor de logs
        LogManager gestor = new LogManager("app.log", "UTF-8");

        // Scanner para leer datos del usuario
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ DE SESIÓN ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Cerrar sesión");
            System.out.println("3. Ver eventos por fecha");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Introduce el nombre del usuario: ");
                    String usuarioInicio = sc.nextLine();
                    String mensajeInicio = "Usuario " + usuarioInicio + " inició sesión";
                    gestor.agregarEvento(mensajeInicio);
                    log.info("Registrado: {}", mensajeInicio);
                    break;

                case "2":
                    System.out.print("Introduce el nombre del usuario: ");
                    String usuarioCierre = sc.nextLine();
                    String mensajeCierre = "Usuario " + usuarioCierre + " cerró sesión";
                    gestor.agregarEvento(mensajeCierre);
                    log.info("Registrado: {}", mensajeCierre);
                    break;

                case "3":
                    System.out.print("Introduce la fecha (YYYY-MM-DD): ");
                    String fecha = sc.nextLine();
                    java.util.List<String> eventos = gestor.filtrarPorFecha(fecha);
                    System.out.println("\nEventos del " + fecha + ":");
                    for (String e : eventos) {
                        System.out.println(e);
                    }
                    break;

                case "4":
                    salir = true;
                    log.info("Saliendo de la aplicación...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        sc.close();
        log.info("Aplicación finalizada correctamente.");
    }
}

package com.example.add.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j                     // Lombok crea automáticamente el "log"
@RequiredArgsConstructor   // Lombok crea el constructor con los atributos final
public class LogManager {

    // Atributos del log
    private final String rutaFichero;     // Ruta del archivo donde guardamos los logs
    private final String codificacion;    // Codificación (UTF-8 o ISO-8859-1)

    // Método para agregar un evento al log
    public void agregarEvento(String mensaje) {
        LocalDateTime ahora = LocalDateTime.now(); // Fecha y hora actuales
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String linea = "[" + ahora.format(formato) + "] " + mensaje;

        // Escritura secuencial con OutputStreamWriter y BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(rutaFichero, true), codificacion))) {

            writer.write(linea);
            writer.newLine(); // salto de línea
            log.info("Evento añadido al log: {}", linea);

        } catch (IOException e) {
            log.error("Error al escribir en el archivo de log", e);
        }
    }

    public List<String> leerEventos() {
        List<String> eventos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaFichero), codificacion))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                eventos.add(linea);
            }

        } catch (IOException e) {
            log.error("Error al leer el log", e);
        }

        return eventos;
    }

    // Filtrar eventos por fecha (YYYY-MM-DD)
    public List<String> filtrarPorFecha(String fecha) {
        List<String> filtrados = new ArrayList<>();
        for (String evento : leerEventos()) {
            if (evento.contains("[" + fecha)) {
                filtrados.add(evento);
            }
        }
        return filtrados;
    }

}

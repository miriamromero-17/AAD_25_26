package com.example.add;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Alumnos grupo = new Alumnos();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miria\\Documents\\Grado Superior\\2º Año\\Acceso a Datos\\AAD_25_26\\src\\test\\java\\com\\example\\add\\resources\\Alumnos.csv"))) {
            String linea = br.readLine(); // Saltar cabecera

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double nota = Double.parseDouble(partes[2]);
                grupo.agregarAlumno(new Alumno(id, nombre, nota));
            }

            log.info("Lectura del fichero alumnos.csv completada correctamente.");
            grupo.mostrarAlumnos();
            grupo.getCantidad();

            // Exportar a JSON y XML
            grupo.exportarAJson("C:\\Users\\miria\\Documents\\Grado Superior\\2º Año\\Acceso a Datos\\AAD_25_26\\src\\test\\java\\com\\example\\add\\resources\\Alumnos.json");
            grupo.exportarAXml("C:\\Users\\miria\\Documents\\Grado Superior\\2º Año\\Acceso a Datos\\AAD_25_26\\src\\test\\java\\com\\example\\add\\resources\\Alumnos.xml");

        } catch (IOException e) {
            log.error("Error al leer el fichero alumnos.csv", e);
        } catch (NumberFormatException e) {
            log.error("Error al convertir datos numéricos del CSV", e);
        } catch (Exception e) {
            log.error("Error inesperado durante la ejecución", e);
        }
    }
}

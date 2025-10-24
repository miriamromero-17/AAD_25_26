package com.example.add;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Alumnos {
    private final List<Alumno> lista = new ArrayList<>();

    public void agregarAlumno(Alumno alumno) {
        lista.add(alumno);
    }

    public void mostrarAlumnos() {
        for (Alumno a : lista) {
            System.out.println(a);
        }
    }

    public void getCantidad() {
        System.out.println("Cantidad total de alumnos: " + lista.size());
    }

    public void exportarAJson(String rutaArchivo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(rutaArchivo), lista);
            log.info("Archivo JSON generado correctamente en: {}", rutaArchivo);
        } catch (IOException e) {
            log.error("Error al generar el archivo JSON", e);
        }
    }

    public void exportarAXml(String rutaArchivo) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(rutaArchivo), lista);
            log.info("Archivo XML generado correctamente en: {}", rutaArchivo);
        } catch (IOException e) {
            log.error("Error al generar el archivo XML", e);
        }
    }
}

package src;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
public class FicheroTexto {
    public static void main(String[] args) throws IOException {
        Path ruta = Paths.get("alumnos.txt");
        // Escribir en el fichero
        Files.writeString(ruta, "ID,Nombre\n1,Ana\n2,Juan", StandardCharsets.UTF_8);
        // Leer del fichero
        String contenido = Files.readString(ruta, StandardCharsets.UTF_8);
        System.out.println("Contenido del fichero:");
        System.out.println(contenido);
    }
}

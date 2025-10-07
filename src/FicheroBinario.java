package src;

import java.io.*;
public class FicheroBinario {
    public static void main(String[] args) {
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream("imagen.jpg"))) {

            byte[] buffer = new byte[1024];
            int bytesLeidos;
            int total = 0;

            while ((bytesLeidos = bis.read(buffer)) != -1) {
                total += bytesLeidos;
            }
            System.out.println("Imagen leída con éxito. Total bytes: " + total);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}

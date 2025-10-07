package com.example.act_1_2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AlumnoFile implements AutoCloseable {
    private final RandomAccessFile raf;

    // Posiciones dentro del registro (offsets)
    public static final int OFFSET_ID = 0; // 4 bytes
    public static final int OFFSET_NOMBRE = Integer.BYTES; // 4
    public static final int OFFSET_NOTA = OFFSET_NOMBRE + (Character.BYTES * Alumno.NAME_LEN); // 44

    public AlumnoFile(String path) throws IOException {
        this.raf = new RandomAccessFile(path, "rw");
    }

    // Calcula la posición del registro según su índice
    private long posOfIndex(int index) {
        return (long) index * Alumno.RECORD_SIZE;
    }

    //Añade un nuevo alumno al final del fichero (acceso secuencial).
    public void append(Alumno a) throws IOException {
        raf.seek(raf.length()); // ir al final
        writeAtCurrent(a);
    }

    //Devuelve el número total de registros almacenados.
    public int size() throws IOException {
        return (int) (raf.length() / Alumno.RECORD_SIZE);
    }

    // Escribe un alumno en la posición actual del puntero.
    private void writeAtCurrent(Alumno a) throws IOException {
        raf.writeInt(a.getId());
        String fixed = Alumno.fixNombre(a.getNombre());
        for (int i = 0; i < Alumno.NAME_LEN; i++) {
            raf.writeChar(fixed.charAt(i));
        }
        raf.writeDouble(a.getNota());
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }
}


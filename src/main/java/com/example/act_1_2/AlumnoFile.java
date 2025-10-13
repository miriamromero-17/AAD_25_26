package com.example.act_1_2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AlumnoFile implements AutoCloseable {
    private final RandomAccessFile raf;

    public AlumnoFile(String nombre) throws IOException {
        raf = new RandomAccessFile(nombre, "rw");
    }

    public void addAlumno(Alumno nuevo) throws IOException {
        raf.seek(raf.length());
        nuevo.write(raf);
    }

    public Alumno leerAlumno(int index) throws IOException {
        long pos = (long) index * Alumno.RECORD_SIZE;
        if (pos >= raf.length()) return null;
        raf.seek(pos);
        return Alumno.read(raf);
    }

    public void modificarNota(int index, double nuevaNota) throws IOException {
        long pos = (long) index * Alumno.RECORD_SIZE + 4 + 2 * Alumno.NAME_LEN;
        raf.seek(pos);
        raf.writeDouble(nuevaNota);
    }

    public int contar() throws IOException {
        return (int) (raf.length() / Alumno.RECORD_SIZE);
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }
}

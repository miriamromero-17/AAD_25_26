package com.example.act_1_2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Alumno {
    public static final int NAME_LEN = 20; // longitud fija
    public static final int RECORD_SIZE = 4 + 2 * NAME_LEN + 8; // id + nombre + nota

    private int id;
    private String nombre;
    private double nota;

    public Alumno(int id, String nombre, double nota) {
        this.id = id;
        this.nombre = nombre;
        this.nota = nota;
    }

    public void write(RandomAccessFile raf) throws IOException {
        raf.writeInt(id);
        StringBuffer sb = new StringBuffer(nombre);
        sb.setLength(NAME_LEN);
        raf.writeChars(sb.toString());
        raf.writeDouble(nota);
    }

    public static Alumno read(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        char[] nameChars = new char[NAME_LEN];
        for (int i = 0; i < NAME_LEN; i++) nameChars[i] = raf.readChar();
        String nombre = new String(nameChars).trim();
        double nota = raf.readDouble();
        return new Alumno(id, nombre, nota);
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " - " + nota;
    }
}

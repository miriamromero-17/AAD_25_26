package com.example.act_1_2;

public class Alumno {
    public static final int NAME_LEN = 20; // longitud fija del nombre
    public static final int RECORD_SIZE =
            Integer.BYTES + (Character.BYTES * NAME_LEN) + Double.BYTES; // 4 + 40 + 8 = 52 bytes

    private final int id;
    private final String nombre;
    private final double nota;

    public Alumno(int id, String nombre, double nota) {
        this.id = id;
        this.nombre = fixNombre(nombre);
        this.nota = nota;
    }

    // Rellenar con espacios o corta a 20 caracteres
    public static String fixNombre(String s) {
        if (s == null) s = "";
        if (s.length() > NAME_LEN) return s.substring(0, NAME_LEN);
        return String.format("%-" + NAME_LEN + "s", s);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getNota() { return nota; }

    @Override
    public String toString() {
        return "Alumno{id=" + id + ", nombre='" + nombre.trim() + "', nota=" + nota + "}";
    }
}


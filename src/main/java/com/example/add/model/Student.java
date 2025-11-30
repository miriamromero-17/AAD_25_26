package com.example.add.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;
    private String nif;
    private String name;
    private String email;

    // Atributos que usa el código de AddApplication
    private String course;
    private List<Module> modules;

    // Constructor EXACTO que usa AddApplication
    public Student(Integer id, String nif, String name, String email) {
        this.id = id;
        this.nif = nif;
        this.name = name;
        this.email = email;
        this.course = course;
        this.modules = modules;
    }
}

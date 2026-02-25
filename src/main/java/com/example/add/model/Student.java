package com.example.add.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Integer id;

    @Column(name = "nif")
    private String nif;

    @Column(name = "nombre")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Transient
    private String course;

    @Transient
    private List<Module> modules;

    public Student(Integer id, String nif, String name, String email, String course, List<Module> modules) {
        this.id = id;
        this.nif = nif;
        this.name = name;
        this.email = email;
        this.course = course;
        this.modules = modules;
    }
}

package com.example.add.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    private Integer id;

    @Column(name = "codigo")
    private String code;

    @Column(name = "nombre")
    private String name;

    @Column(name = "horas")
    private Integer hours;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Module(Integer id, String code, String name, Integer hours) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.hours = hours;
    }

}

package com.example.add.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "matricula")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "id_alumno")
    private Student student;

    @ManyToOne
    @MapsId("moduleId")
    @JoinColumn(name = "id_modulo")
    private Module module;

    @Column(name = "fecha", nullable = false)
    private LocalDate date;

    @Column(name = "nota_final")
    private Double finalGrade;

}

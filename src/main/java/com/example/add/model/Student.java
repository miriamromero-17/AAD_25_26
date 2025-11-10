package com.example.add.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Double averageGrade;
}

package com.example.add.service;

import com.example.add.model.Enrollment;
import com.example.add.model.EnrollmentId;
import com.example.add.model.Module;
import com.example.add.model.Student;
import com.example.add.repository.EnrollmentRepository;
import com.example.add.repository.ModuleRepository;
import com.example.add.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    // -------------------------------------
    // CREAR ESTUDIANTE (validaciones simples)
    // -------------------------------------
    public Student createStudent(Student student) {

        if (student.getNif() == null || student.getNif().isBlank())
            throw new IllegalArgumentException("El NIF es obligatorio");

        if (student.getName() == null || student.getName().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");

        if (student.getEmail() == null || student.getEmail().isBlank())
            throw new IllegalArgumentException("El email es obligatorio");


        // evitar duplicados
        Student existing = studentRepository.findByNif(student.getNif()).orElse(null);
        if (existing != null) return existing;


        return studentRepository.save(student);
    }

    // -------------------------------------
    // CREAR MODULO (validaciones)
    // -------------------------------------
    public Module createModule(Module module) {

        if (module.getCode() == null || module.getCode().isBlank())
            throw new IllegalArgumentException("El código es obligatorio");

        if (module.getName() == null || module.getName().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");

        if (module.getHours() == null || module.getHours() <= 0)
            throw new IllegalArgumentException("Las horas deben ser mayores que 0");


        // evitar duplicados
        Module existing = moduleRepository.findByCode(module.getCode()).orElse(null);
        if (existing != null) return existing;


        return moduleRepository.save(module);
    }

    // -------------------------------------
    // MATRICULAR ESTUDIANTE EN MÓDULO
    // Transacción declarativa (@Transactional)
    // -------------------------------------

    @Transactional
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no existe"));

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Módulo no existe"));

        EnrollmentId id = new EnrollmentId(studentId, moduleId);

        if (enrollmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Ya está matriculado en ese módulo");
        }

        Enrollment e = new Enrollment();
        e.setId(id);
        e.setStudent(student);
        e.setModule(module);
        e.setDate(LocalDate.now());
        e.setFinalGrade(null);

        return enrollmentRepository.save(e);
    }


    // -------------------------------------
    // LISTA DE  METODOS
    // -------------------------------------
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public List<Module> listModules() {
        return moduleRepository.findAll();
    }

    public List<Enrollment> listEnrollments() {
        return enrollmentRepository.findAll();
    }

    public int countEnrollments(int studentId) {
        return (int) enrollmentRepository.countByIdStudentId(studentId);
    }

    public Double avgGradeByModule(Integer moduleId) {
        return null;
    }
}

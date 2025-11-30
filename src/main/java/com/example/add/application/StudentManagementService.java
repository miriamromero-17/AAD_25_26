package com.example.add.application;

import com.example.add.model.Enrollment;
import com.example.add.model.Module;
import com.example.add.model.Student;
import com.example.add.repository.EnrollmentRepository;
import com.example.add.repository.ModuleRepository;
import com.example.add.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Repository
@RequiredArgsConstructor
@Slf4j
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    // -------------------------------------
    // CREATE STUDENT (validaciones simples)
    // -------------------------------------
    public Student createStudent(Student student) {

        if (student.getNif() == null || student.getNif().isBlank())
            throw new IllegalArgumentException("NIF is required");

        if (student.getName() == null || student.getName().isBlank())
            throw new IllegalArgumentException("Name is required");

        if (student.getEmail() == null || student.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");

        // evitar duplicados
        Student existing = studentRepository.findByNif(student.getNif());
        if (existing != null) return existing;

        return studentRepository.insert(student);
    }

    // -------------------------------------
    // CREATE MODULE (validaciones)
    // -------------------------------------
    public Module createModule(Module module) {

        if (module.getCode() == null || module.getCode().isBlank())
            throw new IllegalArgumentException("Code is required");

        if (module.getName() == null || module.getName().isBlank())
            throw new IllegalArgumentException("Name is required");

        if (module.getHours() == null || module.getHours() <= 0)
            throw new IllegalArgumentException("Hours must be > 0");

        // evitar duplicados
        Module existing = moduleRepository.findByCode(module.getCode());
        if (existing != null) return existing;

        return moduleRepository.insert(module);
    }

    // -------------------------------------
    // ENROLL STUDENT IN MODULE
    // Declarative Transaction
    // -------------------------------------

    @Transactional
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {

        Student student = studentRepository.findById(studentId);
        if (student == null)
            throw new IllegalArgumentException("Student does not exist");

        Module module = moduleRepository.findById(moduleId);
        if (module == null)
            throw new IllegalArgumentException("Module does not exist");

        Enrollment e = new Enrollment(
                null,
                studentId,
                moduleId,
                LocalDate.now()
        );

        return enrollmentRepository.insert(e);
    }

    // -------------------------------------
    // LIST METHODS
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
        return enrollmentRepository.countEnrollments(studentId);
    }
}

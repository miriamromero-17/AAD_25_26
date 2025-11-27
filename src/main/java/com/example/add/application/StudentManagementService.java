package com.example.add.application;

import com.example.add.model.Enrollment;
import com.example.add.model.Module;
import com.example.add.model.Student;
import com.example.add.repository.EnrollmentRepository;
import com.example.add.repository.ModuleRepository;
import com.example.add.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    // ------------------------------------------------------
    // CREATE STUDENT
    // ------------------------------------------------------
    public Student createStudent(Student student) {
        log.info("Creating student: {}", student);
        return studentRepository.insert(student);
    }

    // ------------------------------------------------------
    // CREATE MODULE
    // ------------------------------------------------------
    public Module createModule(Module module) {
        log.info("Creating module: {}", module);
        return moduleRepository.insert(module);
    }

    // ------------------------------------------------------
    // ENROLL STUDENT IN MODULE
    // ------------------------------------------------------
    public Enrollment enroll(int studentId, int moduleId) {
        log.info("Enrolling student {} in module {}", studentId, moduleId);

        Enrollment e = new Enrollment();
        e.setStudentId(studentId);
        e.setModuleId(moduleId);

        return enrollmentRepository.createEnrollment(e);
    }

    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {
        return enroll(studentId, moduleId);
    }

    // ------------------------------------------------------
    // LIST ALL STUDENTS
    // ------------------------------------------------------
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    // ------------------------------------------------------
    // LIST ALL MODULES
    // ------------------------------------------------------
    public List<Module> listModules() {
        return moduleRepository.findAll();
    }

    // ------------------------------------------------------
    // LIST ALL ENROLLMENTS
    // ------------------------------------------------------
    public List<Enrollment> listEnrollments() {
        return enrollmentRepository.findAll();
    }

    // ------------------------------------------------------
    // COUNT ENROLLMENTS BY STUDENT
    // ------------------------------------------------------
    public int countEnrollments(int studentId) {
        return enrollmentRepository.countEnrollments(studentId);
    }
}

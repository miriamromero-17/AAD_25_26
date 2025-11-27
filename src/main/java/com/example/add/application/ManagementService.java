package com.example.add.application;

import com.example.add.config.PostgresqlDriver;
import com.example.add.model.Enrollment;
import com.example.add.model.Module;
import com.example.add.model.Student;
import com.example.add.repository.EnrollmentRepository;
import com.example.add.repository.ModuleRepository;
import com.example.add.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class ManagementService implements CustomService<Student> {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PostgresqlDriver driver;

    // ---------------------------------------------------------------------------
    // createModule (CON VALIDACIONES)
    // ---------------------------------------------------------------------------
    @Override
    public Module createModule(Module module) {
        log.info("Validating and creating module: {}", module);

        // VALIDACIÓN: mismo código
        Module existing = moduleRepository.findByCode(module.getCode());
        if (existing != null) {
            log.info("Module with code {} already exists. Returning existing.", module.getCode());
            return existing;
        }

        // Inserción
        return moduleRepository.insert(module);
    }

    // ---------------------------------------------------------------------------
    // createStudent (VALIDACIÓN DE NIF, EMAIL, NOMBRE, ETC)
    // ---------------------------------------------------------------------------
    @Override
    public Student createStudent(Student student) {
        log.info("Validating and creating student: {}", student);

        // VALIDACIONES BÁSICAS
        if (student.getNif() == null || student.getNif().isEmpty())
            throw new IllegalArgumentException("NIF cannot be empty");

        if (student.getName() == null || student.getName().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");

        if (student.getEmail() == null || student.getEmail().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty");

        // VALIDACIÓN: estudiante ya existe
        Student existing = studentRepository.findByNif(student.getNif());
        if (existing != null) {
            log.info("Student with NIF {} already exists. Returning existing", student.getNif());
            return existing;
        }

        // Inserción
        return studentRepository.insert(student);
    }

    // ---------------------------------------------------------------------------
    // enrollStudentInModule (TRANSACCIÓN MANUAL)
    // ---------------------------------------------------------------------------
    @Override
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) throws SQLException {
        try {

            // 1. Iniciar transacción
            driver.beginTransaction();

            // 2. Recuperar y validar estudiante
            var student = studentRepository.findById(studentId);
            if (student == null)
                throw new IllegalArgumentException("Student not found: " + studentId);

            // 3. Recuperar y validar módulo
            var module = moduleRepository.findById(moduleId);
            if (module == null)
                throw new IllegalArgumentException("Module not found: " + moduleId);

            // 4. Crear matrícula con fecha actual
            Enrollment created = new Enrollment(
                    null,
                    student.getId(),
                    module.getId(),
                    LocalDate.now()
            );

            // 5. Guardar matrícula
            enrollmentRepository.createEnrollment(created);

            // 6. Confirmar la transacción
            driver.commit();

            return created;

        } catch (Exception e) {
            // 7. Revertir en caso de error
            driver.rollback();
            throw new RuntimeException("Error enrolling student in module", e);
        }
    }
}

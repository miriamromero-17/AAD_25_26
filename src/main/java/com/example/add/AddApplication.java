package com.example.add;

import com.example.add.model.Enrollment;
import com.example.add.model.Module;
import com.example.add.model.Profile;
import com.example.add.model.Student;
import com.example.add.repository.StudentRepository;
import com.example.add.service.StudentManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class AddApplication implements CommandLineRunner {

    private final StudentManagementService managementService;
    private final StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(AddApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Profile perfil = new Profile();
        perfil.setAddress("Calle Falsa 123");
        perfil.setPhone("600112233");

        Student miriam = new Student();
        miriam.setNif("66280457T");
        miriam.setName("Miriam");
        miriam.setEmail("miriam@g.educaand.es");
        miriam.setCourse("DAW");
        miriam.setProfile(perfil);

        Module programacion = new Module();
        programacion.setCode("0485");
        programacion.setName("Programación");
        programacion.setHours(250);

        // 1) CRUD: crear alumno
        miriam = managementService.createStudent(miriam);
        log.info("Alumno creado: {}", miriam);

        // 2) CRUD: crear módulo
        programacion = managementService.createModule(programacion);
        log.info("Módulo creado: {}", programacion);

        // 3) Transacción: matricular
        Enrollment enrollment = managementService.enrollStudentInModule(
                miriam.getId(), programacion.getId()
        );
        log.info("Matrícula realizada: {}", enrollment);
        //Añadir nota a la matrícula para que no quede NULL
        enrollment.setFinalGrade(8.5);

        // 4) CRUD / query: contar matrículas
        int countEnrollments = managementService.countEnrollments(miriam.getId());
        log.info("{} módulos matriculados para el alumno {}", countEnrollments, miriam.getName());

        // 5) CRUD: recuperar por NIF (repositorio)
        miriam = studentRepository.findByNif(miriam.getNif())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        log.info("Alumno recuperado: {}", miriam);

        Student alumnoSQL =
                studentRepository.buscarPorNifSQL("66280457T")
                        .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        log.info("Alumno obtenido mediante SQL nativo: {}", alumnoSQL);

        // 6) Procedimiento avg_grade_by_module (lo haces en el servicio)
        Double avg = managementService.avgGradeByModule(programacion.getId());
        log.info("Media de notas del módulo {} = {}", programacion.getCode(), avg);

        // 7) ROLLBACK: descomenta para demostrar rollback
        // throw new RuntimeException("Forzando rollback de la transacción");
    }
}
package com.example.add;

import com.example.add.application.StudentManagementService;
import com.example.add.model.Module;
import com.example.add.model.Student;
import com.example.add.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AddApplication implements CommandLineRunner {

    @Autowired
    private StudentManagementService studentManagementService;

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(AddApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // ----------------------------
        // CÓDIGO DEL PDF (NO MODIFICAR)
        // ----------------------------

        Student miriam = new Student(
                null,
                "66280457T",
                "Miriam",
                "miriam@g.educaand.es",
                "DAW",
                List.of()
        );

        Module programacion = new Module(
                null,
                "0485",
                "Programación",
                250
        );

        miriam = studentManagementService.createStudent(miriam);
        programacion = studentManagementService.createModule(programacion);

        studentManagementService.enrollStudentInModule(
                miriam.getId(),
                programacion.getId()
        );

        studentRepository.delete(miriam.getId());
    }
}

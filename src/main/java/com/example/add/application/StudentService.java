package com.example.add.application;

import com.example.add.model.Student;
import com.example.add.repository.StudentJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentJdbcRepository studentRepository;

    /**
     * Valida que un estudiante tenga nombre y apellidos no vacíos.
     */
    public boolean validate(Student student) {
        return student != null
                && student.getFirstName() != null && !student.getFirstName().isBlank()
                && student.getLastName() != null && !student.getLastName().isBlank();
    }

    /**
     * Crea un estudiante en la base de datos si pasa la validación.
     */
    public Student createStudent(Student student) {
        if (validate(student)) {
            return studentRepository.create(student);
        } else {
            throw new IllegalArgumentException("Student data is not valid");
        }
    }

    /**
     * Busca un estudiante por su ID.
     */
    public Student findById(Integer id) {
        Student probe = new Student();
        probe.setId(id);
        return studentRepository.read(probe);
    }

    /**
     * Actualiza los datos de un estudiante existente.
     */
    public Student updateStudent(Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("Cannot update student without ID");
        }
        return studentRepository.update(student);
    }

    /**
     * Elimina un estudiante de la base de datos.
     */
    public boolean deleteStudent(Integer id) {
        Student toDelete = new Student();
        toDelete.setId(id);
        return studentRepository.delete(toDelete);
    }
}

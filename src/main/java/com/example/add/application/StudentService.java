package com.example.add.application;

import com.example.add.model.Module;
import com.example.add.model.Student;
import com.example.add.repository.ModuleRepository;
import com.example.add.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements CustomService<Student> {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;

//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean validate(Student entity) {
        return !entity.getDni().isBlank() && !entity.getName().isBlank();
    }

    public Student createStudent(final Student student, final List<Module> modules) {
        if (validate(student)) {
            student.setModules(modules);
            return studentRepository.create(student);
        }
        return null;
    }
}

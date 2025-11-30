package com.example.add.application;

import com.example.add.model.Enrollment;
import com.example.add.model.Module;

import java.sql.SQLException;

public interface CustomService<T> {

    // ============================================================================
    // createModule (CON VALIDACIONES)
    // ============================================================================
    com.example.add.model.Module createModule(Module module);

    T createStudent(T student);

    T createModule(T module);

    Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) throws SQLException;
}
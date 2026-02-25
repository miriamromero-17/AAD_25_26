package com.example.add.repository;

import com.example.add.model.Enrollment;
import com.example.add.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository
        extends JpaRepository<Enrollment, EnrollmentId> {

    boolean existsByIdStudentIdAndIdModuleId(Integer studentId, Integer moduleId);

    // Matrículas de un alumno
    List<Enrollment> findByStudentId(Integer studentId);

    @Query("SELECT e FROM Enrollment e WHERE e.finalGrade >= :minGrade")
    List<Enrollment> findByMinFinalGrade(@Param("minGrade") Double minGrade);

    //Cuenta cuántas matrículas tiene un alumno
    int countByIdStudentId(int studentId);
}

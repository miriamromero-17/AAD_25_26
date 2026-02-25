package com.example.add.repository;

import com.example.add.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByNif(String nif);

    // CONSULTA SQL NATIVA
    @Query(value = "SELECT * FROM alumno WHERE nif = :nif", nativeQuery = true)
    Optional<Student> buscarPorNifSQL(@Param("nif") String nif);

}

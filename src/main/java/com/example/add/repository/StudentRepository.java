package com.example.add.repository;

import com.example.add.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Student> rowMapper = (rs, rowNum) ->
            new Student(
                    rs.getInt("id_alumno"),
                    rs.getString("nif"),
                    rs.getString("nombre"),
                    rs.getString("email")
            );

    public Student insert(Student student) {
        String sql = "INSERT INTO alumno (nif, nombre, email) VALUES (?, ?, ?) RETURNING id_alumno";

        Integer newId = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                student.getNif(),
                student.getName(),
                student.getEmail()
        );

        student.setId(newId);
        return student;
    }

    public List<Student> findAll() {
        return jdbcTemplate.query(
                "SELECT id_alumno, nif, nombre, email FROM alumno",
                rowMapper
        );
    }

    public Student findById(int id) {
        List<Student> result = jdbcTemplate.query(
                "SELECT id_alumno, nif, nombre, email FROM alumno WHERE id_alumno = ?",
                rowMapper,
                id
        );
        return result.isEmpty() ? null : result.get(0);
    }

    public Student findByNif(String nif) {
        List<Student> result = jdbcTemplate.query(
                "SELECT id_alumno, nif, nombre, email FROM alumno WHERE nif = ?",
                rowMapper,
                nif
        );
        return result.isEmpty() ? null : result.get(0);
    }

    public Student update(Student student) {
        jdbcTemplate.update(
                "UPDATE alumno SET nif=?, nombre=?, email=? WHERE id_alumno=?",
                student.getNif(),
                student.getName(),
                student.getEmail(),
                student.getId()
        );
        return student;
    }

    public boolean delete(int id) {
        return jdbcTemplate.update(
                "DELETE FROM alumno WHERE id_alumno=?",
                id
        ) > 0;
    }
}

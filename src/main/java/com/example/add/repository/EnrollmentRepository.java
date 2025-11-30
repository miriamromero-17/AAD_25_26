package com.example.add.repository;

import com.example.add.model.Enrollment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EnrollmentRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Enrollment> rowMapper = (rs, rowNum) ->
            new Enrollment(
                    null,
                    rs.getInt("id_alumno"),
                    rs.getInt("id_modulo"),
                    rs.getDate("fecha").toLocalDate()
            );

    // INSERT matricula
    public Enrollment insert(Enrollment enrollment) {
        String sql = "INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                enrollment.getStudentId(),
                enrollment.getModuleId(),
                enrollment.getDate()
        );

        return enrollment;
    }

    // LIST ALL
    public List<Enrollment> findAll() {
        return jdbcTemplate.query(
                "SELECT id_alumno, id_modulo, fecha FROM matricula",
                rowMapper
        );
    }

    // FIND BY STUDENT
    public List<Enrollment> findByStudent(int studentId) {
        return jdbcTemplate.query(
                "SELECT id_alumno, id_modulo, fecha FROM matricula WHERE id_alumno = ?",
                rowMapper,
                studentId
        );
    }

    // DELETE
    public boolean delete(int studentId, int moduleId) {
        return jdbcTemplate.update(
                "DELETE FROM matricula WHERE id_alumno = ? AND id_modulo = ?",
                studentId, moduleId
        ) > 0;
    }

    // FUNCTION: count_enrollments
    public int countEnrollments(int studentId) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("count_enrollments");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("student_id", studentId);

        return call.executeFunction(Integer.class, params);
    }
}

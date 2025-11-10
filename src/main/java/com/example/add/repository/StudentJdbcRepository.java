package com.example.add.repository;

import com.example.add.config.PostgresqlDriver;
import com.example.add.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class StudentJdbcRepository implements CrudRepository<Student> {

    private static final String SQL_INSERT = """
            INSERT INTO student (first_name, last_name, birth_date, average_grade)
            VALUES (?, ?, ?, ?)
            """;

    private static final String SQL_SELECT_BY_ID = """
            SELECT id, first_name, last_name, birth_date, average_grade
            FROM student
            WHERE id = ?
            """;

    private static final String SQL_UPDATE = """
            UPDATE student
            SET first_name = ?, last_name = ?, birth_date = ?, average_grade = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM student
            WHERE id = ?
            """;

    private final PostgresqlDriver dataSource;


    @Override
    public Student create(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setDate(3, entity.getBirthDate() != null ? Date.valueOf(entity.getBirthDate()) : null);
            ps.setObject(4, entity.getAverageGrade(), Types.NUMERIC);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) entity.setId(keys.getInt(1));
            }
            log.info("create OK: {}", entity);
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Student", e);
        }
    }

    @Override
    public Student read(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, entity.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading Student", e);
        }
    }

    @Override
    public Student update(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setDate(3, entity.getBirthDate() != null ? Date.valueOf(entity.getBirthDate()) : null);
            ps.setObject(4, entity.getAverageGrade(), Types.NUMERIC);
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
            log.info("update OK: {}", entity);
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Student", e);
        }
    }

    @Override
    public boolean delete(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, entity.getId());
            int deleted = ps.executeUpdate();
            log.info("delete OK: {}", deleted > 0);
            return deleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Student", e);
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        Date bd = rs.getDate("birth_date");
        s.setBirthDate(bd != null ? bd.toLocalDate() : null);
        s.setAverageGrade(
                rs.getBigDecimal("average_grade") != null
                        ? rs.getBigDecimal("average_grade").doubleValue()
                        : 0.0
        );
        return s;
    }
}

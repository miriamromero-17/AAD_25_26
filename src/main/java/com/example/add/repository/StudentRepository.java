package com.example.add.repository;

import com.example.add.config.PostgresqlDriver;
import com.example.add.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudentRepository {

    private final PostgresqlDriver driver;

    // -----------------
    // CREATE
    // -----------------
    public Student insert(Student student) {
        String sql = "INSERT INTO alumno (nif, nombre, email) VALUES (?, ?, ?) RETURNING id_alumno";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, student.getNif());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getEmail());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                student.setId(rs.getInt(1));
            }

            return student;

        } catch (SQLException e) {
            log.error("Error inserting student", e);
            throw new RuntimeException(e);
        }
    }

    // --------------------
    // FIND ALL
    // --------------------
    public List<Student> findAll() {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno";
        List<Student> students = new ArrayList<>();

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id_alumno"));
                s.setNif(rs.getString("nif"));
                s.setName(rs.getString("nombre"));
                s.setEmail(rs.getString("email"));
                students.add(s);
            }

        } catch (SQLException e) {
            log.error("Error finding all students", e);
        }

        return students;
    }

    // -----------------------
    // FIND BY ID
    // -----------------------
    public Student findById(int id) {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno WHERE id_alumno = ?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id_alumno"),
                        rs.getString("nif"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        null,
                        null
                );
            }

        } catch (SQLException e) {
            log.error("Error finding student by id", e);
        }

        return null;
    }

    // ------------------------------------------------------
    // FIND BY NIF: para comprobar si el estudiante ya existe
    // ------------------------------------------------------
    public Student findByNif(String nif) {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno WHERE nif = ?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nif);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id_alumno"),
                        rs.getString("nif"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        null,
                        null
                );
            }

        } catch (Exception e) {
            log.error("Error finding student by NIF", e);
        }

        return null;
    }

    // ------------------
    // UPDATE
    // ------------------
    public Student update(Student student) {
        String sql = "UPDATE alumno SET nif=?, nombre=?, email=? WHERE id_alumno=?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, student.getNif());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getEmail());
            stmt.setInt(4, student.getId());

            stmt.executeUpdate();
            return student;

        } catch (SQLException e) {
            log.error("Error updating student", e);
            throw new RuntimeException(e);
        }
    }

    // -------------------
    // DELETE
    // -------------------
    public boolean delete(int studentId) {

        String sql = "DELETE FROM alumno WHERE id_alumno = ?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            int rows = stmt.executeUpdate();

            log.info("Deleted student with id {}. Rows affected: {}", studentId, rows);

            return rows > 0;

        } catch (Exception e) {
            log.error("Error deleting student {}", studentId, e);
            return false;
        }
    }
}

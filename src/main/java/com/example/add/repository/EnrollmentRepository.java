package com.example.add.repository;

import com.example.add.config.PostgresqlDriver;
import com.example.add.model.Enrollment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EnrollmentRepository {

    private final PostgresqlDriver driver;

    // ---------------------------------------------------------------
    //  CREATE ENROLLMENT (WITH MANUAL TRANSACTION)
    // ---------------------------------------------------------------
    public Enrollment createEnrollment(Enrollment enrollment) {

        String sql = "INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES (?, ?, ?)";

        try {
            driver.beginTransaction();   // Start transaction
            Connection con = driver.getTransactionalConnection();

            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setInt(1, enrollment.getStudentId());
                stmt.setInt(2, enrollment.getModuleId());
                stmt.setDate(3, Date.valueOf(LocalDate.now()));

                stmt.executeUpdate();

                driver.commit();        // Commit transaction
                log.info("Enrollment created successfully");
                return enrollment;

            } catch (SQLException errorStmt) {
                driver.rollback();      // Rollback only statement errors
                log.error("Error inserting enrollment", errorStmt);
                throw errorStmt;
            }

        } catch (Exception e) {
            log.error("Transaction failed", e);
            throw new RuntimeException("Transaction failed", e);
        }
    }

    // ---------------------------------------------------------------
    //  FIND ALL
    // ---------------------------------------------------------------
    public List<Enrollment> findAll() {
        String sql = "SELECT id_alumno, id_modulo, fecha FROM matricula";
        List<Enrollment> list = new ArrayList<>();

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Enrollment(
                        null,
                        rs.getInt("id_alumno"),
                        rs.getInt("id_modulo"),
                        rs.getDate("fecha").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            log.error("Error retrieving all enrollments", e);
        }

        return list;
    }

    // ---------------------------------------------------------------
    //  FIND BY STUDENT
    // ---------------------------------------------------------------
    public List<Enrollment> findByStudent(int studentId) {
        String sql = "SELECT id_alumno, id_modulo, fecha FROM matricula WHERE id_alumno = ?";
        List<Enrollment> list = new ArrayList<>();

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Enrollment(
                        null,
                        rs.getInt("id_alumno"),
                        rs.getInt("id_modulo"),
                        rs.getDate("fecha").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            log.error("Error finding enrollments for student {}", studentId, e);
        }

        return list;
    }

    // ---------------------------------------------------------------
    //  DELETE
    // ---------------------------------------------------------------
    public boolean delete(int studentId, int moduleId) {
        String sql = "DELETE FROM matricula WHERE id_alumno = ? AND id_modulo = ?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, moduleId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error("Error deleting enrollment", e);
            return false;
        }
    }

    // ---------------------------------------------------------------
    //  CALL STORED FUNCTION: count_enrollments
    // ---------------------------------------------------------------
    public int countEnrollments(int studentId) {

        String call = "{ ? = call count_enrollments(?) }";

        try (Connection con = driver.getConnection();
             CallableStatement cs = con.prepareCall(call)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, studentId);

            cs.execute();
            return cs.getInt(1);

        } catch (SQLException e) {
            log.error("Error calling count_enrollments for student {}", studentId, e);
            throw new RuntimeException(e);
        }
    }
}
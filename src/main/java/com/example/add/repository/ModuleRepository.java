package com.example.add.repository;

import com.example.add.config.PostgresqlDriver;
import com.example.add.model.Module;
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
public class ModuleRepository {

    private final PostgresqlDriver driver;

    // -------------------------------
    // CREATE
    // -------------------------------
    public Module insert(Module module) {
        String sql = "INSERT INTO modulo (codigo, nombre, horas) VALUES (?, ?, ?) RETURNING id_modulo";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, module.getCode());
            stmt.setString(2, module.getName());
            stmt.setInt(3, module.getHours());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                module.setId(rs.getInt(1));
            }

            return module;

        } catch (SQLException e) {
            log.error("Error inserting module", e);
            throw new RuntimeException(e);
        }
    }

    // -------------------------------
    // FIND ALL
    // -------------------------------
    public List<Module> findAll() {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo";
        List<Module> modules = new ArrayList<>();

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Module m = new Module();
                m.setId(rs.getInt("id_modulo"));
                m.setCode(rs.getString("codigo"));
                m.setName(rs.getString("nombre"));
                m.setHours(rs.getInt("horas"));
                modules.add(m);
            }

        } catch (SQLException e) {
            log.error("Error finding all modules", e);
        }

        return modules;
    }

    // -------------------------------
    // FIND BY ID
    // -------------------------------
    public Module findById(int id) {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE id_modulo = ?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Module(
                        rs.getInt("id_modulo"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("horas")
                );
            }

        } catch (SQLException e) {
            log.error("Error finding module by id", e);
        }

        return null;
    }

    // -------------------------------
    // FIND BY CODE: Para comprobar si el módulo ya existe
    // -------------------------------
    public Module findByCode(String code) {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE codigo = ?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Module(
                        rs.getInt("id_modulo"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("horas")
                );
            }

        } catch (Exception e) {
            log.error("Error finding module by code", e);
        }

        return null;
    }

    // -------------------------------
    // UPDATE
    // -------------------------------
    public Module update(Module module) {
        String sql = "UPDATE modulo SET codigo=?, nombre=?, horas=? WHERE id_modulo=?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, module.getCode());
            stmt.setString(2, module.getName());
            stmt.setInt(3, module.getHours());
            stmt.setInt(4, module.getId());

            stmt.executeUpdate();
            return module;

        } catch (SQLException e) {
            log.error("Error updating module", e);
            throw new RuntimeException(e);
        }
    }

    // -------------------------------
    // DELETE
    // -------------------------------
    public boolean delete(int id) {
        String sql = "DELETE FROM modulo WHERE id_modulo=?";

        try (Connection con = driver.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error("Error deleting module", e);
            return false;
        }
    }
}

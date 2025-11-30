package com.example.add.repository;

import com.example.add.model.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ModuleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Module> rowMapper = (rs, rowNum) ->
            new Module(
                    rs.getInt("id_modulo"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("horas")
            );

    // INSERT con RETURNING para PostgreSQL
    public Module insert(Module module) {
        String sql = "INSERT INTO modulo (codigo, nombre, horas) VALUES (?, ?, ?) RETURNING id_modulo";

        Integer newId = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                module.getCode(),
                module.getName(),
                module.getHours()
        );

        module.setId(newId);
        return module;
    }

    public List<Module> findAll() {
        return jdbcTemplate.query(
                "SELECT id_modulo, codigo, nombre, horas FROM modulo",
                rowMapper
        );
    }

    public Module findById(int id) {
        List<Module> result = jdbcTemplate.query(
                "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE id_modulo = ?",
                rowMapper,
                id
        );
        return result.isEmpty() ? null : result.get(0);
    }

    public Module findByCode(String code) {
        List<Module> result = jdbcTemplate.query(
                "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE codigo = ?",
                rowMapper,
                code
        );
        return result.isEmpty() ? null : result.get(0);
    }

    public Module update(Module module) {
        jdbcTemplate.update(
                "UPDATE modulo SET codigo=?, nombre=?, horas=? WHERE id_modulo=?",
                module.getCode(),
                module.getName(),
                module.getHours(),
                module.getId()
        );
        return module;
    }

    public boolean delete(int id) {
        return jdbcTemplate.update(
                "DELETE FROM modulo WHERE id_modulo=?",
                id
        ) > 0;
    }
}

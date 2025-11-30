package com.example.add.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initScripts() {
        runScript("sql/01_schema.sql");
        runScript("sql/02_procedures.sql");
        runScript("sql/03_sample_data.sql");
    }

    private void runScript(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);

            if (!resource.exists()) {
                log.warn("Script no encontrado: {}", path);
                return;
            }

            String sql = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            jdbcTemplate.execute(sql);

            log.info("Script ejecutado correctamente: {}", path);

        } catch (Exception e) {
            log.error("Error ejecutando script {}: {}", path, e.getMessage());
        }
    }
}

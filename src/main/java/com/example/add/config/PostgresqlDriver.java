package com.example.add.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PostgresqlDriver {

    // ------------------------------
    // CONFIGURACIÓN JDBC
    // ------------------------------
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name:org.postgresql.Driver}")
    private String driverClassName;

    // ------------------------------
    // CARGA DE SCRIPTS SQL
    // ------------------------------
    @Value("classpath*:sql/*.sql")
    private Resource[] scripts;
    private Connection transactionalConnection;

    // ------------------------------
    // INICIALIZACIÓN AUTOMÁTICA
    // ------------------------------
    @PostConstruct
    public void init() {
        log.info("Initializing database...");
        for (Resource script : scripts) {
            executeSql(script);
        }
        log.info("Database initialized successfully!");
    }

    // ------------------------------
    // EJECUCIÓN DE SQL INICIAL
    // ------------------------------
    private void executeSql(Resource resource) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

            String sql = reader.lines().collect(Collectors.joining("\n"));
            stmt.execute(sql);
            log.info("Executed script: {}", resource.getFilename());

        } catch (Exception e) {
            log.error("Error executing script {}: {}", resource.getFilename(), e.getMessage());
        }
    }


    // --------------------------------------------------
    // TRANSACCIONES MANUALES
    // --------------------------------------------------

    // ------------------------------
    // CONEXIÓN NORMAL JDBC
    // ------------------------------
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    public void beginTransaction() throws SQLException {
        transactionalConnection = DriverManager.getConnection(url, username, password);
        transactionalConnection.setAutoCommit(false);
        log.info("Transaction started");
    }

    public void commit() throws SQLException {
        if (transactionalConnection != null) {
            transactionalConnection.commit();
            transactionalConnection.close();
            transactionalConnection = null;
            log.info("Transaction committed");
        }
    }

    public void rollback() throws SQLException {
        if (transactionalConnection != null) {
            transactionalConnection.rollback();
            transactionalConnection.close();
            transactionalConnection = null;
            log.info("Transaction rolled back");
        }
    }

    public Connection getTransactionalConnection() {
        return transactionalConnection;
    }
}
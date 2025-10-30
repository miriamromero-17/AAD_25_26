package com.example.add;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Slf4j
public class PostgresqlDriver {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    // Si no se define, por defecto usamos el driver de PostgreSQL
    @Value("${spring.datasource.driver-classname:org.postgresql.Driver}")
    private String driverClassName;

    // @PostConstruct
    // public void init() {
    //     try {
    //         // Cargar driver explícitamente (seguro y compatible)
    //         Class.forName(driverClassName);
    //         System.out.println("JDBC driver loaded: " + driverClassName);
    //
    //         // Prueba rápida de conexión al arrancar (no estricto: solo informa)
    //         try (Connection conn = getConnection()) {
    //             if (conn != null && !conn.isClosed()) {
    //                 log.info("Database connection test successful");
    //             }
    //         } catch (SQLException ex) {
    //             log.error("Could not test DB connection at startup: {}", ex.getMessage());
    //         }
    //     } catch (ClassNotFoundException e) {
    //         log.error("JDBC Driver class not found: {}", driverClassName);
    //     } catch (Exception e) {
    //         log.error("Unexpected error initializing connector: {}", e.getMessage());
    //     }
    // }

    /**
     * Obtiene una nueva conexión JDBC usando DriverManager.
     * El llamador es responsable de cerrar la conexión (try-with-resources recomendado).
     *
     * @return nueva Connection abierta
     * @throws SQLException si falla la conexión
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

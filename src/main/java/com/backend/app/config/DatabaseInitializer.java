// Não tá 100%

package com.backend.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private final String dbName = "db";

    @EventListener(ApplicationPreparedEvent.class)
    public void initializeDatabase() {
        String connectionUrl = dbUrl.split("\\?")[0]; // Remove os parâmetros, se existirem

        try (Connection connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword)) {
            try (Statement statement = connection.createStatement()) {
                String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + dbName;
                statement.executeUpdate(createDatabaseSQL);
                System.out.println("Banco de dados " + dbName + " criado ou já existente.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar o banco de dados: " + e.getMessage(), e);
        }
    }
}

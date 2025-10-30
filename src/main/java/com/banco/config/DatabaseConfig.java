package com.banco.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final Properties props = new Properties();
    private static Connection connection;

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("config/database.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }
            props.load(input);
            logger.info("Database properties loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading database properties", e);
            throw new RuntimeException("Error loading database properties", e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(props.getProperty("MySQLJDBCDriver"));
                connection = DriverManager.getConnection(
                        props.getProperty("sistema_bancario"),
                        props.getProperty("root"),
                        props.getProperty(""));
                logger.info("Database connection established successfully");
            } catch (ClassNotFoundException e) {
                logger.error("MySQL JDBC Driver not found", e);
                throw new RuntimeException("MySQL JDBC Driver not found", e);
            } catch (SQLException e) {
                logger.error("Error connecting to database", e);
                throw new RuntimeException("Error connecting to database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.info("Database connection closed successfully");
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }
}
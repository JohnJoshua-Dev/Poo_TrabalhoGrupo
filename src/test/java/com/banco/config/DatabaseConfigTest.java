package com.banco.config;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConfigTest {

    @Test
    public void testDatabaseConnection() {
        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();
            assertNotNull(connection, "Database connection should not be null");
            assertTrue(connection.isValid(5), "Database connection should be valid");
        } catch (Exception e) {
            fail("Should be able to connect to database: " + e.getMessage());
        } finally {
            DatabaseConfig.closeConnection();
        }
    }
}
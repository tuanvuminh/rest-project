package com.backend.db;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for obtaining database connections using HikariCP connection pool.
 *
 * This class provides a method to obtain a connection from the HikariCP connection pool configured in the
 * HikariDBConfig class. It ensures a single shared instance of HikariDataSource is used across the application.
 */
public class DBConnectionV2 {

    private static final HikariDataSource dataSource = HikariDBConfig.getDataSource();

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private DBConnectionV2() {
    }

    /**
     * Retrieves a database connection from the HikariCP connection pool.
     *
     * @return A Connection object representing a database connection
     * @throws SQLException If a database access error occurs or the maximum pool size is reached
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

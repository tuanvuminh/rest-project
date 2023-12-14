package com.backend.db;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static final HikariDataSource dataSource = HikariDBConfig.getDataSource();

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

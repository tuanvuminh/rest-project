package com.backend.db;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionV2 {

    private static final HikariDataSource dataSource = HikariDBConfig.getDataSource();

    private DBConnectionV2() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

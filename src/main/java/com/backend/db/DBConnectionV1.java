package com.backend.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for obtaining database connections using Apache DBCP2 connection pool.
 *
 * This class provides a method to obtain a connection from the Apache DBCP2 connection pool configured based on the
 * properties loaded from the "database.properties" file. It ensures that the required JDBC driver is loaded during
 * class initialization.
 */
public class DBConnectionV1 {

    private static final String FILE_PATH = "/database.properties";
    private static final Properties props = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            InputStream fileInputStream = DBConnectionV1.class.getResourceAsStream(FILE_PATH);
            props.load(fileInputStream);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a database connection from the Apache DBCP2 connection pool.
     *
     * @return A Connection object representing a database connection
     * @throws RuntimeException If a SQLException occurs during the connection retrieval
     */
    public static Connection getConnection() {
        try {
            BasicDataSource dataSource = new BasicDataSource();

            dataSource.setUrl(requireNonNull(props.getProperty("url")));
            dataSource.setUsername(requireNonNull(props.getProperty("user")));
            dataSource.setPassword(requireNonNull(props.getProperty("password")));

            return dataSource.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

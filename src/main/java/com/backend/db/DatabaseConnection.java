package com.backend.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class DatabaseConnection {

    private static final String FILE_PATH = "/database.properties";
    private static final Properties props = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            InputStream fileInputStream = DatabaseConnection.class.getResourceAsStream(FILE_PATH);
            props.load(fileInputStream);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

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

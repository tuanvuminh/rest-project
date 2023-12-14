package com.backend.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HikariDBConfig {

    private static final String FILE_PATH = "/database.properties";
    private static HikariDataSource dataSource;

    private HikariDBConfig() {
    }

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig(loadProperties());
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    private static Properties loadProperties() {

        Properties properties = new Properties();
        try (InputStream input = HikariDBConfig.class.getResourceAsStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}

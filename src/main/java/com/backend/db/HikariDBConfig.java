package com.backend.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for configuring and providing a HikariDataSource for database connections.
 *
 * This class follows the Singleton pattern to ensure that a single instance of HikariDataSource is used across
 * the application. It reads database configuration properties from the "database.properties" file and creates a
 * HikariDataSource based on these properties.
 */
public class HikariDBConfig {

    private static final String FILE_PATH = "/database.properties";
    private static HikariDataSource dataSource;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private HikariDBConfig() {
    }

    /**
     * Retrieves a shared instance of HikariDataSource, creating it if necessary.
     *
     * @return The HikariDataSource instance configured for database connections
     */
    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig(loadProperties());
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    /**
     * Loads properties from a file.
     *
     * @return The loaded properties
     */
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

package com.backend.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.ResourceBundle;

/**
 * Utility class for configuring and providing a HikariDataSource for database connections.
 *
 * This class follows the Singleton pattern to ensure that a single instance of HikariDataSource is used across
 * the application. It reads database configuration properties from the "database.properties" file and creates a
 * HikariDataSource based on these properties.
 */
public class HikariDBConfig {

    private static final String BUNDLE_NAME = "database";
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
            HikariConfig config = loadProperties();
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    /**
     * Loads configuration from a file.
     *
     * @return The loaded properties
     */
    private static HikariConfig loadProperties() {

        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(bundle.getString("jdbcUrl"));
        config.setUsername(bundle.getString("username"));
        config.setPassword(bundle.getString("password"));
        config.setDriverClassName(bundle.getString("driverClassName"));
        config.setMaximumPoolSize(Integer.parseInt(bundle.getString("maximumPoolSize")));
        config.setConnectionTimeout(Integer.parseInt(bundle.getString("connectionTimeout")));
        config.setIdleTimeout(Integer.parseInt(bundle.getString("idleTimeout")));

        return config;
    }
}

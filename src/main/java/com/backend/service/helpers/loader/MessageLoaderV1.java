package com.backend.service.helpers.loader;

import com.backend.consts.RESTMessages;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading messages from a properties file.
 *
 * This class provides methods to retrieve messages based on keys from a properties file. It uses the provided
 * `RESTMessages` enum to identify the keys and fetches the corresponding messages from the "messages.properties" file.
 * The properties are loaded only once during the initialization of the class, following the Singleton pattern.
 */
@Default
@RequestScoped
public class MessageLoaderV1 {

    private static final String FILE_PATH = "/messages.properties";
    private Properties props;

    /**
     * Constructs a new Message instance and loads properties from the "messages.properties" file.
     */
    public MessageLoaderV1() {
        this.props = loadProperties();
    }

    /**
     * Loads properties from a file.
     *
     * @return The loaded properties
     */
    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream fileInputStream = MessageLoaderV1.class.getResourceAsStream(FILE_PATH)) {
            props.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * Retrieves the message associated with the given key from the properties file.
     *
     * @param message The key representing the message in the properties file
     * @return The message associated with the key, or null if the key is not found
     */
    public String get(RESTMessages message) {
        return props.getProperty(String.valueOf(message));
    }

//    private static final String FILE_PATH = "/messages.properties";
//    private static Properties properties;
//
//    static {
//        properties = loadProperties();
//    }
//
//    private static Properties loadProperties() {
//        Properties props = new Properties();
//        try (InputStream fileInputStream = Message.class.getResourceAsStream(FILE_PATH)) {
//            props.load(fileInputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return props;
//    }
//
//    public String get(RESTMessages message) {
//        return properties.getProperty(String.valueOf(message));
//    }
}

package com.backend.service.loader;

import com.backend.consts.RESTMessages;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Default
@RequestScoped
public class Message {

    private static final String FILE_PATH = "/messages.properties";
    private Properties properties;

    public Message() {
        this.properties = loadProperties();
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream fileInputStream = Message.class.getResourceAsStream(FILE_PATH)) {
            props.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public String get(RESTMessages message) {
        return properties.getProperty(String.valueOf(message));
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

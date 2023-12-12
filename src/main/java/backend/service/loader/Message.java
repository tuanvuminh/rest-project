package backend.service.loader;

import backend.consts.RESTMessages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Message {

    private static final String FILE_PATH = "C:\\Users\\w137337\\IdeaProjects\\rest-project\\src\\main\\resources\\messages.properties";
    private static final Properties props;

    static {
        props = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH)) {
            props.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized String get(RESTMessages message) {
        return props.getProperty(String.valueOf(message));
    }

//    private static final String FILE_PATH = "C:\\Users\\w137337\\IdeaProjects\\rest-project\\src\\main\\messages.properties";
//    private final Properties properties;
//
//    public Message() {
//        this.properties = loadProperties(FILE_PATH);
//    }
//
//    private Properties loadProperties(String filePath) {
//        Properties props = new Properties();
//        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
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

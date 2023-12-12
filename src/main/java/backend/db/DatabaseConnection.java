package backend.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class DatabaseConnection {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection() {

        Properties props = new Properties();

        try {
            props.load(new FileInputStream("C:\\Users\\w137337\\IdeaProjects\\rest-project\\data\\database.properties"));

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(requireNonNull(props.getProperty("url")));
            dataSource.setUsername(requireNonNull(props.getProperty("user")));
            dataSource.setPassword(requireNonNull(props.getProperty("password")));

            return dataSource.getConnection();

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

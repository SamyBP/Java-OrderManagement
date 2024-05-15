package utcn.ordermanagement.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final String APPLICATION_PROPERTIES_FILE = "D:\\MyWorkSpace\\MyProjects\\OrderManagement\\OrderManagement\\src\\main\\resources\\application.properties";
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(APPLICATION_PROPERTIES_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadUser() {
        return properties.getProperty("db.user");
    }

    public static String loadPassword() {
        return properties.getProperty("db.password");
    }

    public static String loadURL() {
        return properties.getProperty("db.url");
    }

    public static String loadDriver() {
        return properties.getProperty("db.driver");
    }
}

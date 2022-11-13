package at.kaindorf.lernen_School.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DB_Properties {
    private static final Properties PROPS = new Properties();

    static {
        Path path = Paths.get("src/main/resources/database.properties");

        try {
            FileInputStream fis = new FileInputStream(path.toFile());
            PROPS.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return PROPS.getProperty(key);
    }
}

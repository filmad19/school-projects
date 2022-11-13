package at.kaindorf.lernen_books.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DB_Properties {
    private static Properties PROPS = new Properties();

    static{
        Path path = Paths.get("src/main/resources/database.properties");
        try {
            FileInputStream fis = new FileInputStream(path.toFile());
            PROPS.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return PROPS.getProperty(key);
    }
}

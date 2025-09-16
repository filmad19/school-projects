package at.kaindorf.xml;

import jakarta.xml.bind.JAXB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class FileAccess {

    private static FileAccess instance;

    private FileAccess() {
        readCountries();
    }

    public synchronized static FileAccess getInstance() {
        if(instance == null) {
            instance = new FileAccess();
        }

        return instance;
    }

    /////////////////////////////////////////////


    private List<String> countries;

    public void readCountries() {
        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "countries.txt");

        try {
            countries = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String getNextCountry() {
        if (countries.size() <= 0) return null;

        String country = countries.remove(0);

        return country;
    }

    public synchronized XMLTempData readXMLTempData() {
        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "city_temperatures.xml");

        return JAXB.unmarshal(path.toFile(), XMLTempData.class);
    }
}

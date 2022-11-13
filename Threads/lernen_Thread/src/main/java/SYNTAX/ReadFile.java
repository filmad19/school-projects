package SYNTAX;

import beans.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadFile {
    public static List<Person> readPersons() throws IOException {
        Path path = Paths.get("src/main/resources/personPassword.csv");

        return Files.readAllLines(path)
                .stream()
                .skip(1)
//                .mapToInt(Integer::valueOf)
                .map(Person::new)
                .toList();
    }
}

package at.kaindorf.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Access {

    private static final Path INPUT_ROOT_FOLDER = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "hiking");

    public static List<File> findFilesToRead() {
        try {
            return Files.walk(INPUT_ROOT_FOLDER, 1)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xml"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

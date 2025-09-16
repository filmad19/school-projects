package at.kaindorf.observer;

import at.kaindorf.pojos.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PrintObserver extends Observer {

    private static final Path EXPORT_FILE_PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "out.log");

    static {
        try {
            Files.deleteIfExists(EXPORT_FILE_PATH);
            Files.createFile(EXPORT_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PrintObserver(Subject subject) {
        super(subject);
        subject.register(this);
    }

    @Override
    public void printResult(Result result) {
        try {
            String text = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(result) + ",\n";

            synchronized (EXPORT_FILE_PATH) {

                Files.writeString(EXPORT_FILE_PATH, text, StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

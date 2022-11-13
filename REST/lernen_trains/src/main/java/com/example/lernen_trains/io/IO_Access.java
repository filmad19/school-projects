package com.example.lernen_trains.io;

import com.example.lernen_trains.pojos.Train;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Access {
    private static final Path path = Paths.get("C:\\Users\\matze\\IdeaProjects\\4. Klasse\\lernen_trains\\src\\main\\resources\\trains.csv");

    public static List<Train> getTrainsFromCsv() throws IOException {
        return Files.readAllLines(path).stream()
                .skip(1)
                .map(Train::new)
                .collect(Collectors.toList());
    }
}

package at.kaindorf.io;

import at.kaindorf.bl.SortingAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class IO_Access {

    public static int outputNumber = 1;

    public static int[] readIntValuesFromFile(Path path) {

        try {
            return Arrays.stream(Files.readString(path)
                    .split(","))
                    .mapToInt(Integer::valueOf)
                    .toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printIntValuesToFile(int[] values, SortingAlgorithm sortingAlgorithm, Path path) {

        String fileName = "/primes_" + sortingAlgorithm.name() + "_" + outputNumber + ".txt";

        path = Path.of(path + fileName);


        try {
            Files.writeString(path, Arrays.toString(values));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        outputNumber++;
    }
}

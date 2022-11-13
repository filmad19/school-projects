package at.kaindorf.Theorie_Thread.Hashing_Cracking;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BruteForceLauncher {
    private static Path filePath = Paths.get("src/main/java/at/kaindorf/Theorie_Thread/Hashing_Cracking/passwd_file.csv");

    private List<Person> readPersons() throws IOException {
        return Files.readAllLines(filePath)
                .stream()
                .skip(1)
                .map(Person::new)
                .toList();
    }

    private void runWorker(){
        List<Person> personList = new ArrayList<>();

        try {
            personList = readPersons();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(8);
        List<BruteForceWorker> workers = new ArrayList<>();

        personList.forEach(p -> workers.add(new BruteForceWorker(p))); //TOLLLLL


        try {
            List<Future<String>> futures = pool.invokeAll(workers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    //5 zeichen; kleinbuchstaben und Ziffern; name + password in Hash; eine Person pro Thread
    public static void main(String[] args) {
        new BruteForceLauncher().runWorker();
    }
}

package LERNEN.Names_scores_22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NameLauncher {

    private static List<String> readFile() throws IOException {
        Path path = Paths.get("src/main/java/LERNEN/Names_scores_22/p022_names.txt");

        List<String> nameList = Arrays.stream(Files.readString(path)
                        .replace("\"", "")
                        .split(","))
                .sorted()
                .toList();

        return nameList;
    }

    private void runWorkers(){
        List<String> nameList = new ArrayList<>();

        try {
            nameList = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        INVOKE ALL
        ExecutorService pool = Executors.newFixedThreadPool(8);
        List<NameWorker> workerList = new ArrayList<>();

//        5000/500
        for (int i = 0; i < nameList.size(); i++) {
            workerList.add(new NameWorker(nameList.get(i), i+1));
        }

        try {
            List<Future<Long>> futures = pool.invokeAll(workerList);
            long solution = 0;

            for(Future<Long> f : futures){
                solution += f.get();
            }

            System.out.println(solution);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }


    public static void main(String[] args) {
        new NameLauncher().runWorkers();
    }
}

//871198282
//871198282
//870873746

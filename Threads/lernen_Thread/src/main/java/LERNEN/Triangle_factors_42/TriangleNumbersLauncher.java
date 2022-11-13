package LERNEN.Triangle_factors_42;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class TriangleNumbersLauncher {

    private List<String> readWords() throws IOException {
        Path path = Paths.get("src/main/java/LERNEN/Triangle_factors_42/p042_words.txt");

        return Arrays.stream(Files.readString(path)
                .replace("\"", "")
                .split(","))
                .toList();
    }

    private void runWorkers(){
        List<String> wordList = new ArrayList<>();

        try {
            wordList = readWords();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        COMPLETION SERVICE
        ExecutorService pool = Executors.newFixedThreadPool(8);
        CompletionService<Integer> service = new ExecutorCompletionService(pool);

//        2000 words; 200 pro Thread
        int wordsPerThread = 893;

        for (int i = 0; i < wordList.size(); i+=wordsPerThread) {
            if(i+wordsPerThread > wordList.size()){
                System.out.println(" X von " + i + " bis " + wordList.size());
                service.submit(new TriangleNumbersWorker(wordList.subList(i, wordList.size())));
                break;
            }
            service.submit(new TriangleNumbersWorker(wordList.subList(i, (i+wordsPerThread))));
            System.out.println(" von " + i + " bis " + ((i+wordsPerThread)));

        }

        pool.shutdown();

        int cnt = 0;
        for (int i = 0; i < wordList.size(); i+=wordsPerThread) {
            try {
                cnt += service.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(cnt);
    }


    public static void main(String[] args) {
        new TriangleNumbersLauncher().runWorkers();
    }
}

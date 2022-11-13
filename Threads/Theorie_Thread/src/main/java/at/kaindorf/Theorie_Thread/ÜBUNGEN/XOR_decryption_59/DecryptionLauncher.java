package at.kaindorf.Theorie_Thread.ÜBUNGEN.XOR_decryption_59;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DecryptionLauncher {

    public void runWorkers() throws IOException {
//        COMMON ENGLISH WORDS
        String[] commonEnglishWords = {"that", "was", "with", "they", "this", "have", "from", "word", "what", "were"};

//        READ FILE
        int[] file = Arrays.stream(Files.readString(Path.of("src/main/java/at/kaindorf/Theorie_Thread/ÜBUNGEN/XOR_decryption_59/p059_cipher.txt"))
                .split(","))
                .mapToInt(Integer::valueOf)
                .toArray();

        DecryptionWorker.setCommonEnglishWords(commonEnglishWords);
        DecryptionWorker.setFile(file);

        ExecutorService pool = Executors.newFixedThreadPool(8); //      26 Threads von aaa - azz --> zaa - zzz
        List<Callable<String>> workers = new ArrayList<>();

        for(char c = 'a'; c <= 'z'; c++){
            workers.add(new DecryptionWorker(c));
        }

        try {
            String decryptedText = pool.invokeAny(workers);
//            System.out.println(decryptedText);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    public static void main(String[] args) {
        try {
            new DecryptionLauncher().runWorkers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

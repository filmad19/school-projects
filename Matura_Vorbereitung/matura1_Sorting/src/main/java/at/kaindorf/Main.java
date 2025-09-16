package at.kaindorf;

import at.kaindorf.bl.CustomSorter;
import at.kaindorf.bl.MathHelper;
import at.kaindorf.bl.SortingAlgorithm;
import at.kaindorf.io.IO_Access;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class Main {

    private static final Path inputPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "numbers_small.csv");
//    private static final Path inputPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "numbers_original.txt");
    private static final Path outputPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "out");

    private static ExecutorService pool = MyThreadPool.getInstance().getCachedPool();

    private static boolean programStopped = false;
    private static Integer runningTasks = 0;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (!pool.isShutdown()) {

            System.out.println("""
                    1 - INSERTION
                    2 - BUBBLE
                    3 - SELECTION
                    4 - QUICK
                    5 - EXIT
                    °-^-^-^-^-^-^-^-^-^-^-°
                    """);

            switch (scanner.nextInt()) {
                case 1 -> runPipeline(SortingAlgorithm.INSERTION);
                case 2 -> runPipeline(SortingAlgorithm.BUBBLE);
                case 3 -> runPipeline(SortingAlgorithm.SELECTION);
                case 4 -> runPipeline(SortingAlgorithm.QUICK);
                default -> {
                    programStopped = true;

                    synchronized (runningTasks) {
                        if(runningTasks == 0) pool.shutdown();
                    }

                    return;
                }
            }
        }
    }

    private static void runPipeline(SortingAlgorithm sortingAlgorithm) {

        synchronized (runningTasks) {
            runningTasks++;
        }

        LocalDateTime startTime = LocalDateTime.now();

        CompletableFuture.supplyAsync(() -> IO_Access.readIntValuesFromFile(inputPath), pool)
                .thenApplyAsync(values -> switch (sortingAlgorithm) {
                    case INSERTION -> CustomSorter.insertionSort(values);
                    case BUBBLE -> CustomSorter.bubbleSort(values);
                    case SELECTION -> CustomSorter.selectionSort(values);
                    case QUICK -> CustomSorter.quickSort(values);
                }, pool)
                .thenApplyAsync(MathHelper::removeDuplicates, pool)
                .thenApplyAsync(MathHelper::extractPrimes, pool)
                .thenAcceptAsync(values -> IO_Access.printIntValuesToFile(values, sortingAlgorithm, outputPath), pool)
                .thenAcceptAsync(x -> {
                    LocalDateTime endTime = LocalDateTime.now();
                    Duration duration = Duration.between(startTime, endTime);

                    System.out.printf("%s took %02d:%02d:%03d\n\n",
                            sortingAlgorithm.name(),
                            duration.toMinutesPart(),
                            duration.toSecondsPart(),
                            duration.toMillisPart()
                    );

                    synchronized (runningTasks) {
                        runningTasks--;
                        if(programStopped && runningTasks == 0) pool.shutdown();
                    }
                }, pool);
    }
}
package at.kaindorf;

import at.kaindorf.calculation.DensityMonteCarlo;
import at.kaindorf.calculation.StatisticsCalculator;
import at.kaindorf.calculation.VolumesMonteCarlo;
import at.kaindorf.io.IO_Access;
import at.kaindorf.pojos.GoldMuenze;
import at.kaindorf.pojos.Measurement;
import at.kaindorf.pojos.Muenzen;
import at.kaindorf.thread.MyThreads;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class Main {

    private static final Path path1 = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data1.xml");
    private static final Path path2 = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data2.xml");

    private static boolean programmStopped = false;
    private static Integer tasksRunning = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ExecutorService pool = MyThreads.getInstance().getPool();

        IO_Access<GoldMuenze> ioAccessGoldMuenze = new IO_Access<>();
        IO_Access<Muenzen> ioAccessMuenzen = new IO_Access<>();

        while (!pool.isShutdown()) {

            System.out.println("""
                    1 - radius
                    2 - height
                    3 - volumes monte carlo
                    4 - density monte carlo
                    -----------------------
                    """);

            synchronized (tasksRunning) {
                tasksRunning++;
            }

            switch (scanner.nextInt()) {
                case 1 -> CompletableFuture.supplyAsync(() -> ioAccessGoldMuenze.readData(path1, GoldMuenze.class), pool)
                        .thenApplyAsync(measurement -> measurement.getMeasurementList().stream().mapToDouble(Measurement::getRadius).toArray(), pool)
                        .thenApplyAsync(StatisticsCalculator::computeStatistics, pool)
                        .thenAcceptAsync(statistics -> {
                            System.out.println("RADIUS = " + statistics);

                            synchronized (tasksRunning) {
                                tasksRunning--;

                                if(programmStopped && tasksRunning == 0) pool.shutdown();
                            }
                        }, pool);
                case 2 -> CompletableFuture.supplyAsync(() -> ioAccessGoldMuenze.readData(path1, GoldMuenze.class), pool)
                        .thenApplyAsync(measurement -> measurement.getMeasurementList().stream().mapToDouble(Measurement::getHeight).toArray(), pool)
                        .thenApplyAsync(StatisticsCalculator::computeStatistics, pool)
                        .thenAcceptAsync(statistics -> {
                            System.out.println("HEIGHT = " + statistics);

                            synchronized (tasksRunning) {
                                tasksRunning--;

                                if(programmStopped && tasksRunning == 0) pool.shutdown();
                            }
                        }, pool);
                case 3 -> CompletableFuture.supplyAsync(() -> ioAccessGoldMuenze.readData(path1, GoldMuenze.class), pool)
                        .thenApplyAsync(measurement -> VolumesMonteCarlo.computeRandomVolumes(measurement.getMeasurementList(), 1_000_000), pool)
                        .thenApplyAsync(StatisticsCalculator::computeStatistics, pool)
                        .thenAcceptAsync(statistics -> {
                            System.out.println("VOLUMES = " + statistics);

                            synchronized (tasksRunning) {
                                tasksRunning--;

                                if(programmStopped && tasksRunning == 0) pool.shutdown();
                            }
                        }, pool);
                case 4 -> CompletableFuture.supplyAsync(() -> ioAccessMuenzen.readData(path2, Muenzen.class), pool)
                        .thenApplyAsync(muenzen -> DensityMonteCarlo.computeRandomDensities(muenzen, 1_000_000), pool)
                        .thenApplyAsync(StatisticsCalculator::computeStatistics, pool)
                        .thenAcceptAsync(statistics -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("DENSITY = " + statistics);

                            synchronized (tasksRunning) {
                                tasksRunning--;

                                if(programmStopped && tasksRunning == 0) pool.shutdown();
                            }
                        }, pool);
                default -> {
                    programmStopped = true;

                    synchronized (tasksRunning){
                        tasksRunning--;
                        if(tasksRunning == 0) pool.shutdown();
                    }

                    return;
                }
            }
        }
//        List<Measurement> measurementList = ioAccessGoldMuenze.readData(path1, GoldMuenze.class).getMeasurementList();
//
//
//        Muenzen muenzen = ioAccessMuenzen.readData(path2, Muenzen.class);
//
//
//        Statistics statistics = StatisticsCalculator.computeStatistics(measurementList.stream().mapToDouble(Measurement::getRadius).toArray());
//
//        double[] volumes = VolumesMonteCarlo.computeRandomVolumes(measurementList, 100_000);
//        Statistics volumesStatistics = StatisticsCalculator.computeStatistics(volumes);
//
//        double[] densities = DensityMonteCarlo.computeRandomDensities(muenzen, 100_000);
//        Statistics densityStatistics = StatisticsCalculator.computeStatistics(densities);
//
//        System.out.println("12asd");
    }
}
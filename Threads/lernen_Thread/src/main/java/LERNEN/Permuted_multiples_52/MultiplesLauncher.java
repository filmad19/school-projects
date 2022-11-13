package LERNEN.Permuted_multiples_52;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiplesLauncher {

    private void runWorkers(){
        int numbersPerThread = 1000;

        ExecutorService pool = Executors.newFixedThreadPool(8);
        List<MultiplesWorker> workerList = new ArrayList<>();

        for (int i = 0; i < 200_000/numbersPerThread; i++) {
            workerList.add(new MultiplesWorker(i*numbersPerThread+1, (i+1) * numbersPerThread));
        }

        try {
            int result = pool.invokeAny(workerList);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    public static void main(String[] args) {
        new MultiplesLauncher().runWorkers();
    }
}

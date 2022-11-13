package at.kaindorf.Theorie_Thread.ÜBUNGEN.cubic_permutations_62;

import at.kaindorf.Theorie_Thread.ÜBUNGEN.permuted_multiples_52.MultiplesWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PermutationsLauncher {

    public void runWorkers(){
        ExecutorService pool = Executors.newFixedThreadPool(8);
        List<PermutationWorker> workerList = new ArrayList<>();

        int numbersPerThread = 500;

        for (int i = 0; i < 100_000; i+=numbersPerThread) {
            workerList.add(new PermutationWorker(i, i+numbersPerThread-1));
        }

        try {
            int solution = pool.invokeAny(workerList);

            System.out.println(solution);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    public static void main(String[] args) {
        new PermutationsLauncher().runWorkers();
    }
}

package LERNEN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LauncherLernen {

    private void runWorkers(){
//        INVOKE ALL
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<WorkerLernen> workerLernenList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            workerLernenList.add(new WorkerLernen());
        }

        try {
            List<Future<List<String>>> futureList = pool.invokeAll(workerLernenList);
            List<List<String>> solutions = new ArrayList<>();

            for(Future<List<String>> f : futureList){
                solutions.add(f.get());
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

//        COMPLETION SERVICE
//        ExecutorService pool = Executors.newFixedThreadPool(4);
//        CompletionService<List<String>> service = new ExecutorCompletionService(pool);
//
//        for (int i = 0; i < 10; i++) {
//            service.submit(new WorkerLernen());
//        }
//        pool.shutdown();
//
//        for (int i = 0; i < 10; i++) {
//            try {
//                List<String> solution = service.take().get();
//                System.out.println(solution);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void main(String[] args) {
        new LauncherLernen().runWorkers();
    }
}

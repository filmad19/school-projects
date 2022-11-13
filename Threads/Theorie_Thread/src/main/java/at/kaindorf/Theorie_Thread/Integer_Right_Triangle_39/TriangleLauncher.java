package at.kaindorf.Theorie_Thread.Integer_Right_Triangle_39;

import java.util.Set;
import java.util.concurrent.*;

public class TriangleLauncher {

    public void runWorkers(){
        ExecutorService pool = Executors.newFixedThreadPool(8); //pool wo  MAX. 4 Threads gleichzeitig laufen können
        CompletionService<Set<Triangle>> service = new ExecutorCompletionService<>(pool); // signatur con CALL-METHODE pool übergeben

        for (int i = 10;i <= 1000;i++){
            service.submit(new TriangleWorker(i));
        }
        pool.shutdown(); //danach darf kein Callable mehr hineinsubmitet werden

        Set<Triangle> largestTriangleSet = null;

        for (int i = 10;i <= 1000;i++){
            try {
                Future<Set<Triangle>> future = service.take(); //liefert Ergebnisse an future
                Set<Triangle> triangleSet = future.get();
                if(largestTriangleSet == null || largestTriangleSet.size() < triangleSet.size()){
                    largestTriangleSet = triangleSet;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(largestTriangleSet);
    }

    public static void main(String[] args) {
         new TriangleLauncher().runWorkers();
    }
}

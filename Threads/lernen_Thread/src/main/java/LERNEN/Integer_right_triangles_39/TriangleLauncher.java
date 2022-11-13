package LERNEN.Integer_right_triangles_39;

import java.util.*;
import java.util.concurrent.*;

public class TriangleLauncher {

    private void runWorkers(){
        ExecutorService pool = Executors.newFixedThreadPool(8);
        CompletionService<List<Set<Triangle>>> service = new ExecutorCompletionService<>(pool);

        int perimetersPerThread = 100;
        int highestPerimeter = 1000;

        for (int i = 0; i < highestPerimeter/perimetersPerThread; i++) {
            service.submit(new TriangleWorker(i * perimetersPerThread +1, (i+1) * perimetersPerThread));
        }

        pool.shutdown();
        Set<Triangle> perimeterWithMostSolutions = new HashSet<>();

        for (int i = 0; i < highestPerimeter/perimetersPerThread; i++) {

            try {
                List<Set<Triangle>> allTrianglesList = service.take().get();

                for(Set<Triangle> s : allTrianglesList){
                    if(s.size() > perimeterWithMostSolutions.size()){
                        perimeterWithMostSolutions = s;
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        perimeterWithMostSolutions.forEach(System.out::println);
    }



    public static void main(String[] args) {
        new TriangleLauncher().runWorkers();
    }
}

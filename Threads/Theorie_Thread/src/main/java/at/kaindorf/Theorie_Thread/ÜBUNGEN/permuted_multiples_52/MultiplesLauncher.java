package at.kaindorf.Theorie_Thread.ÜBUNGEN.permuted_multiples_52;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiplesLauncher {

    public void runWorkers(){
        ExecutorService pool = Executors.newFixedThreadPool(5); //pool wo  MAX. 5 Threads gleichzeitig laufen können
        List<Callable<Integer>> workerList = new ArrayList<>(); //Liste von allen Threads

        for (int i = 1;i <= 2_000_000;i++){
            workerList.add(new MultiplesWorker(i)); // added Threads in Liste
        }

        try{
            Integer result = pool.invokeAny(workerList); //result = Wenn Thread etwas anderes als eine Exception zurückgibt
            System.out.println(result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

//    OLD runWorkers
//        public void runWorkers(){
//            ExecutorService pool = Executors.newFixedThreadPool(5); //pool wo  MAX. 5 Threads gleichzeitig laufen können
//            CompletionService<Integer> service = new ExecutorCompletionService<>(pool); // signatur von CALL-METHODE + pool übergeben
//
//            for (int i = 1;i <= 200_000;i++){
//                service.submit(new MultiplesWorker(i));
//            }
//
//            pool.shutdown(); //pool ausschalten damit Werte aufgewertet werden können
//
//            for (int i = 1;i <= 200_000;i++){
//                try {
//                    Future<Integer> future = service.take(); //liefert Ergebnisse an future + signatur von CALL-METHODE
//                    int value = future.get();
//                    if(value != 0){
//                        System.out.println(value);
//                        return;
//                    }
//
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }


    public static void main(String[] args) {
        new MultiplesLauncher().runWorkers();
    }
}

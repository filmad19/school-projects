package SYNTAX;

import beans.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Launcher {

    private void runWorkers(){
//--------------------------------------------
//        READ FILE
        List<Person> personList = new ArrayList<>();
        try {
            personList = ReadFile.readPersons();
        } catch (IOException e) {
            e.printStackTrace();
        }

//--------------------------------------------
//        NORMAL THREAD = RUNNABLE
        WorkerRunnable worker = new WorkerRunnable();
        Thread thread = new Thread(worker);

        thread.start();

//--------------------------------------------
//        SPECIAL CODE
        try {
            personList.wait(); //wartet bis notified wird
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    //
        synchronized (personList){ //nur 1 Thread kann auf Liste zugreifen

        }
    //
        personList.notify(); //weckt einen Thread auf
        personList.notifyAll(); //weckt alle Threads auf
    //
        try {
            Thread.sleep(100); //schickt DIESEN Thread schlafen für 100 millisec
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    //
        try {
            thread.join(); //aktueller Thread (Main) wartet bis thread gestorben is
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    //
        StringBuffer stringBuffer = new StringBuffer(10); //ist Threadsafe
    //
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    //
//--------------------------------------------
//        CALLABLE:

//        COMPLETION SERVICE
        ExecutorService pool1 = Executors.newFixedThreadPool(4); //pool1 wo  MAX. 4 Threads gleichzeitig laufen können
        CompletionService<String> service = new ExecutorCompletionService<>(pool1); // signatur con CALL-METHODE pool1 übergeben

        for (int i = 0; i < 10; i++) {
            service.submit(new WorkerCallable(5));
        }
        pool1.shutdown();

        for (int i = 0; i < 10; i++) {
            try {
//                Future<String> future = service.take();
                String result = service.take().get().toString(); //liefert Ergebnis
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

////--------------------------------------------
//        INVOKE ANY / INVOKE ALL
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Callable<String>> workerList = new ArrayList<>(); //Liste von allen Callable Objekten

        for (int i = 0; i < 10; i++) {
            workerList.add(new WorkerCallable(5));  // added Threads in Liste
        }

        try {
            //INVOKE ANY
            String result = pool.invokeAny(workerList); //result = Wenn Thread etwas anderes als eine Exception zurückgibt
            System.out.println(result);
            ////--------------------------------------------



            //INVOKE ALL
            List<Future<String>> result2 = pool.invokeAll(workerList);
            List<String> result2Final = new ArrayList<>();

            for(Future<String> r : result2){
                result2Final.add(r.get());
            }
            result2Final.forEach(System.out::println);
            ////--------------------------------------------


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();

////--------------------------------------------
//        PRO PERSON EINEN THREAD
        List<Callable<String>> workerList2 = new ArrayList<>();

        personList.forEach(p -> workerList2.add(new WorkerCallable(5)));

    }




    public static void main(String[] args) {
        new Launcher().runWorkers();
    }
}

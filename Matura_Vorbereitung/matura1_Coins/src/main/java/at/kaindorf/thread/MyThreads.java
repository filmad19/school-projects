package at.kaindorf.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MyThreads {

    private static MyThreads instance;

    private MyThreads() {
    }

    public static MyThreads getInstance() {
        if(instance == null) {
            instance = new MyThreads();
        }

        return instance;
    }

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public ExecutorService getPool() {
        return pool;
    }
}

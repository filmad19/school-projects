package at.kaindorf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MyThreadPool {
    private static MyThreadPool instance;

    private MyThreadPool() {

    }

    public synchronized static MyThreadPool getInstance() {
        if(instance == null) {
            instance = new MyThreadPool();
        }

        return instance;
    }
    ////////////////////////////////////////

    public ExecutorService getCachedPool() {
        return Executors.newCachedThreadPool();
    }
}

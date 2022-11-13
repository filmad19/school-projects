package SYNTAX;

import java.util.concurrent.Callable;

public class WorkerCallable implements Callable<String> {
    private int number;

    public WorkerCallable(int number) {
        this.number = number;
    }

    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
}

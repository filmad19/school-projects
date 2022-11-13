package LERNEN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class WorkerLernen implements Callable<List<String> > {


    @Override
    public List<String> call() throws Exception {
        String text = "";

        for (int i = 0; i < 10; i++) {
            text +=  "" + i;
        }

        List<String> list = new ArrayList<>();
        list.add(text);
        list.add(Thread.currentThread().getName());

        return list;
    }
}

package LERNEN.Names_scores_22;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class NameWorker implements Callable<Long> {
    private String name;
    private int startIndexFromList;


    public NameWorker(String name, int startIndexFromList) {
        this.name = name;
        this.startIndexFromList = startIndexFromList;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0;

        char[] letters = name.toCharArray();

        for(char c : letters){
            sum += ((int) c-64);
        }

        return sum*startIndexFromList;
    }

    public static void main(String[] args) {
        NameWorker worker = new NameWorker("ZORAIDA", 5161);

        try {
            System.out.println(worker.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

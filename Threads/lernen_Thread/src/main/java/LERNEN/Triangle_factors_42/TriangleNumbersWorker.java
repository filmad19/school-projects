package LERNEN.Triangle_factors_42;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class TriangleNumbersWorker implements Callable<Integer> {
    private List<String> words;

    public TriangleNumbersWorker(List<String> words) {
        this.words = words;
    }

    @Override
    public Integer call() throws Exception {
        int cnt = 0;

        for(String word : words){
            char[] letters = word.toCharArray();
            int value = 0;

            for(char c : letters){
                value+=c-64;
            }

            if(isTriangleNumber(value)) {
//                System.out.println(word);
                cnt++;
            }
        }

        return cnt;
    }

    private Boolean isTriangleNumber(int value){
        int n = 0;
        int formulaValue;

        do{
            formulaValue = (int) (0.5 * n * (n + 1));
            n++;
        }while(formulaValue < value);

        if(formulaValue == value){
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("PROCEDURE");

        TriangleNumbersWorker worker = new TriangleNumbersWorker(list);
        try {
            System.out.println(worker.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

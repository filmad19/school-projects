package LERNEN.Permuted_multiples_52;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class MultiplesWorker implements Callable<Integer> {
    private int startNumber;
    private int endNumber;

    public MultiplesWorker(int startNumber, int endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }

    @Override
    public Integer call() throws Exception {
        for (int number = startNumber; number < endNumber; number++) {
            String originalNumberDigits = getSortedDigits(number);

            if(originalNumberDigits.equals(getSortedDigits(number*2)) &&
                    originalNumberDigits.equals(getSortedDigits(number*3)) &&
                            originalNumberDigits.equals(getSortedDigits(number*4)) &&
                                    originalNumberDigits.equals(getSortedDigits(number*5)) &&
                                            originalNumberDigits.equals(getSortedDigits(number*6))){
                   return number;
            }
        }
        throw new Exception("number not found!");
    }

    private String getSortedDigits(int number){
        int[] splitNumber = new int[7];

        int i = 0;
        while(number > 0){
            splitNumber[i] = number%10;
            number /= 10;
            i++;
        }

        Arrays.sort(splitNumber);

        String numberText = "";

        for(int j : splitNumber){
            numberText += "" + j;
        }
        return numberText;
    }

    public static void main(String[] args) {
        MultiplesWorker worker = new MultiplesWorker(1, 2);

        System.out.println(worker.getSortedDigits(125874));

        System.out.println(worker.getSortedDigits(125874*2));


        System.out.println(worker.getSortedDigits(125874).equals(worker.getSortedDigits(125874*2)));

        System.out.println("");
    }
}

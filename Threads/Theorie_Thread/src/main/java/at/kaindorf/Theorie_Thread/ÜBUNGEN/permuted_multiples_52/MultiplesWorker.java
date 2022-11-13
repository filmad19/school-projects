package at.kaindorf.Theorie_Thread.ÜBUNGEN.permuted_multiples_52;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

public class MultiplesWorker implements Callable<Integer> {
    private int number;

    public MultiplesWorker(int number) {
        this.number = number;
    }

    private Set<Integer> getDigitsFromNumber(int number){
        Set<Integer> digits = new TreeSet<>();

        int i = 0;
        while(number > 0){
            digits.add(number%10);
            number = number/10;
        }
        return digits;
    }

    @Override
    public Integer call() throws Exception {
        Set<Integer> correctDigits = getDigitsFromNumber(number);

        if(getDigitsFromNumber(number*2).containsAll(correctDigits)&&
           getDigitsFromNumber(number*3).containsAll(correctDigits) &&
           getDigitsFromNumber(number*4).containsAll(correctDigits) &&
           getDigitsFromNumber(number*5).containsAll(correctDigits) &&
           getDigitsFromNumber(number*6).containsAll(correctDigits) &&
           getDigitsFromNumber(number*6).size() == correctDigits.size()){

            return number;
        }

        throw new Exception("value not found");
    }

    public static void main(String[] args) {
        MultiplesWorker worker = new MultiplesWorker(125874);
        try {
            System.out.println("numbers = " + worker.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

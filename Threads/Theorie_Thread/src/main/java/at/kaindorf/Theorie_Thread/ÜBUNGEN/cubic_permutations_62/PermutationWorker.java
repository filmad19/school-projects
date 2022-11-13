package at.kaindorf.Theorie_Thread.ÜBUNGEN.cubic_permutations_62;

import java.util.*;
import java.util.concurrent.Callable;

public class PermutationWorker implements Callable<Integer> {
    private int startNumber;
    private int endNumber;

    public PermutationWorker(int startNumber, int endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }

    //      - geh durch jede Zahl durch
//          - finde die ^3 von der Zahl
//          - finde alle permutations davon
//          - schau ob die 3. Wurzel auch eine ganze Zahl is
//              - schau ob GENAU 5 zahlen auf das zutreffen

    @Override
    public Integer call() throws Exception {

        for (int number = startNumber; number < endNumber; number++) {
            long cube = (long) Math.pow(number, 3);
            Set<Long> permutations = getAllPermutations(cube);
//            System.out.println("----------" + number);
//
//            for(long l : permutations){
//                System.out.println(l);
//            }
//
//            System.out.println("----------");

            int cnt = 0;
            for(long p : permutations){
                System.out.println(p + " " + Math.cbrt(p) + " = " + (double)Math.round(Math.cbrt(p)*10000)/10000 + " " + (int)Math.round(Math.cbrt(p)*10000)/10000);

                if(Math.round(Math.cbrt(p)) == Math.cbrt(p)){
                    cnt++;
                }
            }

            if(cnt >= 3){
                return number;
            }

        }
        throw new Exception("number not found in range!");
    }

    private Set<Long> getAllPermutations(long number){
        char[] digits = Long.toString(number).toCharArray();
        Set<Long> permutations = new HashSet<>();

//        123
//        132
//        312
//        321
//        231
//        213
//
//        123 ENDE
        char[] firstPerm = digits.clone();


        do {
            for (int i = 0; i < firstPerm.length-1; i++) {
                if(String.valueOf(Long.parseLong(String.valueOf(digits))).length() == String.valueOf(Long.parseLong(String.valueOf(firstPerm))).length()){
//                    System.out.println(String.valueOf(Long.parseLong(String.valueOf(digits))).length() + " " + String.valueOf(Long.parseLong(String.valueOf(firstPerm))).length());
                    permutations.add(Long.parseLong(String.valueOf(digits)));
                }

                char temp = digits[i+1];
                digits[i+1] = digits[i];
                digits[i] = temp;

                if(String.valueOf(digits).equals(String.valueOf(firstPerm))) break;
            }
        } while(!String.valueOf(digits).equals(String.valueOf(firstPerm)));

        return permutations;
    }

    public static void main(String[] args) {
        PermutationWorker worker = new PermutationWorker(345, 346);

//        long[] perm = worker.getAllPermutations(125963);
//
//        for(long l : perm){
//            System.out.println(l);
//        }

        try {
            worker.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package at.kaindorf.bl;

import java.util.Arrays;

public class MathHelper {

    public static int[] removeDuplicates(int[] values) {
        int freeIndex = 1;

        for (int i = 1; i < values.length; i++) {

            while (i < values.length-1 && values[i] == values[i - 1]) {
                i++;
            }

            values[freeIndex] = values[i];
            freeIndex++;
        }

        return Arrays.copyOfRange(values, 0, freeIndex);
    }


    public static int[] extractPrimes(int[] values) {

        int freeIndex = 0;

        for (int i = 0; i < values.length; i++) {

            if(isPrime(values[i])) {
                values[freeIndex] = values[i];
                freeIndex++;
            }
        }

        return Arrays.copyOfRange(values, 0, freeIndex);
    }


    private static boolean isPrime(int value) {
        if(value == 2) return true;

        if(value == 0 || value == 1 || value % (double) 2 == 0.0) return false;

        for (int i = 3; i < Math.sqrt(value) + 1; i+=2) {
            if((double) value % (double) i == 0.0) return false;
        }

        return true;
    }
}

package at.kaindorf.bl;

import java.util.Arrays;

public class CustomSorter {

    public static int[] insertionSort(int[] values) {

        for (int i = 1; i < values.length; i++) {
            int key = values[i];
            int j = i-1;

            while (j >= 0 && values[j] > key) {
                values[j + 1] = values[j];
                j--;
            }

            values[j + 1] = key;
        }

        return values;
    }

    public static int[] bubbleSort(int[] values) {

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length-i-1; j++) {

                if(values[j] > values[j + 1]) {
                    int temp = values[j + 1];
                    values[j + 1] = values[j];
                    values[j] = temp;
                }
            }
        }

        return values;
    }

    public static int[] selectionSort(int[] values) {

        for (int i = 0; i < values.length; i++) {
            int minIndex = i;

            for (int j = i + 1; j < values.length; j++) {

                if(values[j] < values[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = values[i];
            values[i] = values[minIndex];
            values[minIndex] = temp;
        }

        return values;
    }


    public static int[] quickSort(int[] values) {
        Arrays.sort(values);
        return values;
    }
}

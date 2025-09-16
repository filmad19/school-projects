package at.kaindorf.calculation;

import at.kaindorf.pojos.Statistics;

import java.util.Arrays;

public class StatisticsCalculator {

    public static Statistics computeStatistics(double[] numbers) {

        double mean = Arrays.stream(numbers)
                .average().getAsDouble();

        double stddev =
                Math.sqrt((double) 1 /(numbers.length-1) *
                        Arrays.stream(numbers)
                                .map(value -> Math.pow(value - mean, 2))
                                .sum()
        );

        return new Statistics(mean, stddev);
    }
}

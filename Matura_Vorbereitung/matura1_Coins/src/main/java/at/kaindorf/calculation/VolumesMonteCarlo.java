package at.kaindorf.calculation;

import at.kaindorf.pojos.Measurement;

import java.util.List;
import java.util.Random;

public class VolumesMonteCarlo {

    private static Random random = new Random();

    public static double[] computeRandomVolumes(List<Measurement> measurementList, int max) {
        double[] volumes = new double[max];

        for (int i = 0; i < max; i++) {
            int radiusIndex = random.nextInt(measurementList.size());
            double radius = measurementList.get(radiusIndex).getRadius();

            int heightIndex = random.nextInt(measurementList.size());
            double height = measurementList.get(heightIndex).getHeight();

            volumes[i] = Math.PI * radius * radius * height;
        }

        return volumes;
    }
}

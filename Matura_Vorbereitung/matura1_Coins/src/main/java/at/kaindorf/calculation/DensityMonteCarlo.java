package at.kaindorf.calculation;

import at.kaindorf.pojos.Measurement;
import at.kaindorf.pojos.Muenzen;

import java.util.List;
import java.util.Random;

public class DensityMonteCarlo {

    private static Random random = new Random();

    public static double[] computeRandomDensities(Muenzen muenzen, int max) {
        double[] densities = new double[max];

        for (int i = 0; i < max; i++) {

            double diameter = muenzen.getDiameter().getMean() + muenzen.getDiameter().getStddev() * random.nextGaussian();
            double thickness = muenzen.getThickness().getMean() + muenzen.getThickness().getStddev() * random.nextGaussian();
            double mass = muenzen.getMass().getMean() + muenzen.getMass().getStddev() * random.nextGaussian();

            densities[i] = 4 * mass / Math.PI / diameter / diameter / thickness;
        }

        return densities;
    }
}

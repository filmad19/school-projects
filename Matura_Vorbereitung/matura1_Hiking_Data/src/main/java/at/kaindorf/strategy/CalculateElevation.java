package at.kaindorf.strategy;

import at.kaindorf.pojos.Result;
import at.kaindorf.pojos.Track;
import at.kaindorf.pojos.TrackingPoint;

import java.util.List;

public class CalculateElevation implements CalculationStrategy {

    @Override
    public void calculate(Track track, Result result) {

        double elePos = 0;
        double eleNeg = 0;

        List<TrackingPoint> trackingPoints = track.getTrackingPoints();

        double prevElevation = trackingPoints.get(0).getElevation();

        for (int i = 1; i < trackingPoints.size(); i++) {
            double elevation = trackingPoints.get(i).getElevation();
            double diff = elevation - prevElevation;

            if (diff > 0) elePos += diff;
            else eleNeg -= diff;

            prevElevation = elevation;
        }

        result.setElePos(elePos);
        result.setEleNeg(eleNeg);
    }
}

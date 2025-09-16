package at.kaindorf.strategy;

import at.kaindorf.pojos.Result;
import at.kaindorf.pojos.Track;
import at.kaindorf.pojos.TrackingPoint;

import java.util.List;

public class CalculateDistance implements CalculationStrategy {

    @Override
    public void calculate(Track track, Result result) {

        double distance = 0;

//        Haversine formula:
//        a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
//        d = R ⋅ 2 ⋅ atan2( √a, √(1−a) )

        List<TrackingPoint> trackingPoints = track.getTrackingPoints();

        TrackingPoint prevPoint = trackingPoints.get(0);

        int earthRadius = 6371000;

        for(TrackingPoint point : trackingPoints.subList(1, trackingPoints.size())) {

            // of previous point
            double lat1 = Math.toRadians(prevPoint.getLat());
            double lon1 = Math.toRadians(prevPoint.getLon());

            double lat2 = Math.toRadians(point.getLat());
            double lon2 = Math.toRadians(point.getLon());

            // of current point
            double Δlat = lat2-lat1;
            double Δlon = lon2-lon1;

            double a = (Math.sin(Δlat/2) * Math.sin(Δlat/2)
                    + Math.cos(lat1) * Math.cos(lat2) * Math.sin(Δlon/2) * Math.sin(Δlon/2)
            );

            // bogenmaß, horizontale distanz
            double d = (double)(2 * earthRadius * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));

            double elevation = Math.abs(point.getElevation() - prevPoint.getElevation());

            distance += Math.sqrt(d*d + elevation*elevation);

            prevPoint = point;
        }

        result.setDistance(distance);
    }
}

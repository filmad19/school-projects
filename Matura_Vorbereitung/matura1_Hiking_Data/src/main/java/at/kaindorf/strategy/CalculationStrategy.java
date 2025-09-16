package at.kaindorf.strategy;

import at.kaindorf.pojos.Result;
import at.kaindorf.pojos.Track;

public interface CalculationStrategy {
    void calculate(Track track, Result result);
}

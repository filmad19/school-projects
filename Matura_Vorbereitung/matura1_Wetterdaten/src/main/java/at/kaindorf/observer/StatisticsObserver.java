package at.kaindorf.observer;

import at.kaindorf.pojos.StrategyResult;

public interface StatisticsObserver {
    void update(StrategyResult strategyResult);
}

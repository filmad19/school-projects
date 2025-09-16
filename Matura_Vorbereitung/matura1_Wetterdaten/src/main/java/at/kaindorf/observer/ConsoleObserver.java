package at.kaindorf.observer;

import at.kaindorf.pojos.StrategyResult;

public class ConsoleObserver implements StatisticsObserver {

    public ConsoleObserver(StatisticsSubject statisticsSubject) {
        statisticsSubject.attach(this);
    }

    @Override
    public void update(StrategyResult strategyResult) {
        System.out.println(strategyResult);
    }
}

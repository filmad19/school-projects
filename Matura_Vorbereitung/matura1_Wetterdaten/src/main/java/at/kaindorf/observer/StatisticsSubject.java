package at.kaindorf.observer;

import at.kaindorf.pojos.StrategyResult;

import java.util.ArrayList;
import java.util.List;

public abstract class StatisticsSubject {
    private List<StatisticsObserver> observerList = new ArrayList<>();


    public void attach(StatisticsObserver statisticsObserver) {
        observerList.add(statisticsObserver);
    }

    public void detach(StatisticsObserver statisticsObserver) {
        observerList.remove(statisticsObserver);
    }

    public void notifyObservers(StrategyResult strategyResult) {
        for (StatisticsObserver observer : observerList) {
            observer.update(strategyResult);
        }
    }
}

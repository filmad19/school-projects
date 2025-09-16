package at.kaindorf;

import at.kaindorf.observer.StatisticsSubject;
import at.kaindorf.pojos.Queue;
import at.kaindorf.pojos.QueueEntry;
import at.kaindorf.pojos.StrategyResult;
import at.kaindorf.strategy.IStrategy;

import java.util.stream.Collectors;


public class Consumer extends StatisticsSubject implements Runnable {

    private Queue queue;
    private IStrategy statisticStrategy;

    public Consumer(Queue queue, IStrategy statisticStrategy) {
        this.queue = queue;
        this.statisticStrategy = statisticStrategy;
    }

    @Override
    public void run() {

        int waitCount = 0;

        while (!Thread.interrupted()) {

            QueueEntry queueEntry;

            synchronized (queue) {

                while (queue.isEmpty()) {
                    try {
                        queue.wait(1000);
                        waitCount++;

                        if(waitCount > 5) return;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                queueEntry = queue.popEntry();
                waitCount = 0;
                queue.notifyAll();
            }

            queueEntry.setMeasures(queueEntry.getMeasures().stream()
                    .filter(measureData -> measureData.getValue() != -99)
                    .collect(Collectors.toList()));

            StrategyResult strategyResult = statisticStrategy.analyze(queueEntry);
            notifyObservers(strategyResult);
        }
    }
}

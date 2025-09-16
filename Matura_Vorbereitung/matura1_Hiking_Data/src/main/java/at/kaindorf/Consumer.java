package at.kaindorf;

import at.kaindorf.observer.Subject;
import at.kaindorf.pojos.Queue;
import at.kaindorf.pojos.Result;
import at.kaindorf.pojos.Track;
import at.kaindorf.strategy.CalculateDistance;
import at.kaindorf.strategy.CalculateElevation;
import at.kaindorf.strategy.CalculationStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consumer extends Subject implements Runnable {
    private Queue<Track> queue;
    private List<CalculationStrategy> strategies = new ArrayList<>();

    public Consumer(Queue<Track> queue) {
        this.queue = queue;
        strategies.add(new CalculateElevation());
        strategies.add(new CalculateDistance());
    }

    @Override
    public void run() {
        int waitCount = 0;

        while (!Thread.interrupted()) {

            long startTime = System.nanoTime();

            Track track;

            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait(1000);

                        waitCount++;
                        if (waitCount > 10) return;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                track = queue.dequeue();
                queue.notifyAll();
            }

            Result result = new Result(track.getName());
            strategies.forEach(strategy -> strategy.calculate(track, result));


            long endTime = System.nanoTime();
            result.setAnalyzeTime(endTime - startTime);

            notifyObserver(result);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

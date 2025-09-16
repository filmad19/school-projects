package at.kaindorf;

import at.kaindorf.observer.ConsoleObserver;
import at.kaindorf.observer.ExcelObserver;
import at.kaindorf.pojos.Queue;
import at.kaindorf.strategy.MonthAverageStrategy;
import at.kaindorf.strategy.YearAverageStrategy;


public class Main {
    public static void main(String[] args) {
        Queue queue = new Queue(10);

        for (int i = 0; i < 4; i++) {
            Producer producer = new Producer(queue);
            Thread producerThread = new Thread(producer);
            producerThread.start();
        }

        Consumer consumerYear = new Consumer(queue, new YearAverageStrategy());
        new ConsoleObserver(consumerYear);
        new ExcelObserver(consumerYear);
        Thread consumerYearThread = new Thread(consumerYear);
        consumerYearThread.start();

        Consumer consumerMonth = new Consumer(queue, new MonthAverageStrategy());
        new ConsoleObserver(consumerMonth);
        new ExcelObserver(consumerMonth);
        Thread consumerMonthThread = new Thread(consumerMonth);
        consumerMonthThread.start();
    }
}
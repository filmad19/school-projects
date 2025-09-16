package at.kaindorf;

import at.kaindorf.io.IO_Access;
import at.kaindorf.observer.LogObserver;
import at.kaindorf.observer.PrintObserver;
import at.kaindorf.pojos.Queue;
import at.kaindorf.pojos.Track;
import lombok.extern.java.Log;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int producerCount = 4;
        int consumerCount = 3;

        List<File> filesToRead = IO_Access.findFilesToRead();
        Queue<Track> trackQueue = new Queue<>(10);

        int filesCount = filesToRead.size();
        int filesPerProducer = (int) Math.ceil(filesCount / (double) producerCount);

        for (int i = 0; i < producerCount; i++) {

            int startIndex = filesPerProducer * i;
            int endIndex = filesPerProducer * (i + 1);

            Producer producer = new Producer(trackQueue, filesToRead.subList(startIndex, endIndex));
            Thread thread = new Thread(producer);
            thread.start();
        }

        for (int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(trackQueue);
            new LogObserver(consumer);
            new PrintObserver(consumer);
            Thread thread = new Thread(consumer);
            thread.start();
        }
    }
}
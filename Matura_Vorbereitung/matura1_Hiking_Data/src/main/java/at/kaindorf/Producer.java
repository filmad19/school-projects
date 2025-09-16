package at.kaindorf;

import at.kaindorf.pojos.Queue;
import at.kaindorf.pojos.Track;
import jakarta.xml.bind.JAXB;

import java.io.File;
import java.util.List;

public class Producer implements Runnable {

    private Queue<Track> queue;
    private List<File> filesToRead;

    public Producer(Queue<Track> queue, List<File> filesToRead) {
        this.queue = queue;
        this.filesToRead = filesToRead;
    }

    @Override
    public void run() {

        for(File file : filesToRead) {
            Track track = JAXB.unmarshal(file, Track.class);

            synchronized (queue) {
                while (queue.isFull()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                queue.enqueue(track);
                queue.notifyAll();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

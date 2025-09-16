package at.kaindorf.pojos;

import java.util.LinkedList;

public class Queue {

    private LinkedList<QueueEntry> entries = new LinkedList<>();
    private int maxSize;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void pushEntry(QueueEntry entry) {
        if(isFull()) {
            return;
        }

        entries.push(entry);
        System.out.println("Queue Size after push = " + entries.size());
    }

    public QueueEntry popEntry() {
        if(isEmpty()) {
            throw new RuntimeException("POPPING WHILE QUEUE IS EMPTY");
        }

        QueueEntry entry = entries.remove(0);
        System.out.println("Queue Size after pop = " + entries.size());

        return entry;
    }


    public boolean isFull() {
        return entries.size() >= maxSize;
    }

    public boolean isEmpty() {
        return entries.size() <= 0;
    }
}

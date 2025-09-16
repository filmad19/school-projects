package at.kaindorf.pojos;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
    private List<T> entries = new ArrayList<>();
    private int size;

    public Queue(int size) {
        this.size = size;
    }

    public void enqueue(T t) {
        if (isFull()) {
            throw new RuntimeException("enqueue while full!");
        }

        entries.add(t);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("dequeue while empty");
        }

        return entries.remove(0);
    }

    public boolean isEmpty() {
        return entries.size() <= 0;
    }

    public boolean isFull() {
        return entries.size() >= size;
    }
}

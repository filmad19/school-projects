package LERNEN.ConsumerProducer;


public class Launcher {
    public static void main(String[] args) {
        Stack stack = new Stack(10);

        Producer producer = new Producer(stack);
        Consumer consumer = new Consumer(stack);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}

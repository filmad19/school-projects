package at.kaindorf.Theorie_Thread.Consumer_Producer;

public class Launcher {

    public static void main(String[] args) {
        Stack stack = new Stack(5);
        int noInter = 20;

        Producer producer = new Producer(stack, noInter);
        Consumer consumer = new Consumer(stack, noInter);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

//        start Threads
        producerThread.start();
        consumerThread.start();
    }
}

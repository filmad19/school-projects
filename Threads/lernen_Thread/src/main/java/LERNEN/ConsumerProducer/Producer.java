package LERNEN.ConsumerProducer;

import java.util.Random;

public class Producer implements Runnable{
    private Stack stack;

    public Producer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            synchronized (stack){
                while (stack.isFull()){
                    System.out.println("Producer must wait - Stack is full");

                    try {
                        stack.wait();
                        System.out.println("Producer continues");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stack.push(random.nextInt(5));
                stack.notify();
                System.out.println("Producer: " + stack);
            }

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package LERNEN.ConsumerProducer;

import java.util.Random;

public class Consumer implements Runnable{
    private Stack stack;

    public Consumer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            synchronized (stack){
                while (stack.isEmpty()){
                    System.out.println("Consumer must wait - Stack is empty");

                    try {
                        stack.wait();
                        System.out.println("Consumer continues");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stack.pop();
                stack.notify();
                System.out.println("Consumer: " + stack);
            }

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

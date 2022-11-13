package at.kaindorf.Theorie_Thread.Consumer_Producer;

import java.util.Random;

public class Consumer implements Runnable{//produces values to STACK
    private Stack beerStack;
    private int noIterations;


    public Consumer(Stack beerStack, int noIterations) {
        this.beerStack = beerStack;
        this.noIterations = noIterations;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for(int i = 0;i < noIterations;i++){
            synchronized (beerStack){
                while(beerStack.isEmpty()){
                    System.out.println("Consumer must wait - Stack is empty");
                    try {
                        beerStack.wait();
                        System.out.println("Consumer continues");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int value = beerStack.pop();
                beerStack.notify();

                System.out.println("Consumer: " + beerStack);
            }
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

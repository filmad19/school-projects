package at.kaindorf.Theorie_Thread.Consumer_Producer;

import java.util.Random;

public class Producer implements Runnable{//consumes values from STACK
    private Stack beerStack;
    private int noIterations;

    public Producer(Stack beerStack ,int noIterations) {
        this.beerStack = beerStack;
        this.noIterations = noIterations;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for(int i = 0;i < noIterations;i++){
            synchronized (beerStack){
                while(beerStack.isFull()){
                    System.out.println("Producer must wait - Stack is full");

                    try {
                        beerStack.wait(); //muss auf synchronisierten Block aufgerufen werden
                        System.out.println("Producer continues");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                beerStack.push(rand.nextInt(90) + 10);
                beerStack.notify();

                System.out.println("Producer: " + beerStack);
            }

            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

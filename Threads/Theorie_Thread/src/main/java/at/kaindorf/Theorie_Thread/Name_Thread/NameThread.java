package at.kaindorf.Theorie_Thread.Name_Thread;

public class NameThread implements Runnable{
//    private StringBuffer stringBuilder = new StringBuffer(10_000_000);          -->  IST THREAD SAVE
    private StringBuilder stringBuilder = new StringBuilder(10_000_000);


    @Override
    public void run() {
        for(int i = 0;i < 100_000;i++){
            synchronized (stringBuilder){ //block so klein wie möglich
                stringBuilder.append(Thread.currentThread().getName());
            }
        }
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public static void main(String[] args) {
        NameThread nameThread = new NameThread();

        Thread thread1 = new Thread(nameThread, "Jonas");
        Thread thread2 = new Thread(nameThread, "Fred");
        Thread thread3 = new Thread(nameThread, "Goofy");

        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread3.start();


        try {
            thread1.join(); //aktueller Thread (Main) wartet bis thread 1 fertig is
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Time needed: " + (end-start));
        System.out.println(nameThread.getStringBuilder().length());
        System.out.println(nameThread.getStringBuilder().substring(0, 1000));
    }
}

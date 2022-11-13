package at.kaindorf.Kontoverwaltung;

import javax.swing.*;
import java.util.Random;

public class KontoBenutzer implements Runnable{
    private String name;
    private Konto konto;
    private JTextArea logArea;

    public KontoBenutzer(String name, Konto konto, JTextArea logArea) {
        this.name = name;
        this.konto = konto;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        Random random = new Random();
        int waitCnt = 0;

        START:
        for(int i = 0;i < 10;i++){
            double value = random.nextInt(40)+10;
            value = random.nextBoolean() ? value * (-1) : value;

            try{
                Thread.sleep(random.nextInt(2000)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized(konto){
                while(konto.getBalance() + value <= 0){
                    try{
                        writeLog(name + " must wait --- (" + value + ")");
                        waitCnt++;
                        konto.wait(2000);
                    }catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    if(waitCnt == 3){
                        break START;
                    }
                }

                konto.withdrawMoney(value);
                waitCnt = 0;
                konto.notifyAll();
                writeLog(name + " makes withdrawal: " + value + "\t" + konto.getBalance());
            }
        }
        writeLog(name + " has finished <<");
    }

    private void writeLog(String text){
        synchronized (logArea){
            logArea.append("\n" + text);
        }
//        System.out.println(text);
    }

    @Override
    public String toString() {
        return name;
    }
}

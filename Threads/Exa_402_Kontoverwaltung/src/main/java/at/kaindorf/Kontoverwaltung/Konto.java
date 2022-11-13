package at.kaindorf.Kontoverwaltung;

import javax.swing.*;

public class Konto {
    private double balance;
    private JLabel accountOutput;

    public Konto(JLabel accountOutput) {
        this.balance = 10.0;
        this.accountOutput = accountOutput;
    }

    public void withdrawMoney(double amount){
        balance += amount;
        accountOutput.setText(this.toString());
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("%.2f €", balance);
    }
}

package com.example.exa_204_bankaccountapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private String iban;
    private double balance;
    private float interest;


    public Account(String iban, double balance, float interest) {
        this.iban = iban;
        this.balance = balance;
        this.interest = interest;
    }

    public String getIban() {
        return iban;
    }

    public double getBalance() {
        return balance;
    }

    public String getFormattedBalance() {
        return  String.format("€ %.2f", balance);
    }

    public float getInterest() {
        return interest;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", interest=" + interest +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iban);
        dest.writeDouble(this.balance);
        dest.writeFloat(this.interest);
    }

    public void readFromParcel(Parcel source) {
        this.iban = source.readString();
        this.balance = source.readDouble();
        this.interest = source.readFloat();
    }

    protected Account(Parcel in) {
        this.iban = in.readString();
        this.balance = in.readDouble();
        this.interest = in.readFloat();
    }

}

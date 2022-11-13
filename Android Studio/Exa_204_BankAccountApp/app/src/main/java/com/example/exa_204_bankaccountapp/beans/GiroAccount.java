package com.example.exa_204_bankaccountapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class GiroAccount extends Account implements Parcelable {
    private double overdraft;

    public GiroAccount(String iban, double balance, float interest, double overdraft) {
        super(iban, balance, interest);
        this.overdraft = overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public String getFormattedAvailable() {
        return String.format("€ %.2f", overdraft + getBalance());
    }

    public double getAvailable() {
        return overdraft + getBalance();
    }

    @Override
    public String toString() {
        return "GiroAccount{" + "IBAN: " + getIban() +"overdraft=" + overdraft + " BALANCE=" +  super.getBalance();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(this.overdraft);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.overdraft = source.readDouble();
    }

    protected GiroAccount(Parcel in) {
        super(in);
        this.overdraft = in.readDouble();
    }

    public static final Creator<GiroAccount> CREATOR = new Creator<GiroAccount>() {
        @Override
        public GiroAccount createFromParcel(Parcel source) {
            return new GiroAccount(source);
        }

        @Override
        public GiroAccount[] newArray(int size) {
            return new GiroAccount[size];
        }
    };
}

package com.example.exa_204_bankaccountapp.beans;


import android.os.Parcel;
import android.os.Parcelable;

public class StudentAccount extends Account implements Parcelable {
    private boolean depitCard;

    public StudentAccount(String iban, double balance, float interest, boolean depitCard) {
        super(iban, balance, interest);
        this.depitCard = depitCard;
    }

    public boolean isDepitCard() {
        return depitCard;
    }

    @Override
    public String toString() {
        return "StudentAccount{" + "IBAN: " + getIban() +"depitCard=" + depitCard +" BALANCE=" +  super.getBalance();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.depitCard ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.depitCard = source.readByte() != 0;
    }

    protected StudentAccount(Parcel in) {
        super(in);
        this.depitCard = in.readByte() != 0;
    }

    public static final Creator<StudentAccount> CREATOR = new Creator<StudentAccount>() {
        @Override
        public StudentAccount createFromParcel(Parcel source) {
            return new StudentAccount(source);
        }

        @Override
        public StudentAccount[] newArray(int size) {
            return new StudentAccount[size];
        }
    };
}

package com.example.exa_203_contactsapp.beans;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String firstname;
    private String lastname;
    private String language;
    private char gender;
    private Uri picture;
    private String phoneNumber;

    public Contact(String firstname, String lastname, String language, char gender, String picture, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.language = language;
        this.gender = gender;
        this.picture = Uri.parse(picture);
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName() {
        return lastname + ", " + firstname;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getLanguage() {
        return language;
    }

    public char getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.language);
        dest.writeInt(this.gender);
        dest.writeParcelable(this.picture, flags);
        dest.writeString(this.phoneNumber);
    }

    public void readFromParcel(Parcel source) {
        this.firstname = source.readString();
        this.lastname = source.readString();
        this.language = source.readString();
        this.gender = (char) source.readInt();
        this.picture = source.readParcelable(Uri.class.getClassLoader());
        this.phoneNumber = source.readString();
    }

    protected Contact(Parcel in) {
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.language = in.readString();
        this.gender = (char) in.readInt();
        this.picture = in.readParcelable(Uri.class.getClassLoader());
        this.phoneNumber = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}

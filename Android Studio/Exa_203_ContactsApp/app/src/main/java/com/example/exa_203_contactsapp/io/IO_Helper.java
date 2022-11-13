package com.example.exa_203_contactsapp.io;

import android.content.res.AssetManager;

import com.example.exa_203_contactsapp.beans.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Helper {
    public static List<Contact> readContacts(AssetManager am) throws IOException {
        InputStream is = am.open("contact_data.csv");

        List<String> contactsRow = new BufferedReader(
                new InputStreamReader(is))
                .lines()
                .skip(1)
//                .map(Contact::this)   KONSTRUKTOR BRAUCHT ALS ÜBERGABEWERT String line --> Konstruktor spliter lines aufsd
                .collect(Collectors.toList());

        List<Contact> contactList = new ArrayList<>();

        for (String row : contactsRow){
            String[] tokens = row.split(",");
//            id,first_name,last_name,language,gender,picture,phone_number
            contactList.add(new Contact(tokens[1], tokens[2], tokens[3], tokens[4].charAt(0), tokens[5], tokens[6]));
        }
        return contactList;
    }
}

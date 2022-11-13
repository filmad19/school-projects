package com.example.exa_204_bankaccountapp.io;

import android.content.res.AssetManager;

import com.example.exa_204_bankaccountapp.beans.Account;
import com.example.exa_204_bankaccountapp.beans.GiroAccount;
import com.example.exa_204_bankaccountapp.beans.StudentAccount;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Access {
    public static List<Account> loadAccounts(AssetManager am) throws IOException {
        InputStream is = am.open("account_data.csv");
//        id,type,iban,amount,overdraft,debitcard,interest

        List<String> stringList = new BufferedReader(new InputStreamReader(is))
                .lines()
                .skip(1)
                .collect(Collectors.toList());

        List<Account> accountList = new ArrayList<>();

        for(String line : stringList){
            String[] token = line.split(",");

            String type = token[1];
            String iban = token[2];
            double amount = Double.parseDouble(token[3]);
            double overdraft = Double.parseDouble(token[4]);
            boolean debitcard = (token[5].charAt(0) ==  'T') ? true : false;
            float interest = Float.parseFloat(token[6]);


            if(type.equals("STUDENT")){
                StudentAccount acc = new StudentAccount(iban, amount, interest, debitcard);
                accountList.add(acc);
            }else{
                GiroAccount acc = new GiroAccount(iban, amount, interest, overdraft);
                accountList.add(acc);
            }
        }
        return accountList;
    }
}

package com.example.exa_204_bankaccountapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.exa_204_bankaccountapp.beans.Account;
import com.example.exa_204_bankaccountapp.beans.GiroAccount;
import com.example.exa_204_bankaccountapp.bl.AccountAdapter;
import com.example.exa_204_bankaccountapp.io.IO_Access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rvList);

//        READ ACCOUNTS
        List<Account> accountList = new ArrayList<>();
        try {
            accountList = IO_Access.loadAccounts(getAssets());
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "ERROR while input", Toast.LENGTH_SHORT).show();
        }

//        ADAPTER
        adapter = new AccountAdapter(accountList);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.sortList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        adapter.filterAccount(item);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1 && resultCode == 2) {
            Account acc = intent.getParcelableExtra("acc");
            String iban = intent.getStringExtra("iban");
            double amount = intent.getDoubleExtra("amount", 0);

            adapter.transfereMoney(acc, iban, amount);
        }
    }
}
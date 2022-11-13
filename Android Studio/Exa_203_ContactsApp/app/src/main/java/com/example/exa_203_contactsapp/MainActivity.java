package com.example.exa_203_contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.exa_203_contactsapp.beans.Contact;
import com.example.exa_203_contactsapp.bl.ContactAdapter;
import com.example.exa_203_contactsapp.io.IO_Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private SearchView svSearch;
    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svSearch = findViewById(R.id.svSearch);
        rvList = findViewById(R.id.rvList);

//       Daten Einlesen
        List<Contact> contactList = new ArrayList<>();
        try {
            contactList = IO_Helper.readContacts(this.getAssets());
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File could not be read in", Toast.LENGTH_SHORT).show();
        }

//        Daten in View Liste mit Adapter
        ContactAdapter adapter = new ContactAdapter(contactList);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvList.setAdapter(adapter);

//        Filtern
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterNames(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newTex) {
                adapter.filterNames(newTex);
                return true;
            }
        });
    }
}
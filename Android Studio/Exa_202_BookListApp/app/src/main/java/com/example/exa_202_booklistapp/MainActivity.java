package com.example.exa_202_booklistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.exa_202_booklistapp.beans.Book;
import com.example.exa_202_booklistapp.bl.BookAdapter;
import com.example.exa_202_booklistapp.io.IO_Access;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBooks = findViewById(R.id.rvBookList);

        try {
            List<Book> books = IO_Access.loadBooks(this.getAssets());
            rvBooks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvBooks.setAdapter(new BookAdapter(books));
        } catch (IOException e){
            Toast.makeText(getApplicationContext(), "ERROR during file reading", Toast.LENGTH_SHORT).show();
        }
    }
}
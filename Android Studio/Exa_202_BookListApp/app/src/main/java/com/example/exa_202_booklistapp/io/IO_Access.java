package com.example.exa_202_booklistapp.io;

import android.content.res.AssetManager;

import com.example.exa_202_booklistapp.beans.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Access {
    public static List<Book> loadBooks(AssetManager am) throws IOException {
        InputStream is = am.open("bookList.csv");
        List<String> books = new BufferedReader(
                new InputStreamReader(is))
                .lines()
                .skip(1)
                .collect(Collectors.toList());

//        Title;Author;ReleaseDate;ISBN
//        Krieg, stell dir vor er sei hier;Jane Teller;2015-08;3213777163

        List<Book> bookList = new ArrayList<>();
        for(String book : books){
            String[] tokens = book.split(";");
            String title = tokens[0];
            String author = tokens[1];
            YearMonth releaseDate = YearMonth.parse(tokens[2], Book.DTF);
            String isbn = tokens[3];
            bookList.add(new Book(title, author, releaseDate, isbn));
        }

        Collections.sort(bookList);
        return bookList;
    }
}

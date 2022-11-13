package com.example.exa_202_booklistapp.beans;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import lombok.Data;

public class Book implements Comparable<Book>{
    private String title;
    private String author;
    private YearMonth releaseDate;
    private String isbn;

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM");

    public Book(String title, String author, YearMonth releaseDate, String isbn) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.isbn = isbn;
    }

    @Override
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getFormattedReleaseDate(){
        return releaseDate.format(DTF);
    }

    public String getIsbn() {
        return isbn;
    }
}

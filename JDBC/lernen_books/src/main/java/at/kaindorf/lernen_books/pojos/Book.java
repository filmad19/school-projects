package at.kaindorf.lernen_books.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Book {
    private String title;
    private String isbnNr;
    private List<String> authors = new ArrayList<>();
    private String publisher;
    private int pages;
    private float rating;
    private LocalDate date;
    private List<String> genre = new ArrayList<>();
    private DateTimeFormatter DTF_OUT = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getFormattedAuthors() {
        if(authors.isEmpty()) return "-";

        return authors.stream().collect(Collectors.joining(", "));
    }

    public String getFormattedGenre() {
        if(genre.isEmpty()) return "-";

        String output = genre.get(0);
        for(int i = 1;i < genre.size();i++){
            output += ", " + genre.get(i);
        }
        return output;
    }

    public String getFormattedDate() {
        if(date == null) return "-";
        return date.format(DTF_OUT);
    }
}

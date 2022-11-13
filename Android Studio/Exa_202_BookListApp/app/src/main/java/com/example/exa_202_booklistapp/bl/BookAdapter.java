package com.example.exa_202_booklistapp.bl;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_202_booklistapp.R;
import com.example.exa_202_booklistapp.beans.Book;

import java.time.YearMonth;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter <BookViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvAuthor = view.findViewById(R.id.tvAuthor);
        TextView tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        TextView tvISBN = view.findViewById(R.id.tvISBN);

        return new BookViewHolder(view, tvTitle, tvAuthor, tvReleaseDate, tvISBN);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.getTvTitle().setText(book.getTitle());
        holder.getTvAuthor().setText(book.getAuthor());
        holder.getTvReleaseDate().setText(book.getFormattedReleaseDate());
        holder.getTvISBN().setText(book.getIsbn());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
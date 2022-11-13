package com.example.exa_202_booklistapp.bl;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvReleaseDate;
    private TextView tvISBN;

    public BookViewHolder(@NonNull View itemView, TextView tvTitle, TextView tvAuthor, TextView tvReleaseDate, TextView tvISBN) {
        super(itemView);
        this.tvTitle = tvTitle;
        this.tvAuthor = tvAuthor;
        this.tvReleaseDate = tvReleaseDate;
        this.tvISBN = tvISBN;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }
    public TextView getTvAuthor() {
        return tvAuthor;
    }
    public TextView getTvReleaseDate() {
        return tvReleaseDate;
    }
    public TextView getTvISBN() {
        return tvISBN;
    }
}

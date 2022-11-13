package com.example.exa_201_zodiacsign.bl;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_201_zodiacsign.R;
import com.example.exa_201_zodiacsign.beans.ZodiacSign;

public class ZodiacViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
    private TextView tvName;
    private TextView tvDate;
    private ImageView tvImage;

    public ZodiacViewHolder(@NonNull View itemView, TextView tvName, TextView tvDate, ImageView tvImage) {
        super(itemView);
        this.tvName = tvName;
        this.tvDate = tvDate;
        this.tvImage = tvImage;
        itemView.setOnClickListener(this);
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public ImageView getTvImage() {
        return tvImage;
    }

    @Override
    public void onClick(View v) {
        String url = v.getContext().getString(R.string.wikipedia_url, tvName.getText());
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        v.getContext().startActivity(viewIntent);
    }
}

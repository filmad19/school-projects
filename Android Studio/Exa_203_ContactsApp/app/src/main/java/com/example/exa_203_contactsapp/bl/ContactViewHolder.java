package com.example.exa_203_contactsapp.bl;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_203_contactsapp.MainActivity;
import com.example.exa_203_contactsapp.MainActivityDetails;
import com.example.exa_203_contactsapp.beans.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView ivPicture;
    private TextView tvName;
    private Contact contact;

    public ContactViewHolder(@NonNull View itemView, ImageView ivPicture, TextView tvName) {
        super(itemView);
        this.ivPicture = ivPicture;
        this.tvName = tvName;
        itemView.setOnClickListener(this);
    }

    public ImageView getIvPicture() {
        return ivPicture;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MainActivityDetails.class);
        intent.putExtra("contact", contact);
        v.getContext().startActivity(intent);
    }
}

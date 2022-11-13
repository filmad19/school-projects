package com.example.exa_203_contactsapp.bl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_203_contactsapp.R;
import com.example.exa_203_contactsapp.beans.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder>{
    List<Contact> contactList;
    List<Contact> fullList;

    public ContactAdapter(List<Contact> list) {
        this.fullList = list;
        this.contactList = list;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        ImageView ivPicture = view.findViewById(R.id.ivPicture);
        TextView tvName = view.findViewById(R.id.tvName);
        return new ContactViewHolder(view, ivPicture, tvName);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        Picasso.get()
                .load(contact.getPicture())
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(holder.getIvPicture());
        holder.getTvName().setText(contact.getFullName());
        holder.setContact(contact);
    }

    public void filterNames(String text){
        contactList = new ArrayList<>(fullList);
        contactList.removeIf(contact -> !contact.getLastname().toLowerCase().contains(text.toLowerCase()));
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}

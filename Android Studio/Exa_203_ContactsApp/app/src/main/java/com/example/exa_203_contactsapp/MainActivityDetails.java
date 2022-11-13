package com.example.exa_203_contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exa_203_contactsapp.beans.Contact;
import com.squareup.picasso.Picasso;

public class MainActivityDetails extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvFullName;
    private TextView tvPhone;
    private TextView tvLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);

        ivImage = findViewById(R.id.ivImage);
        tvFullName = findViewById(R.id.tvFullName);
        tvPhone = findViewById(R.id.tvPhone);
        tvLanguage = findViewById(R.id.tvLanguage);

        Intent intent = getIntent();
        Contact contact = intent.getParcelableExtra("contact");

        Picasso.get()
                .load(contact.getPicture())
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(ivImage);
        tvFullName.setText(contact.getFirstname() + " " + contact.getLastname());
        tvPhone.setText(contact.getPhoneNumber());
        tvLanguage.setText(contact.getLanguage());

    }
}
package com.example.exa_201_zodiacsign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.exa_201_zodiacsign.beans.ZodiacSign;
import com.example.exa_201_zodiacsign.bl.ZodiacSignAdapter;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView rvZodiacSignList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvZodiacSignList = findViewById(R.id.rvZodiacSignList);

        rvZodiacSignList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvZodiacSignList.setAdapter(new ZodiacSignAdapter());
    }
}
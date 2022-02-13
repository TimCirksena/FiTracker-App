package com.example.prog3projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prog3projekt.DayDB.Day;

public class VorlageActivity extends AppCompatActivity {
    //Ich brauch Day damit ich Übungen dem Day hinzufügen kann
    //Adapter für die RecyclerView erstellen
    //Mit AddEditNote verbinden

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vorlage);

    }
}
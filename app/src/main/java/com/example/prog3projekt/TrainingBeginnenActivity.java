package com.example.prog3projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainingBeginnenActivity extends AppCompatActivity {

    private Button vorlage;
    private Button neuesTraining;
    private Button aktuellesTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_beginnen);

        vorlage = findViewById(R.id.VorlageBtn);
        neuesTraining = findViewById(R.id.NeuesTrainingBtn);
        aktuellesTraining = findViewById(R.id.AktuellesTrainingBtn);

        vorlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TrainingBeginnenActivity.this, VorlageActivity.class);
                startActivity(intent);
            }
        });
        neuesTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TrainingBeginnenActivity.this, AddEditNoteActivity.class);
                startActivity(intent);
            }
        });
        aktuellesTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingBeginnenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.prog3projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prog3projekt.Home.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                TrainingBeginnenActivity.this.finish();
            }
        });
        /** Auf Back Stack liegende Activities werden bearbeitet */
        neuesTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TrainingBeginnenActivity.this, AddEditNoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                TrainingBeginnenActivity.this.finish();
            }
        });
        aktuellesTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingBeginnenActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                TrainingBeginnenActivity.this.finish();
            }
        });
        FloatingActionButton buttonBack = findViewById(R.id.button_back_trainingBeginnen);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingBeginnenActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                TrainingBeginnenActivity.this.finish();
            }
        });
    }
}
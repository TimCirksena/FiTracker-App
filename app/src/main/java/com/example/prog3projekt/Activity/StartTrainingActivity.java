package com.example.prog3projekt.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prog3projekt.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
/** <h2>Tim Cirksena</h2>
 * */
public class StartTrainingActivity extends AppCompatActivity {

    private Button vorlage;
    private Button neuesTraining;
    private Button aktuellesTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_start);

        vorlage = findViewById(R.id.VorlageBtn);
        neuesTraining = findViewById(R.id.NeuesTrainingBtn);
        aktuellesTraining = findViewById(R.id.AktuellesTrainingBtn);

        vorlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartTrainingActivity.this, TemplateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                StartTrainingActivity.this.finish();
            }
        });
        /** Auf Back Stack liegende Activities werden bearbeitet */
        neuesTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartTrainingActivity.this, AddEditExercisesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                StartTrainingActivity.this.finish();
            }
        });
        aktuellesTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTrainingActivity.this, ListExercisesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                StartTrainingActivity.this.finish();
            }
        });
        FloatingActionButton buttonBack = findViewById(R.id.button_back_trainingBeginnen);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTrainingActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                StartTrainingActivity.this.finish();
            }
        });
    }
}
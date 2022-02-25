package com.example.prog3projekt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseAdapterVorlage;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TemplateActivity extends AppCompatActivity {
    private ExerciseViewModel exerciseViewModel;

    public static final String EXTRA_VORLAGE_VORLAGE =
            "com.example.prog3projekt.EXTRA_VORLAGE_VORLAGE";

    //Ich brauch Day damit ich Übungen dem Day hinzufügen kann
    //Adapter für die RecyclerView erstellen
    //Mit AddEditNote verbinden

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vorlage);


        RecyclerView recyclerViewVorlage = findViewById(R.id.recycler_view_Vorlage);
        recyclerViewVorlage.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVorlage.setHasFixedSize(true);

        //Adapter mit der RecyclerView verbinden
        ExerciseAdapterVorlage adapter = new ExerciseAdapterVorlage();
        recyclerViewVorlage.setAdapter(adapter);

        exerciseViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercisesWithVorlage().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                adapter.setNotes(exercises);
            }
        });
        adapter.setOnItemClickListner(new ExerciseAdapterVorlage.OnItemClickListner() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(TemplateActivity.this, ListUebungActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_VORLAGE_VORLAGE, exercise.getVorlage());
                setResult(79, intent);
                startActivity(intent);
                TemplateActivity.this.finish();

            }
        });
        FloatingActionButton buttonBack = findViewById(R.id.button_back_vorlage);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TemplateActivity.this, StartTrainingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                TemplateActivity.this.finish();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        //Zum öffnen des Textfensters
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.go_back_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_back:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
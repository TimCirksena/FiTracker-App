package com.example.prog3projekt.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.Adapter.ExerciseVorlageAdapter;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.example.prog3projekt.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TemplateActivity extends AppCompatActivity {
    private ExerciseViewModel exerciseViewModel;

    public static final String EXTRA_VORLAGE_VORLAGE =
            "com.example.prog3projekt.EXTRA_VORLAGE_VORLAGE";


    /**Adapter für die RecyclerView erstellen
     * Mit AddEditNote verbinden */
    /** <h2>Tim Cirksena</h2> */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        /** RecyclerView für Exercise die eine Vorlage besitzen */
        RecyclerView recyclerViewVorlage = findViewById(R.id.recycler_view_Vorlage);
        recyclerViewVorlage.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVorlage.setHasFixedSize(true);

        /**Adapter mit der RecyclerView verbinden */
        ExerciseVorlageAdapter adapter = new ExerciseVorlageAdapter();
        recyclerViewVorlage.setAdapter(adapter);

        exerciseViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercisesWithVorlage().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                adapter.setExercises(exercises);
            }
        });
        /** Es soll differenziert werden zwischen den verschiedenen
         * Exercises, eine Vorlage soll nur Exercises erhalten die eine Vorlage besitzen */
        adapter.setOnItemClickListner(new ExerciseVorlageAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(TemplateActivity.this, ListExercisesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_VORLAGE_VORLAGE, exercise.getVorlage());
                setResult(79, intent);
                startActivity(intent);
                TemplateActivity.this.finish();

            }
        });
        /** zurück zur StartTraining Activity Button */
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
}
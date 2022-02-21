package com.example.prog3projekt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseAdapter;
import com.example.prog3projekt.ExerciseDB.ExerciseAdapterVorlage;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;

import java.util.List;

public class VorlageActivity extends AppCompatActivity {
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
                Intent intent = new Intent(VorlageActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_VORLAGE_VORLAGE, exercise.getVorlage());
                Log.d(exercise.getVorlage(),"mk2");
                setResult(79, intent);
                startActivity(intent);

                /**
                intent.putExtra(AddEditNoteActivity.EXTRA_VORLAGE, exercise.getVorlage());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, exercise.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, exercise.getName());
                intent.putExtra(AddEditNoteActivity.EXTRA_DATUM, exercise.getDatum());
                intent.putExtra(AddEditNoteActivity.EXTRA_SCHWIERIGKEIT, exercise.getSchwierigkeit());
                intent.putExtra(AddEditNoteActivity.EXTRA_POS_SPINNER, exercise.getPos());
                intent.putExtra(AddEditNoteActivity.EXTRA_SAETZE, exercise.getSaetze());
                intent.putExtra(AddEditNoteActivity.EXTRA_WIEDERHOLUNGEN, exercise.getWiederholungen());
                intent.putExtra(AddEditNoteActivity.EXTRA_GEWICHT, exercise.getGewicht());
                intent.putExtra(AddEditNoteActivity.EXTRA_BESCHREIBUNG, exercise.getBeschreibung());
*/
                }
        });

                /**
                 //exerciseViewModel.insert(t);
                 //Für das Swipen von notes delete links und rechts
                 new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                 ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
                }

                @Override public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                exerciseViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(VorlageActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                }
                }).attachToRecyclerView(recyclerView);*/
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
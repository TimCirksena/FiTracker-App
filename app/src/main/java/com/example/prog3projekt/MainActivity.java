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
import android.view.View;
import android.widget.Toast;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseAdapter;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExerciseViewModel exerciseViewModel;

    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == 77) {
                        //Neues Note bekommt Daten durch Intent übergeben und wird erzeugt, anschließend in die Datenbank inserted
                        exerciseViewModel.insert(initalisierungNote(data));
                    } else if (result.getResultCode() == 78) {
                        int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "note cant be updated", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Exercise exercise = initalisierungNote(data);
                        exercise.setId(id);
                        exerciseViewModel.update(exercise);
                        Toast.makeText(MainActivity.this, "updated note", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.getResultCode() == 79){
                        String s = data.getStringExtra(VorlageActivity.EXTRA_VORLAGE_VORLAGE);
                        Log.d(s,"mk");
                        Toast.makeText(MainActivity.this, "List of Vorlagen", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "not saved", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

        //Exercise t = new Exercise("Peter","10.10.1000", 1000,"asef", 10, 10, 10, "alles", 1);
        //t.setVorlage("PushDay");


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Adapter mit der RecyclerView verbinden
        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String s = intent.getStringExtra(VorlageActivity.EXTRA_VORLAGE_VORLAGE);
        Log.d(s,"intentswag");

        exerciseViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);
        if(s != null){
            exerciseViewModel.getAllExercisesForVorlage(s).observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(@Nullable List<Exercise> exercises) {
                    adapter.setNotes(exercises);

                }
            });
        }else{
            exerciseViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(@Nullable List<Exercise> exercises) {
                    adapter.setNotes(exercises);
                }
            });
        }

        //exerciseViewModel.insert(t);
        //Für das Swipen von notes delete links und rechts
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                exerciseViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListner(new ExerciseAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Exercise exercise) {
                //TODO: In Bundle machen
                //Intent swaped von MainActivity zu AddEditActivity
                //
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
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

                someActivityResultLauncher.launch(intent);
            }
        });
    }

    //Menu für Delete all notes
    public boolean onCreateOptionsMenu(Menu menu) {
        //Zum öffnen des Textfensters
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                exerciseViewModel.deleteAllNotes();
                Toast.makeText(this, "Alles gelöscht amk", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.back:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Exercise initalisierungNote(Intent data) {

        String uebung = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
        String beschreibung = data.getStringExtra(AddEditNoteActivity.EXTRA_BESCHREIBUNG);
        int schwierigkeit = data.getIntExtra(AddEditNoteActivity.EXTRA_SCHWIERIGKEIT, 1);
        int spinner_pos = data.getIntExtra(AddEditNoteActivity.EXTRA_POS_SPINNER, 0);
        int wiederholungen = data.getIntExtra(AddEditNoteActivity.EXTRA_WIEDERHOLUNGEN, 1);
        int saetze = data.getIntExtra(AddEditNoteActivity.EXTRA_SAETZE, 1);
        String gewicht_string = data.getStringExtra(AddEditNoteActivity.EXTRA_GEWICHT);
        String datum = data.getStringExtra(AddEditNoteActivity.EXTRA_DATUM);
        String vorlage = data.getStringExtra(AddEditNoteActivity.EXTRA_VORLAGE);
        Exercise exercise = new Exercise(uebung, datum,1111, beschreibung, schwierigkeit, wiederholungen, saetze, gewicht_string, spinner_pos);
        exercise.setVorlage(vorlage);
        return exercise;
    }

}
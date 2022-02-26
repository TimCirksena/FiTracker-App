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
import android.view.View;
import android.widget.Toast;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseAdapter;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.example.prog3projekt.ExerciseDB.OnItemClickListener;
import com.example.prog3projekt.Home.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DayExercisesActivity extends AppCompatActivity {
    public static final String EXTRA_GEWICHT_MAIN =
            "com.example.prog3projekt.EXTRA_GEWICHT_MAIN";
    private ExerciseViewModel exerciseViewModel;
    /** <h2>Tim Cirksena & Tom Sattler</h2>
     *  Ansicht von
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent data = getIntent();
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayExercisesActivity.this, AddEditUebungActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

        FloatingActionButton buttonBack = findViewById(R.id.button_back_main);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayExercisesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton buttonDeleteAll = findViewById(R.id.button_delete_main);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = data.getIntExtra("com.example.prog3projekt.Home.EXTRA_DATUM", 1) + 1;
                exerciseViewModel.deleteExerciseForDay(DataTimeConverter.addZerosToDate(tag, DataTimeConverter.getMonth(), DataTimeConverter.getYear()));
                Log.d("Exercise to delete: ", data.getIntExtra("com.example.prog3projekt.Home.EXTRA_DATUM", 1) + 1 + "." + DataTimeConverter.getMonth() + "." + DataTimeConverter.getYear());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        /**Adapter mit der RecyclerView verbinden */
        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);
        exerciseViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);
        exerciseViewModel.getExerciseForDay(data.getIntExtra("com.example.prog3projekt.Home.EXTRA_DATUM", 1) + 1, DataTimeConverter.getMonth(), DataTimeConverter.getYear()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                adapter.setNotes(exercises);
            }
        });


        /** Für das Swipen von notes delete links und rechts */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                exerciseViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DayExercisesActivity.this, "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                //TODO: Hier alles ändern weil wir Trainigsgeräte haben
                //Intent swaped von MainActivity zu AddEditActivity
                Intent intent = new Intent(DayExercisesActivity.this, AddEditUebungActivity.class);
                intent.putExtra(AddEditUebungActivity.EXTRA_ID, exercise.getId());
                intent.putExtra(AddEditUebungActivity.EXTRA_TITLE, exercise.getName());
                intent.putExtra(AddEditUebungActivity.EXTRA_DATUM, exercise.getDatum());
                intent.putExtra(AddEditUebungActivity.EXTRA_SCHWIERIGKEIT, exercise.getSchwierigkeit());
                intent.putExtra(AddEditUebungActivity.EXTRA_POS_SPINNER, exercise.getPos());
                intent.putExtra(AddEditUebungActivity.EXTRA_SAETZE, exercise.getSaetze());
                intent.putExtra(AddEditUebungActivity.EXTRA_WIEDERHOLUNGEN, exercise.getWiederholungen());
                intent.putExtra(AddEditUebungActivity.EXTRA_GEWICHT, exercise.getGewicht());
                intent.putExtra(AddEditUebungActivity.EXTRA_BESCHREIBUNG, exercise.getBeschreibung());
                someActivityResultLauncher.launch(intent);
            }
        });
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    /** Result code wichtig für indenifiaktion */
                    if (result.getResultCode() == 77) {
                        /** Neue Übung bekommt Daten durch Intent übergeben und wird erzeugt, anschließend in die Datenbank inserted */
                        exerciseViewModel.insert(initalisierungNote(data));
                    } else if (result.getResultCode() == 78) {
                        int id = data.getIntExtra(AddEditUebungActivity.EXTRA_ID, -1);
                        if (id == -1) {
                            Toast.makeText(DayExercisesActivity.this, "note cant be updated", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Exercise exercise = initalisierungNote(data);
                        exercise.setId(id);
                        exerciseViewModel.update(exercise);
                        Toast.makeText(DayExercisesActivity.this, "updated note", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DayExercisesActivity.this, "not saved", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private Exercise initalisierungNote(Intent data) {
        String uebung = data.getStringExtra(AddEditUebungActivity.EXTRA_TITLE);
        String beschreibung = data.getStringExtra(AddEditUebungActivity.EXTRA_BESCHREIBUNG);
        int schwierigkeit = data.getIntExtra(AddEditUebungActivity.EXTRA_SCHWIERIGKEIT, 1);
        int spinner_pos = data.getIntExtra(AddEditUebungActivity.EXTRA_POS_SPINNER, 0);
        int wiederholungen = data.getIntExtra(AddEditUebungActivity.EXTRA_WIEDERHOLUNGEN, 1);
        int saetze = data.getIntExtra(AddEditUebungActivity.EXTRA_SAETZE, 1);
        String gewicht_string = data.getStringExtra(AddEditUebungActivity.EXTRA_GEWICHT);
        String datum = data.getStringExtra(AddEditUebungActivity.EXTRA_DATUM);
        Exercise exercise = new Exercise(uebung, datum, beschreibung, schwierigkeit, wiederholungen, saetze, gewicht_string, spinner_pos);

        return exercise;
    }

}
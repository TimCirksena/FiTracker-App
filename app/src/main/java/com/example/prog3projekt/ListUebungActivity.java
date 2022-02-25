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
import android.view.View;
import android.widget.Toast;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseAdapter;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.example.prog3projekt.ExerciseDB.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListUebungActivity extends AppCompatActivity {
    public static final String EXTRA_GEWICHT_MAIN =
            "com.example.prog3projekt.EXTRA_GEWICHT_MAIN";
    private ExerciseViewModel exerciseViewModel;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == 77) {
                        //Neues Note bekommt Daten durch Intent übergeben und wird erzeugt, anschließend in die Datenbank inserted
                        exerciseViewModel.insert(initalisierungNote(data));
                    } else if (result.getResultCode() == 78) {
                        int id = data.getIntExtra(AddEditUebungActivity.EXTRA_ID, -1);
                        if (id == -1) {
                            Toast.makeText(ListUebungActivity.this, "note cant be updated", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Exercise exercise = initalisierungNote(data);
                        exercise.setId(id);
                        exerciseViewModel.update(exercise);
                        Toast.makeText(ListUebungActivity.this, "updated note", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.getResultCode() == 79){
                        String s = data.getStringExtra(TemplateActivity.EXTRA_VORLAGE_VORLAGE);
                        Log.d(s,"mk");
                        Toast.makeText(ListUebungActivity.this, "List of Vorlagen", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ListUebungActivity.this, "not saved", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(ListUebungActivity.this, AddEditUebungActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

        FloatingActionButton buttonBack = findViewById(R.id.button_back_main);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListUebungActivity.this, StartTrainingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ListUebungActivity.this.finish();
            }
        });
        FloatingActionButton buttonDeleteAll = findViewById(R.id.button_delete_main);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseViewModel.deleteAllNotes();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Adapter mit der RecyclerView verbinden
        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String s = intent.getStringExtra(TemplateActivity.EXTRA_VORLAGE_VORLAGE);
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
            exerciseViewModel.getExerciseForDay(DataTimeConverter.getDay(),DataTimeConverter.getMonth(), DataTimeConverter.getYear()).observe(this, new Observer<List<Exercise>>() {
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
                Toast.makeText(ListUebungActivity.this, "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                //TODO: In Bundle machen
                //Intent swaped von MainActivity zu AddEditActivity
                //
                Intent intent = new Intent(ListUebungActivity.this, AddEditUebungActivity.class);
                intent.putExtra(AddEditUebungActivity.EXTRA_VORLAGE, exercise.getVorlage());
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


    private Exercise initalisierungNote(Intent data) {

        String uebung = data.getStringExtra(AddEditUebungActivity.EXTRA_TITLE);
        String beschreibung = data.getStringExtra(AddEditUebungActivity.EXTRA_BESCHREIBUNG);
        int schwierigkeit = data.getIntExtra(AddEditUebungActivity.EXTRA_SCHWIERIGKEIT, 1);
        int spinner_pos = data.getIntExtra(AddEditUebungActivity.EXTRA_POS_SPINNER, 0);
        int wiederholungen = data.getIntExtra(AddEditUebungActivity.EXTRA_WIEDERHOLUNGEN, 1);
        int saetze = data.getIntExtra(AddEditUebungActivity.EXTRA_SAETZE, 1);
        String gewicht_string = data.getStringExtra(AddEditUebungActivity.EXTRA_GEWICHT);
        String datum = data.getStringExtra(AddEditUebungActivity.EXTRA_DATUM);
        String vorlage = data.getStringExtra(AddEditUebungActivity.EXTRA_VORLAGE);
        Exercise exercise = new Exercise(uebung, datum, beschreibung, schwierigkeit, wiederholungen, saetze, gewicht_string, spinner_pos);
        exercise.setVorlage(vorlage);
        return exercise;
    }

}
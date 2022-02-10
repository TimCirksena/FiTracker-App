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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == 77) {

                        String uebung = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                        String beschreibung = data.getStringExtra(AddEditNoteActivity.EXTRA_BESCHREIBUNG);
                        int schwierigkeit = data.getIntExtra(AddEditNoteActivity.EXTRA_SCHWIERIGKEIT, 1);
                        int spinner_pos = data.getIntExtra(AddEditNoteActivity.EXTRA_POS_SPINNER, 0);
                        int wiederholungen = data.getIntExtra(AddEditNoteActivity.EXTRA_WIEDERHOLUNGEN, 1);
                        int saetze = data.getIntExtra(AddEditNoteActivity.EXTRA_SAETZE,1);

                        Note note = new Note(uebung, beschreibung, schwierigkeit, wiederholungen, saetze, 1, spinner_pos);
                        noteViewModel.insert(note);
                    } else if (result.getResultCode() == 78) {
                        int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "note cant be updated", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String uebung = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                        String beschreibung = data.getStringExtra(AddEditNoteActivity.EXTRA_BESCHREIBUNG);
                        int schwierigkeit = data.getIntExtra(AddEditNoteActivity.EXTRA_SCHWIERIGKEIT, 1);
                        int spinner_pos = data.getIntExtra(AddEditNoteActivity.EXTRA_POS_SPINNER, 0);
                        int wiederholungen = data.getIntExtra(AddEditNoteActivity.EXTRA_WIEDERHOLUNGEN, 1);
                        int saetze = data.getIntExtra(AddEditNoteActivity.EXTRA_SAETZE,1);

                        Note note = new Note(uebung, beschreibung, schwierigkeit, wiederholungen, saetze, 1, spinner_pos);
                        note.setId(id);
                        noteViewModel.update(note);
                        Toast.makeText(MainActivity.this, "updated note", Toast.LENGTH_SHORT).show();
                    } else {
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


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Adapter mit der RecyclerView verbinden
        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
        //Für das Swipen von notes delete links und rechts
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListner(new NoteAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Note note) {
                //TODO: Hier alles ändern weil wir Trainigsgeräte haben
                //Intent swaped von MainActivity zu AddEditActivity
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getName());
                intent.putExtra(AddEditNoteActivity.EXTRA_BESCHREIBUNG, note.getDatum());
                intent.putExtra(AddEditNoteActivity.EXTRA_SCHWIERIGKEIT, note.getSchwierigkeit());
                intent.putExtra(AddEditNoteActivity.EXTRA_POS_SPINNER, note.getPos());
                intent.putExtra(AddEditNoteActivity.EXTRA_SAETZE, note.getSaetze());
                intent.putExtra(AddEditNoteActivity.EXTRA_WIEDERHOLUNGEN, note.getWiederholungen());
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
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "Alles gelöscht amk", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
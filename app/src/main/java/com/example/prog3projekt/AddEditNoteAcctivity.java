package com.example.prog3projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteAcctivity extends AppCompatActivity {
    public static final String EXTRA_EDIT =
            "com.example.prog3projekt.EXTRA_EDIT";
    public static final String EXTRA_ID =
            "com.example.prog3projekt.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.prog3projekt.EXTRA_TITLE";
    public static final String EXTRA_DESCRIP =
            "com.example.prog3projekt.EXTRA_DESCRIP";
    public static final String EXTRA_PRIO =
            "com.example.prog3projekt.EXTRA_PRIO";

    private EditText editTextTitle;
    private EditText editTextDescrip;
    private NumberPicker editPrio;
    private boolean help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_acctivity);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescrip = findViewById(R.id.edit_text_description);
        editPrio = findViewById(R.id.number_picker_prio);

        editPrio.setMinValue(1);
        editPrio.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            //TODO: Hier werte anpassen
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescrip.setText(intent.getStringExtra(EXTRA_DESCRIP));
            editPrio.setValue(intent.getIntExtra(EXTRA_PRIO, 1));

        }else{
            setTitle("Add Note");
        }
    }
    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String descript = editTextDescrip.getText().toString();
        int prio = editPrio.getValue();

        if(title.trim().isEmpty() || descript.trim().isEmpty()){
            Toast.makeText(this, "Schreib was rein du huso", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIP, descript);
        data.putExtra(EXTRA_PRIO, prio);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            setResult(78, data);
            data.putExtra(EXTRA_ID, id);
            help = true;
        }else{
            setResult(77, data);
        }
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
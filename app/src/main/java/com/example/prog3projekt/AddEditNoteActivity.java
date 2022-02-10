package com.example.prog3projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_UEBUNG =
            "com.example.prog3projekt.EXTRA_UEBUNG";
    public static final String EXTRA_ID =
            "com.example.prog3projekt.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.prog3projekt.EXTRA_TITLE";
    public static final String EXTRA_BESCHREIBUNG =
            "com.example.prog3projekt.EXTRA_BESCHREIBUNG";
    public static final String EXTRA_SCHWIERIGKEIT =
            "com.example.prog3projekt.EXTRA_SCHWIERIGKEIT";
    public static final String EXTRA_SAETZE =
            "com.example.prog3projekt.EXTRA_SAETZE";
    public static final String EXTRA_WIEDERHOLUNGEN =
            "com.example.prog3projekt.EXTRA_WIEDERHOLUNGEN";
    public static final String EXTRA_POS_SPINNER=
            "com.example.prog3projekt.EXTRA_POS_SPINNER";

    private EditText editTextBeschreibung;
    private NumberPicker editSchwierigkeit;
    private NumberPicker editSaetze;
    private NumberPicker editWiederholungen;
    private String[] uebungen;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private int userSpinnerChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_acctivity);

        //Anlegen der Views
        adapter = ArrayAdapter.createFromResource(this,R.array.uebungen,R.layout.support_simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner_uebung);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTextBeschreibung = findViewById(R.id.edit_text_description);
        editSchwierigkeit = findViewById(R.id.number_picker_schwierigkeit);
        editSaetze = findViewById(R.id.number_picker_saetze);
        editWiederholungen = findViewById(R.id.number_picker_wiederholung);
        uebungen = getResources().getStringArray(R.array.uebungen);

        editSchwierigkeit.setMinValue(1);
        editSchwierigkeit.setMaxValue(10);
        editWiederholungen.setMinValue(1);
        editWiederholungen.setMaxValue(25);
        editSaetze.setMinValue(1);
        editSaetze.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        //In diesem Fall existiert das Object schon und wir wollen es nur verändern
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            //TODO: Hier werte anpassen

            //Damit spinner richtige Value anzeigt
            String text = intent.getStringExtra(EXTRA_UEBUNG);
            int i = adapter.getPosition(text);
            spinner.setSelection(i);


            spinner.setSelection(intent.getIntExtra(EXTRA_POS_SPINNER,0));
            editTextBeschreibung.setText(intent.getStringExtra(EXTRA_BESCHREIBUNG));
            editSchwierigkeit.setValue(intent.getIntExtra(EXTRA_SCHWIERIGKEIT, 1));
            editSaetze.setValue(intent.getIntExtra(EXTRA_SAETZE,1));
            editWiederholungen.setValue(intent.getIntExtra(EXTRA_WIEDERHOLUNGEN,1));
        }else{
            setTitle("Add Note");
        }
    }
    private void saveNote(){
        String uebung = spinner.getSelectedItem().toString();
        String beschreibung = editTextBeschreibung.getText().toString();
        userSpinnerChoice = spinner.getSelectedItemPosition();
        int wiederholungen = editWiederholungen.getValue();
        int saetze = editSaetze.getValue();
        int schwierigkeit = editSchwierigkeit.getValue();

        if(uebung.trim().isEmpty() || beschreibung.trim().isEmpty()){
            Toast.makeText(this, "Schreib was rein du huso", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_POS_SPINNER, userSpinnerChoice);
        data.putExtra(EXTRA_TITLE, uebung);
        data.putExtra(EXTRA_BESCHREIBUNG, beschreibung);
        data.putExtra(EXTRA_SCHWIERIGKEIT, schwierigkeit);
        data.putExtra(EXTRA_SAETZE, saetze);
        data.putExtra(EXTRA_WIEDERHOLUNGEN, wiederholungen);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            setResult(78, data);
            data.putExtra(EXTRA_ID, id);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
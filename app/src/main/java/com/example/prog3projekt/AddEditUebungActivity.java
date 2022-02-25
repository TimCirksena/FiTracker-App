package com.example.prog3projekt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Calendar;

public class AddEditUebungActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_DAY_ID =
            "com.example.prog3projekt.EXTRA_DAY_ID";
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
    public static final String EXTRA_POS_SPINNER =
            "com.example.prog3projekt.EXTRA_POS_SPINNER";
    public static final String EXTRA_GEWICHT =
            "com.example.prog3projekt.EXTRA_GEWICHT";
    public static final String EXTRA_DATUM =
            "com.example.prog3projekt.EXTRA_DATUM";
    public static final String EXTRA_VORLAGE =
            "com.example.prog3projekt.EXTRA_VORLAGE";


    private EditText editTextBeschreibung;
    private EditText editTextVorlage;
    private NumberPicker editSchwierigkeit;
    private NumberPicker editSaetze;
    private NumberPicker editWiederholungen;
    private EditText editTextGewicht;
    private TextView editDateDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String[] uebungen;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private int userSpinnerChoice;
    private Button clickAbbrechen;
    private Button clickTagSpeichern;
    private Button clickWeitereÜbunng;
    private FloatingActionButton buttonBack;


    /**
     * <h2> Tim Cirksena </h2>
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_acctivity);


        /** Erstellung der Buttons im Erstellungsmenü
         *  Anschließende Bearbeitung und Backend Logik der Button
         */

        clickAbbrechen = findViewById(R.id.angry_Abbrechen);
        clickTagSpeichern = findViewById(R.id.angry_Tag_speichern);
        clickWeitereÜbunng = findViewById(R.id.angry_Weitere_Uebung);
        buttonBack = findViewById(R.id.button_back_addEdit);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        clickAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        clickWeitereÜbunng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = editDateDialog.getText().toString();
                String gewicht = editTextGewicht.getText().toString();
                String uebung = spinner.getSelectedItem().toString();
                String beschreibung = editTextBeschreibung.getText().toString();
                userSpinnerChoice = spinner.getSelectedItemPosition();
                int wiederholungen = editWiederholungen.getValue();
                int saetze = editSaetze.getValue();
                int schwierigkeit = editSchwierigkeit.getValue();

                Exercise exercise = new Exercise(uebung, date, beschreibung, schwierigkeit, wiederholungen, saetze, gewicht, userSpinnerChoice);
                openNewAddActivity(exercise);
            }
        });
        clickTagSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        /** BEARBEITUNG DATEN INPUT
         *
         *
         *
         Anlegen der Views */
        editDateDialog = (TextView) findViewById(R.id.editTextDate);
        editDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddEditUebungActivity.this,
                        R.style.Theme_Material3_Light_Dialog_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = new String();
                if (month < 10 || dayOfMonth < 10) {
                    if (month < 10 && dayOfMonth < 10) {
                        date = "0" + dayOfMonth + ".0" + month + "." + year;
                    }
                    if (month < 10 && dayOfMonth > 9) {
                        date = "0" + dayOfMonth + "." + month + "." + year;
                    }
                    if (month < 10 && dayOfMonth > 9) {
                        date = dayOfMonth + ".0" + month + "." + year;
                    }
                } else {
                    date = dayOfMonth + "." + month + "." + year;
                }
                Log.d("Formatierung Date", date);
                editDateDialog.setText(date);
            }
        };
        initialisierungViews();

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        /** In diesem Fall existiert das Object schon und wir wollen es nur verändern */
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            /** Damit spinner richtige Value anzeigt */
            String text = intent.getStringExtra(EXTRA_UEBUNG);
            int i = adapter.getPosition(text);
            spinner.setSelection(i);
            editTextGewicht.setText(intent.getStringExtra(EXTRA_GEWICHT));
            spinner.setSelection(intent.getIntExtra(EXTRA_POS_SPINNER, 0));
            editTextBeschreibung.setText(intent.getStringExtra(EXTRA_BESCHREIBUNG));
            editSchwierigkeit.setValue(intent.getIntExtra(EXTRA_SCHWIERIGKEIT, 1));
            editSaetze.setValue(intent.getIntExtra(EXTRA_SAETZE, 1));
            editWiederholungen.setValue(intent.getIntExtra(EXTRA_WIEDERHOLUNGEN, 1));
            editDateDialog.setText(intent.getStringExtra(EXTRA_DATUM));
            editTextVorlage.setText(intent.getStringExtra(EXTRA_VORLAGE));
        } else {
            setTitle("Add Note");
        }
    }

    /**
     * Logik hinter den Views, wie werden die Daten weitergeleitet
     */
    private void saveNote() {
        String vorlage = editTextVorlage.getText().toString();
        String date = editDateDialog.getText().toString();
        String gewicht = editTextGewicht.getText().toString();
        String uebung = spinner.getSelectedItem().toString();
        String beschreibung = editTextBeschreibung.getText().toString();
        userSpinnerChoice = spinner.getSelectedItemPosition();
        int wiederholungen = editWiederholungen.getValue();
        int saetze = editSaetze.getValue();
        int schwierigkeit = editSchwierigkeit.getValue();

        if (uebung.trim().isEmpty() || beschreibung.trim().isEmpty() || date.isEmpty() || gewicht.isEmpty() || vorlage.isEmpty()) {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_VORLAGE, vorlage);
        data.putExtra(EXTRA_DATUM, date);
        data.putExtra(EXTRA_GEWICHT, gewicht);
        data.putExtra(EXTRA_POS_SPINNER, userSpinnerChoice);
        data.putExtra(EXTRA_TITLE, uebung);
        data.putExtra(EXTRA_BESCHREIBUNG, beschreibung);
        data.putExtra(EXTRA_SCHWIERIGKEIT, schwierigkeit);
        data.putExtra(EXTRA_SAETZE, saetze);
        data.putExtra(EXTRA_WIEDERHOLUNGEN, wiederholungen);

        /** Wichtig für die Bearbeitung bereits vorhandenen Entitäten */
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        /** In diesem Fall exisitiert eine Übung*/
        if (id != -1) {
            setResult(78, data);
            data.putExtra(EXTRA_ID, id);
        } else {
            /** In diesem Fall exisitiert die Übung noch nicht */
            setResult(77, data);
        }
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    /** <h2>Abteilung Hilfsmethoden</h2> */
    public void openMainActivity() {
        Intent intent = new Intent(this, ListUebungActivity.class);
        startActivity(intent);
    }

    public void openNewAddActivity(Exercise exercise) {
        ExerciseViewModel exerciseViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);
        exerciseViewModel.insert(exercise);
        Intent intent = new Intent(this, AddEditUebungActivity.class);
        startActivity(intent);
    }

    public void initialisierungViews() {
        editTextVorlage = findViewById(R.id.edit_Text_Vorlage);
        adapter = ArrayAdapter.createFromResource(this, R.array.uebungen, R.layout.support_simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner_uebung);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        editTextGewicht = findViewById(R.id.edit_Text_Gewicht);
        editTextBeschreibung = findViewById(R.id.edit_text_description);
        editSchwierigkeit = findViewById(R.id.number_picker_schwierigkeit);
        editSaetze = findViewById(R.id.number_picker_saetze);
        editWiederholungen = findViewById(R.id.number_picker_wiederholung);
        uebungen = getResources().getStringArray(R.array.uebungen);
        //Number picker
        editSchwierigkeit.setMinValue(1);
        editSchwierigkeit.setMaxValue(10);
        editWiederholungen.setMinValue(1);
        editWiederholungen.setMaxValue(25);
        editSaetze.setMinValue(1);
        editSaetze.setMaxValue(10);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        clickAbbrechen = findViewById(R.id.angry_Abbrechen);
        clickTagSpeichern = findViewById(R.id.angry_Tag_speichern);
        clickWeitereÜbunng = findViewById(R.id.angry_Weitere_Uebung);
        buttonBack = findViewById(R.id.button_back_addEdit);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        clickAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        clickTagSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }


}

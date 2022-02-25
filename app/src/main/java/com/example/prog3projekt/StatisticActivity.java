package com.example.prog3projekt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.example.prog3projekt.Home.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    private TextView minDate;
    private TextView maxDate;
    private DatePickerDialog.OnDateSetListener minDateSetListener;
    private DatePickerDialog.OnDateSetListener maxDateSetListener;
    ArrayAdapter<CharSequence> adapter;
    private Spinner spinner;
    ExerciseViewModel viewModel;
    private GraphView graphView;
    private Button buttonDisplay;
    String[] übungenArray;
    String dateFormatMin;
    String dateFormatMax;
    private FloatingActionButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        übungenArray = getResources().getStringArray(R.array.uebungen_statistik);
        minDate = (TextView) findViewById(R.id.text_date_min);
        maxDate = (TextView) findViewById(R.id.text_date_max);
        adapter = ArrayAdapter.createFromResource(this, R.array.uebungen_statistik, R.layout.support_simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner_statistik_übung);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);
        graphView = findViewById(R.id.graphViewStatistik);
        buttonDisplay = findViewById(R.id.button_statistik_anzeigen);

        graphView.setVisibility(View.INVISIBLE);
        minDate.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StatisticActivity.this,
                        R.style.Theme_Material3_Light_Dialog_MinWidth, minDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();

            }
        });

        maxDate.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StatisticActivity.this,
                        R.style.Theme_Material3_Light_Dialog_MinWidth, maxDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();

            }
        });
        minDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = DataTimeConverter.addZerosToDate(dayOfMonth, month, year);
                minDate.setText(date);
                dateFormatMin = date;
            }
        };
        maxDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = DataTimeConverter.addZerosToDate(dayOfMonth, month, year);
                Log.d("Formatierung Date", date);
                maxDate.setText(date);
                dateFormatMax = date;
            }
        };
        buttonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
                Date minD = new Date(DataTimeConverter.getYearFromDate(minDate.getText().toString()) - 1900, DataTimeConverter.getMonthFromDate(minDate.getText().toString()) - 1, DataTimeConverter.getDayFromDate(minDate.getText().toString()));
                Date maxD = new Date(DataTimeConverter.getYearFromDate(maxDate.getText().toString()) - 1900, DataTimeConverter.getMonthFromDate(maxDate.getText().toString()) - 1, DataTimeConverter.getDayFromDate(maxDate.getText().toString()));
                Calendar start = Calendar.getInstance();
                start.setTime(minD);
                Calendar end = Calendar.getInstance();
                end.setTime(maxD);


                if (!minDate.getText().toString().equals("") && !maxDate.getText().toString().equals("") && allInputsCorrect(dateFormatMin, dateFormatMax)) {
                    if (übungenArray[spinner.getSelectedItemPosition()].equals("All Exercises")) {
                        viewModel.getAllExercisesInBetweenDates(DataTimeConverter.getDayFromDate(dateFormatMin),
                                DataTimeConverter.getMonthFromDate(dateFormatMin),
                                DataTimeConverter.getYearFromDate(dateFormatMin),
                                DataTimeConverter.getDayFromDate(dateFormatMax),
                                DataTimeConverter.getMonthFromDate(dateFormatMax),
                                DataTimeConverter.getYearFromDate(dateFormatMax))
                                .observe(StatisticActivity.this, new Observer<List<Exercise>>() {
                                            @Override
                                            public void onChanged(@Nullable List<Exercise> exercises) {
                                                {
                                                    int anzahlTage = 0;
                                                    for (Date j = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), j = start.getTime()) {
                                                        anzahlTage++;
                                                    }
                                                    //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
                                                    Date minD = new Date(DataTimeConverter.getYearFromDate(minDate.getText().toString()) - 1900, DataTimeConverter.getMonthFromDate(minDate.getText().toString()) - 1, DataTimeConverter.getDayFromDate(minDate.getText().toString()));
                                                    Date maxD = new Date(DataTimeConverter.getYearFromDate(maxDate.getText().toString()) - 1900, DataTimeConverter.getMonthFromDate(maxDate.getText().toString()) - 1, DataTimeConverter.getDayFromDate(maxDate.getText().toString()));
                                                    Calendar start = Calendar.getInstance();
                                                    start.setTime(minD);
                                                    Calendar end = Calendar.getInstance();
                                                    end.setTime(maxD);
                                                    int max = 1;
                                                    DataPoint[] s = new DataPoint[anzahlTage];
                                                    int iterator = 0;

                                                    for (Date j = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), j = start.getTime()) {

                                                        s[iterator] = new DataPoint(j, 0);
                                                        for (int i = 0; i < exercises.size(); i++) {
                                                            if (exercises.get(i).getDatum().equals(DataTimeConverter.formattedDate(j))) {
                                                                s[iterator] = new DataPoint(j, s[iterator].getY() + exercises.get(i).getSchwierigkeit());
                                                            }
                                                        }
                                                        if (max < s[iterator].getY()) {
                                                            max = (int) s[iterator].getY();
                                                        }
                                                        iterator++;
                                                    }
                                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(s);
                                                    GraphViewHelper.setUpGraphViewWithDate(graphView, minD, maxD, max, StatisticActivity.this);
                                                    GraphViewHelper.updateGraphView(graphView, series);
                                                }
                                            }
                                        }
                                );
                    } else {
                        viewModel.getAllExercisesInBetween(DataTimeConverter.getDayFromDate(dateFormatMin),
                                DataTimeConverter.getMonthFromDate(dateFormatMin),
                                DataTimeConverter.getYearFromDate(dateFormatMin),
                                DataTimeConverter.getDayFromDate(dateFormatMax),
                                DataTimeConverter.getMonthFromDate(dateFormatMax),
                                DataTimeConverter.getYearFromDate(dateFormatMax),
                                übungenArray[spinner.getSelectedItemPosition()]
                        )
                                .observe(StatisticActivity.this, new Observer<List<Exercise>>() {
                                            @Override
                                            public void onChanged(@Nullable List<Exercise> exercises) {
                                                Log.d("Spinner Selection: ", übungenArray[spinner.getSelectedItemPosition()]);
                                                Date minD = new Date(DataTimeConverter.getYearFromDate(minDate.getText().toString()) - 1900, DataTimeConverter.getMonthFromDate(minDate.getText().toString()) - 1, DataTimeConverter.getDayFromDate(minDate.getText().toString()));
                                                Date maxD = new Date(DataTimeConverter.getYearFromDate(maxDate.getText().toString()) - 1900, DataTimeConverter.getMonthFromDate(maxDate.getText().toString()) - 1, DataTimeConverter.getDayFromDate(maxDate.getText().toString()));
                                                int max = 1;
                                                int anzahlTage = 0;
                                                for (Date j = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), j = start.getTime()) {
                                                    anzahlTage++;
                                                }
                                                DataPoint[] s = new DataPoint[anzahlTage];

                                                //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
                                                Calendar start = Calendar.getInstance();
                                                start.setTime(minD);
                                                Calendar end = Calendar.getInstance();
                                                end.setTime(maxD);
                                                int iterator = 0;

                                                for (Date j = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), j = start.getTime()) {

                                                    s[iterator] = new DataPoint(j, 0);
                                                    for (int i = 0; i < exercises.size(); i++) {
                                                        if (exercises.get(i).getDatum().equals(DataTimeConverter.formattedDate(j))) {
                                                            Log.d(exercises.get(i).getDatum() + " equals", DataTimeConverter.formattedDate(j));
                                                            s[iterator] = new DataPoint(j, s[iterator].getY() + exercises.get(i).getSchwierigkeit());
                                                        }
                                                    }
                                                    if (max < s[iterator].getY()) {
                                                        max = (int) s[iterator].getY();
                                                    }
                                                    iterator++;
                                                }
                                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(s);
                                                GraphViewHelper.setUpGraphViewWithDate(graphView, minD, maxD, max, StatisticActivity.this);
                                                GraphViewHelper.updateGraphView(graphView, series);
                                            }
                                        }
                                );

                    }
                    graphView.setVisibility(View.VISIBLE);
                }
            }
        });

        FloatingActionButton buttonBack = findViewById(R.id.button_back_stat);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean allInputsCorrect(String minDate, String maxDate) {
        if (DataTimeConverter.getYearFromDate(minDate) == DataTimeConverter.getYearFromDate(maxDate)) {
            if (DataTimeConverter.getMonthFromDate(minDate) <= DataTimeConverter.getMonthFromDate(maxDate)) {
                if (DataTimeConverter.getDayFromDate(minDate) < DataTimeConverter.getDayFromDate(maxDate) || DataTimeConverter.getYearFromDate(minDate) < DataTimeConverter.getYearFromDate(maxDate) || DataTimeConverter.getYearFromDate(minDate) == DataTimeConverter.getYearFromDate(maxDate) && DataTimeConverter.getMonthFromDate(minDate) < DataTimeConverter.getMonthFromDate(maxDate)) {
                    return true;
                } else {
                    Toast.makeText(StatisticActivity.this, "Der tag des Anfangsdatums darf nicht hinter dem Tag des Enddatums liegen.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(StatisticActivity.this, "Der Monat des Anfangsdatums darf nicht höher sein, als der des Enddatums", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(StatisticActivity.this, "Es können nur Intervalle inheralb eines Jahres eingetragen werden.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
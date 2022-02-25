package com.example.prog3projekt.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.CalendarAdapter;
import com.example.prog3projekt.DataTimeConverter;
import com.example.prog3projekt.Date;
import com.example.prog3projekt.DayExercisesActivity;
import com.example.prog3projekt.ExerciseDB.OnCalendarItemClickListener;
import com.example.prog3projekt.GraphViewHelper;
import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.example.prog3projekt.R;
import com.example.prog3projekt.StartTrainingActivity;
import com.example.prog3projekt.StatisticActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnCalendarItemClickListener {
    Button heute;
    GraphView graphView;
    ExerciseViewModel exerciseViewModel;
    CalendarAdapter adapter;
    public static String EXTRADATUM = "com.example.prog3projekt.Home.EXTRA_DATUM";

    /** <h2>Tom Sattler</h2>
     * Start-Screen nachdem Splash-Screen*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        heute = findViewById(R.id.heuteBtn);
        graphView = findViewById(R.id.graphView);
        RecyclerView recyclerView = findViewById(R.id.calendar_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        adapter = new CalendarAdapter(this);
        exerciseViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ExerciseViewModel.class);


        ArrayList<Date> listDatesInMonth = new ArrayList<Date>();
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < DataTimeConverter.getAmountDay(); i++) {
            listDatesInMonth.add(new Date(i, false));
        }
        adapter.setDaten(listDatesInMonth);

        exerciseViewModel.getExerciseForDate(0, DataTimeConverter.getMonth(), DataTimeConverter.getYear()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                boolean changed = false;
                for (int i = 0; i < DataTimeConverter.getAmountDay(); i++) {
                    changed = false;
                    for (int j = 0; j < exercises.size(); j++) {
                        if (exercises.get(j).getTag() - 1 == i) {
                            adapter.getDatumAt(i).setTrained(true);
                            changed = true;

                        }
                    }
                    if (!changed) {
                        adapter.getDatumAt(i).setTrained(false);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        exerciseViewModel.getExerciseForDate(01, DataTimeConverter.getMonth(), DataTimeConverter.getYear()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                int max = 0;
                DataPoint[] s = new DataPoint[DataTimeConverter.getAmountDay()];
                for (int j = 0; j < DataTimeConverter.getAmountDay(); j++) {
                    s[j] = new DataPoint(j, 0);
                    for (int i = 0; i < exercises.size(); i++) {
                        if (exercises.get(i).getTag() == j) {
                            s[j] = new DataPoint(j, s[j].getY() + exercises.get(i).getSchwierigkeit());
                        }
                    }
                    if (max < s[j].getY()) {
                        max = (int) s[j].getY();
                    }
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(s);
                GraphViewHelper.setUpGraphView(graphView, DataTimeConverter.getAmountDay(), max);
                GraphViewHelper.updateGraphView(graphView, series);
            }
        });

        /** Button zur "Erstellung" eines neuen Tages" */
        heute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StartTrainingActivity.class);
                startActivity(intent);
            }
        });
        /** OnClickListener auf der Statistik, zur ausführlicheren Statistik in der Statistik Activity */
        graphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
        /** Zum Testen, sonst alle Übungen immer einzeln inserten */
        //addSimulationExercises();
    }

    void addSimulationExercises() {
        exerciseViewModel.insert(new Exercise("Bench Press", "01.02.2022", "gut", 20, 10, 3, "100", 5));
        exerciseViewModel.insert(new Exercise("Bench Press", "05.02.2022", "gut", 30, 10, 3, "100", 5));
        exerciseViewModel.insert(new Exercise("Bench Press", "08.02.2022", "gut", 40, 10, 3, "100", 6));
        exerciseViewModel.insert(new Exercise("Bench Press", "11.02.2022", "gut", 50, 10, 3, "100", 3));
        exerciseViewModel.insert(new Exercise("Bench Press", "15.02.2022", "gut", 60, 10, 3, "100", 2));
        exerciseViewModel.insert(new Exercise("Bench Press", "21.02.2022", "gut", 70, 10, 3, "100", 5));
        exerciseViewModel.insert(new Exercise("Bench Press", "25.02.2022", "gut", 80, 10, 3, "100", 1));
        exerciseViewModel.insert(new Exercise("Bench Press", "25.02.2022", "gut", 80, 10, 3, "100", 1));
    }

    @Override
    public void onItemClick(Date datum) {
        Intent intent = new Intent(this, DayExercisesActivity.class);
        intent.putExtra(EXTRADATUM, datum.getDay());
        startActivity(intent);
    }
}
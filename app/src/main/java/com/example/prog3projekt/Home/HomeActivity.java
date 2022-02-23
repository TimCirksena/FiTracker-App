package com.example.prog3projekt.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.CalendarAdapter;
import com.example.prog3projekt.DataTimeConverter;
import com.example.prog3projekt.Datum;
import com.example.prog3projekt.DayExercisesActivity;
import com.example.prog3projekt.ExerciseDB.OnCalendarItemClickListener;
import com.example.prog3projekt.MainActivity;
import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.ExerciseDB.ExerciseViewModel;
import com.example.prog3projekt.R;
import com.example.prog3projekt.statistikActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity  implements OnCalendarItemClickListener {
    Button heute;
    GraphView graphView;
    List <Exercise> allExercises;
    ExerciseViewModel exerciseViewModel;
    CalendarAdapter adapter;
    public static String EXTRADATUM = "com.example.prog3projekt.Home.EXTRA_DATUM";
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


        ArrayList<Datum> listDatesInMonth = new ArrayList<Datum>();
        recyclerView.setAdapter(adapter);
        for(int i=0;i< DataTimeConverter.getAmountDay();i++){
            listDatesInMonth.add(new Datum(i, false));
        }
        adapter.setDaten(listDatesInMonth);

        exerciseViewModel.getExerciseForDate(0, DataTimeConverter.getMonth(), DataTimeConverter.getYear()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                boolean changed = false;
                for(int i=0;i< DataTimeConverter.getAmountDay();i++) {
                    changed = false;
                    for (int j = 0; j < exercises.size(); j++) {
                        if (exercises.get(j).getTag()-1 == i) {
                            adapter.getDatumAt(i).setTrained(true);
                            changed=true;

                        }
                    }
                    if(!changed){
                        adapter.getDatumAt(i).setTrained(false);
                    }
                    adapter.notifyDataSetChanged();
                }
                }
        });

        exerciseViewModel.getExerciseForDate(01, DataTimeConverter.getMonth(), DataTimeConverter.getYear()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                DataPoint[] s = new DataPoint[DataTimeConverter.getAmountDay()];
                for(int j = 0; j<DataTimeConverter.getAmountDay();j++) {
                    s[j] = new DataPoint(j,0);
                    for (int i = 0; i < exercises.size(); i++) {
                        if(exercises.get(i).getTag()==j) {
                            s[j] = new DataPoint(j, s[j].getY()+exercises.get(i).getSchwierigkeit());
                        }
                    }
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(s);
                setUpGraphView(graphView, DataTimeConverter.getAmountDay(), 100);
                updateGraphView(graphView, series);
            }
        });

        //Button zur "Erstellung" eines neuen Tages"
        heute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent); }
            });
        //OnClickListener auf der Statistik, zur ausführlicheren Statistik in der Statistik Activity
        graphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, statistikActivity.class);
                startActivity(intent);
            }
        });
        addSimulationExercises();
    }

    void addSimulationExercises(){
        exerciseViewModel.insert(new Exercise("Übung","01.02.2022","gut",20,10,3,"100",5));
        exerciseViewModel.insert(new Exercise("Übung","05.02.2022","gut",30,10,3,"100",5));
        exerciseViewModel.insert(new Exercise("Übung","08.02.2022","gut",40,10,3,"100",6));
        exerciseViewModel.insert(new Exercise("Übung","11.02.2022","gut",50,10,3,"100",3));
        exerciseViewModel.insert(new Exercise("Übung","15.02.2022","gut",60,10,3,"100",2));
        exerciseViewModel.insert(new Exercise("Übung","21.02.2022","gut",70,10,3,"100",5));
        exerciseViewModel.insert(new Exercise("Übung","25.02.2022","gut",80,10,3,"100",1));
        exerciseViewModel.insert(new Exercise("Übung","25.02.2022","gut",80,10,3,"100",1));
    }

    //Hilfsmethode um den Graphen mit der richtigen größe(x- und y-Achse) und dem richtigen UI zu bauen
    private void setUpGraphView(GraphView graphView, int maxX, int maxY){
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(maxY);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(maxX);
        graphView.setTitleColor(R.color.main_color);
        graphView.setTitleTextSize(18);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
    }
    //Hilsmethoden um bei upgedatetem DataSet den Graphen neu zu zeichnen
    private void updateGraphView(GraphView graphView, LineGraphSeries<DataPoint> s){
        graphView.removeAllSeries();
        s.setThickness(30);
        graphView.addSeries(s);
    }

    @Override
    public void onItemClick(Datum datum) {
        Intent intent = new Intent(this, DayExercisesActivity.class);
        intent.putExtra(EXTRADATUM, datum.getDay());
        startActivity(intent);
    }
}
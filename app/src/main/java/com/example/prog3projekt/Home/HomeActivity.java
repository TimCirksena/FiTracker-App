package com.example.prog3projekt.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.prog3projekt.MainActivity;
import com.example.prog3projekt.NoteDB.Note;
import com.example.prog3projekt.NoteDB.NoteViewModel;
import com.example.prog3projekt.R;
import com.example.prog3projekt.statistikActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Button heute;
    GraphView graphView;
    List <Note> allNotes;
    NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        heute = findViewById(R.id.heuteBtn);
        graphView = findViewById(R.id.graphView);
        graphViewInitialisieren(30, 100);

        //Button zur "Erstellung" eines neuen Tages"
        heute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //OnClickListener auf der Statistik, zur ausführlicheren Statistik in der Statistik Activity
        graphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, statistikActivity.class);
                startActivity(intent);
            }
        });
    }



    // https://github.com/jjoe64/GraphView/wiki/Style-options Dokumentation von der GraphView
    void graphViewInitialisieren(int maxX, int maxY){
        graphView = findViewById(R.id.graphView);
        noteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            //onChanged, ebenso wie bei der MainActivity, wird der Graph verändert wenn sich das DataSet verändert.
            public void onChanged(@Nullable List<Note> notes) {
                allNotes = notes;
                DataPoint[] s = new DataPoint[allNotes.size()];
                for (int i = 0; i<allNotes.size();i++) {
                    //s[i] = new DataPoint(DataTimeHilfsKlasse.getDatesDayAsInt(allNotes.get(i).getDatum()), allNotes.get(i).getSchwierigkeit());
                    sortDates(s);
                }
                sortDates(s);
                Series<DataPoint> series = new LineGraphSeries<>(s);
                setUpGraphView(graphView, maxX, maxY);
                updateGraphView(graphView, series);
            }
        });

    }
    private void sortDates(DataPoint [] s){
        for(int i = 0; i<s.length; i++){
            for(int j = 0; j<s.length; j++){
                if(s[i].getX()>s[j].getX()){
                    DataPoint temp = s[j];
                    s[j] = s[i];
                    s[i] = temp;
                }
            }
        }
    }
    //Hilfsmethode um den Graphen zu zeichnen
    private void setUpGraphView(GraphView graphView, int maxX, int maxY){
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(maxY);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(maxX);
        graphView.setTitleColor(R.color.main_color);
        graphView.setTitleTextSize(18);
    }
    //Hilsmethoden um bei upgedatetem DataSet den Graphen neu zu zeichnen
    private void updateGraphView(GraphView graphView, Series<DataPoint> s){
        graphView.addSeries(s);
    }
}
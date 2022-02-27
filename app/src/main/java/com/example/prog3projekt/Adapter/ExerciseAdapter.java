package com.example.prog3projekt.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.Interface.OnItemClickListener;
import com.example.prog3projekt.R;

import java.util.ArrayList;
import java.util.List;

/** Tim Cirksena & Tom Sattler
 * Adapter ViewHolder der eindimensionalen RecyclerView Listen*/
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {
    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListener listener;
    private ViewGroup x;

    /** Die View die wir später an den Context weitergeben, -> Listexercises_activity */
    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        x = parent;
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);
        return new ExerciseHolder(itemView);
    }
    /**assigning values to the views we created in the recycler_view layout file
    based on the postion of the recycler view
    Was auf dem Screen angezeigt wird */
    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.textViewUebung.setText(currentExercise.getName());
        holder.textViewBeschreibung.setText(currentExercise.getDatum());
        holder.textViewGewicht.setText(String.valueOf(currentExercise.getSchwierigkeit()));
    }
    /**the recycler view wants to know the number of item we're passing */
    @Override
    public int getItemCount() {
        Log.d("note.size", exercises.size() + "");
        return exercises.size();
    }
    /** Ändern der Liste und benachrichtigen das diese geändert wurde */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }
    /** Hilfsmethode um die Position unsere Exercise an den ItemTouchHelper zu übergeben
     * grabbing the views from our recycler_view layout
     Kinda like the in the onCreate method*/
    public Exercise getExercisesAt(int position) {
        return exercises.get(position);
    }


    /** ViewHolder Klasse die zum verwalten eines einzelnen Elementes konstruiert wird*/
    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewUebung;
        private TextView textViewBeschreibung;
        private TextView textViewGewicht;

        /** Konstruktor */
        public ExerciseHolder(View itemView) {
            super(itemView);
            itemView.setBackground(x.getResources().getDrawable(R.drawable.buttonshape));
            textViewUebung = itemView.findViewById(R.id.text_view_title);
            textViewBeschreibung = itemView.findViewById(R.id.text_view_description);
            textViewGewicht = itemView.findViewById(R.id.text_view_priority);

            /** Für das Anpassen der UI der Exercises
            FÜrs EDIT der Exercises */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(exercises.get(position));
                    }
                }
            });
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

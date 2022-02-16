package com.example.prog3projekt.ExerciseDB;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {
    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListner listner;

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Die View die wir später an den Context weitergeben, -> Main_activity
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        //assigning values to the views we created in the recycler_view layout file
        //based on the postion of the recycler view
        //Was auf dem Screen angezeigt wird
        Exercise currentExercise = exercises.get(position);
        holder.textViewUebung.setText(currentExercise.getName());
        holder.textViewBeschreibung.setText(currentExercise.getDatum());
        holder.textViewGewicht.setText(String.valueOf(currentExercise.getSchwierigkeit()));
    }

    @Override
    public int getItemCount() {
        //the recycler view wants to know the number of item we're passing
        Log.d("note.size", exercises.size() + "");
        return exercises.size();
    }

    public void setNotes(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    //Hilfsmethode um die Position unsere Note an den ItemTouchHelper zu übergeben
    public Exercise getNoteAt(int position) {
        return exercises.get(position);
    }
    //grabbing the views from our recycler_view layout
    //Kinda like the in the onCreate method

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewUebung;
        private TextView textViewBeschreibung;
        private TextView textViewGewicht;

        public ExerciseHolder(View itemView) {
            super(itemView);
            /* TODO:HIER ANPASSEN */
            textViewUebung = itemView.findViewById(R.id.text_view_title);
            textViewBeschreibung = itemView.findViewById(R.id.text_view_description);
            textViewGewicht = itemView.findViewById(R.id.text_view_priority);

            //FÜrs EDIT der Notes
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(exercises.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(Exercise exercise);

    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }
}

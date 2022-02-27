package com.example.prog3projekt.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.ExerciseDB.Exercise;
import com.example.prog3projekt.R;

import java.util.ArrayList;
import java.util.List;
/** Tim Cirksena
 * Adapter ViewHolder zur eindimensionalen Template RecyclerView*/

public class ExerciseVorlageAdapter extends RecyclerView.Adapter<ExerciseVorlageAdapter.ExerciseHolder> {
    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListner listner;
    ViewGroup x;

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Die View die wir später an den Context weitergeben, -> Main_activity
        x = parent;
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_item, parent, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        //assigning values to the views we created in the recycler_view layout file
        //based on the postion of the recycler view
        //Was auf dem Screen angezeigt wird
        Exercise currentExercise = exercises.get(position);
        if (currentExercise.getVorlage() != null) {
            holder.textViewVorlageTitle.setText(currentExercise.getVorlage());
        }
    }

    @Override
    public int getItemCount() {
        //the recycler view wants to know the number of item we're passing
        Log.d("note.size", exercises.size() + "");
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    //Hilfsmethode um die Position unsere Note an den ItemTouchHelper zu übergeben
    public Exercise getExercisesAt(int position) {
        return exercises.get(position);
    }
    //grabbing the views from our recycler_view layout
    //Kinda like the in the onCreate method

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewVorlageTitle;
        private TextView textViewBeschreibung;

        public ExerciseHolder(View itemView) {
            super(itemView);
            /* TODO:HIER ANPASSEN */
            textViewVorlageTitle = itemView.findViewById(R.id.text_view_vorlage_title);
            itemView.setBackground(x.getResources().getDrawable(R.drawable.buttonshape));
            textViewVorlageTitle.setTextColor(x.getResources().getColor(R.color.white));
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


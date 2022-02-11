package com.example.prog3projekt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListner listner;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Die View die wir später an den Context weitergeben, -> Main_activity
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        //assigning values to the views we created in the recycler_view layout file
        //based on the postion of the recycler view
        //Was auf dem Screen angezeigt wird
        Note currentNote = notes.get(position);
        holder.textViewUebung.setText(currentNote.getName());
        holder.textViewBeschreibung.setText(currentNote.getDatum());
        holder.textViewGewicht.setText(String.valueOf(currentNote.getSchwierigkeit()));
    }

    @Override
    public int getItemCount() {
        //the recycler view wants to know the number of item we're passing
        Log.d("note.size", notes.size() + "");
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    //Hilfsmethode um die Position unsere Note an den ItemTouchHelper zu übergeben
    public Note getNoteAt(int position) {
        return notes.get(position);
    }
    //grabbing the views from our recycler_view layout
    //Kinda like the in the onCreate method

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewUebung;
        private TextView textViewBeschreibung;
        private TextView textViewGewicht;

        public NoteHolder(View itemView) {
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
                        listner.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(Note note);

    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }
}

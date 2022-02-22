package com.example.prog3projekt;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.ExerciseDB.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private List<Datum> daten = new ArrayList<>();
    private OnItemClickListener listener;
    private String [] kalenderTage;
    ViewGroup x;

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View calendarView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_field, parent, false);
        this.x = parent;
        kalenderTage = x.getResources().getStringArray(R.array.kalendertage);
        return new CalendarViewHolder(calendarView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.bind(daten.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return this.daten.size();
    }

    public void setDaten(List<Datum> daten) {
        this.daten = daten;
        notifyDataSetChanged();
    }
    public Datum getDatumAt(int position) {
        return daten.get(position);
    }


    class CalendarViewHolder extends RecyclerView.ViewHolder{
        TextView valueTextView;

        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);
            valueTextView = itemView.findViewById(R.id.feld_value);
        }
        public void bind(final Datum datum){
                    valueTextView.setText(kalenderTage[datum.getDay()]);
                    valueTextView.setTextColor(Color.BLACK);
                    if(datum.trained){
                    itemView.setBackgroundColor(Color.BLUE);
                    }
                    else{
                        itemView.setBackgroundColor(Color.CYAN);
                    }
            }
        }
    }
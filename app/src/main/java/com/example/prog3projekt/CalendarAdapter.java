package com.example.prog3projekt;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.ExerciseDB.OnCalendarItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private List<Date> daten = new ArrayList<>();
    private OnCalendarItemClickListener listener;
    private String[] kalenderTage;
    ViewGroup x;
    /** <h2>Tom Sattler</h2> */
    public CalendarAdapter(OnCalendarItemClickListener clickListener) {
        listener = clickListener;
    }

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

    public void setDaten(List<Date> daten) {
        this.daten = daten;
        notifyDataSetChanged();
    }

    public Date getDatumAt(int position) {
        return daten.get(position);
    }


    class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            valueTextView = itemView.findViewById(R.id.feld_value);
        }

        public void bind(final Date datum) {
            valueTextView.setText(kalenderTage[datum.getDay()]);
            valueTextView.setTextColor(Color.WHITE);
            if (datum.trained) {
                itemView.setBackground(x.getResources().getDrawable(R.drawable.calendar_item_shape_trained));
            } else {
                itemView.setBackground(x.getResources().getDrawable(R.drawable.calendar_item_shape));
            }
            if (datum.getDay() == DataTimeConverter.getDay() - 1) {
                itemView.setBackground(x.getResources().getDrawable(R.drawable.calendar_item_shape_today));
            }
            itemView.setOnClickListener(view -> {
                listener.onItemClick(datum);
            });
        }
    }
}
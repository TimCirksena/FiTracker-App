package com.example.prog3projekt.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prog3projekt.HelperClasses.Date;
import com.example.prog3projekt.Interface.OnCalendarItemClickListener;
import com.example.prog3projekt.HelperClasses.DataTimeConverter;
import com.example.prog3projekt.R;

import java.util.ArrayList;
import java.util.List;
/** <h2>Tom Sattler</h2>
 * Adapter ViewHolder der zweidimensionalen Kalender RecyclerView */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private List<Date> daten = new ArrayList<>();
    private OnCalendarItemClickListener listener;
    private String[] kalenderTage;
    ViewGroup x;


    /** Konstruktor weißt Interface OnClickListener zu*/
    public CalendarAdapter(OnCalendarItemClickListener clickListener) {
        listener = clickListener;
    }



    /**CalendarViewHolder weißt die Werte aus den Ressourcen den Instanzvariablen zu:
     *      kalenderTage -> R.array.kalendertage
     *      calendarView -> R.layout.calendar_item.xml
     *      */
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View calendarView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        this.x = parent;
        kalenderTage = x.getResources().getStringArray(R.array.kalendertage);
        return new CalendarViewHolder(calendarView);
    }


    /** onBindViewholder für Veränderung der Liste durch den ViewHolder*/
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.bind(daten.get(position));
        holder.setIsRecyclable(false);
    }


    /** Hat die Anzahl an getragenen Items in der Liste als Rückgabewert*/
    @Override
    public int getItemCount() {
        return this.daten.size();
    }


    /** Ändert die Instanzvariable date
     * und meldet dass die Daten im ui geändert werden müssen*/
    public void setDaten(List<Date> daten) {
        this.daten = daten;
        notifyDataSetChanged();
    }


    /** Gibt das Datum an einem int position zurück*/
    public Date getDatumAt(int position) {
        return daten.get(position);
    }


    /** Implementierung des ViewHolders*/
    class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        /** Konstruktor bekommt die View von dem zu bearbeitendem Datum zugewiese*/
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            valueTextView = itemView.findViewById(R.id.feld_value);
        }

        /** bind Methode zu Veränderung einzelner Elemente
         * 3 Zustände: trained, untrainer und tag = heute*/
        public void bind(final Date datum) {
            valueTextView.setText(kalenderTage[datum.getDay()]);
            valueTextView.setTextColor(Color.WHITE);
            if (datum.getTrained()) {
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
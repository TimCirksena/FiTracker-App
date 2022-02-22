package com.example.prog3projekt;

import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataTimeHilfsKlasse {
    //return das Date als Date Object -> Calender library
    public static Date getTodaysDateAsDate() {
        return Calendar.getInstance().getTime();
    }
    public static int getDatesDayAsInt(Date date){
        String s = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        Log.d("Date today Day:", s);
        return 1;
    }
}

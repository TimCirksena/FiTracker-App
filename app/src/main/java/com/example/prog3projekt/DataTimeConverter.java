package com.example.prog3projekt;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DataTimeConverter {
    public static String getDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
        String s = curFormater.format(c);
        return s;
    }

    public static int getAmountDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public static int getDayFromDate(String s){
        if(Integer.parseInt(String.valueOf(s.charAt(0)))==0){
            return Integer.parseInt(String.valueOf(s.charAt(1)));
        }
        else{
            int j = Integer.parseInt(String.valueOf(s.charAt(0)))*10 + Integer.parseInt(String.valueOf(s.charAt(1)));
            Log.d("j= ", "+"+j);
            return j;
        }
    }
    public static int getMonthFromDate(String s){
        if(Integer.parseInt(String.valueOf(s.charAt(3)))==0){
            return Integer.parseInt(String.valueOf(s.charAt(4)));
        }
        else{
            return Integer.parseInt(String.valueOf(s.charAt(3)))*10 + Integer.parseInt(String.valueOf(s.charAt(4)));
        }
    }
    public static int getYearFromDate(String s){

            return Integer.parseInt(String.valueOf(s.charAt(6)))*1000 + Integer.parseInt(String.valueOf(s.charAt(7)))*100 + Integer.parseInt(String.valueOf(s.charAt(8)))*10 + Integer.parseInt(String.valueOf(s.charAt(9)));
    }
    public static int getMonth(){
        return getMonthFromDate(getDate());
    }
    public static int getYear(){
        return getYearFromDate(getDate());
    }
}

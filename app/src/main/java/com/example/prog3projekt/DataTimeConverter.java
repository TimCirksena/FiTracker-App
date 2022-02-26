package com.example.prog3projekt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataTimeConverter {
    public static String getDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
        String s = curFormater.format(c);
        return s;
    }

    public static String formattedDate(Date date){
        SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
        String s = curFormater.format(date);
        return s;
    }

    public static int getAmountDaysOfMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public static int getDayFromDate(String s){
            if (Integer.parseInt(String.valueOf(s.charAt(0))) == 0) {
                return Integer.parseInt(String.valueOf(s.charAt(1)));
            }

        else{
            int j = Integer.parseInt(String.valueOf(s.charAt(0)))*10 + Integer.parseInt(String.valueOf(s.charAt(1)));
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
    public static int getDay(){return getDayFromDate(getDate());}
    public static String addZerosToDate(int dayOfMonth, int month, int year){
        String date = new String();
        if (month < 10 || dayOfMonth < 10) {
            if (month < 10 && dayOfMonth < 10) {
                date = "0" + dayOfMonth + ".0" + month + "." + year;
            }
            if (month < 10 && dayOfMonth > 9) {
                date = "0" + dayOfMonth + "." + month + "." + year;
            }
            if (month < 10 && dayOfMonth > 9) {
                date = dayOfMonth + ".0" + month + "." + year;
            }
        } else {
            date = dayOfMonth + "." + month + "." + year;
        }
        return date;
    }
}

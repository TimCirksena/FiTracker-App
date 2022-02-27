package com.example.prog3projekt.HelperClasses;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
 /** <h2>Tom Sattler</h2>
  * HilfsKlasse die Strings oder Integer in die gewünschten Formate konvertiert
  * Es wurde meist mithilfe der Methoden des Java Calendar oder Java Date gea-
  * rbeitet*/
public class DataTimeConverter {
      /** Methode, welche das aktuelle Datum als String liefert*/
    public static String getDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
        String s = curFormater.format(c);
        return s;
    }
     /** Wandelt ein Date Objekt in einen formattierten String um*/
    public static String formattedDate(Date date) {
        SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
        String s = curFormater.format(date);
        return s;
    }
     /** Liefter als Rückgabeparameter die Anzahl der Tage des
      * aktuellen Monats*/
    public static int getAmountDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
     /** Wandelt einen String Datum aus dem Format Tag.Monat.Jahr
      * zu einem int Tag um.*/
    public static int getDayFromDate(String s) {
        if (Integer.parseInt(String.valueOf(s.charAt(0))) == 0) {
            return Integer.parseInt(String.valueOf(s.charAt(1)));
        } else {
            int j = Integer.parseInt(String.valueOf(s.charAt(0))) * 10 + Integer.parseInt(String.valueOf(s.charAt(1)));
            return j;
        }
    }
     /** Wandelt einen String Datum aus dem Format Tag.Monat.Jahr
      * zu einem int Monat um.*/
    public static int getMonthFromDate(String s) {
        if (Integer.parseInt(String.valueOf(s.charAt(3))) == 0) {
            return Integer.parseInt(String.valueOf(s.charAt(4)));
        } else {
            return Integer.parseInt(String.valueOf(s.charAt(3))) * 10 + Integer.parseInt(String.valueOf(s.charAt(4)));
        }
    }
     /** Wandelt einen String Datum aus dem Format Tag.Monat.Jahr
      * zu einem int Jahr um.*/
    public static int getYearFromDate(String s) {

        return Integer.parseInt(String.valueOf(s.charAt(6))) * 1000 + Integer.parseInt(String.valueOf(s.charAt(7))) * 100 + Integer.parseInt(String.valueOf(s.charAt(8))) * 10 + Integer.parseInt(String.valueOf(s.charAt(9)));
    }
     /** Gibt den aktuellen Monat als Integer zurück.*/
    public static int getMonth() {
        return getMonthFromDate(getDate());
    }
     /** Gibt das aktuelle Jahr als Integer zurück*/
    public static int getYear() {
        return getYearFromDate(getDate());
    }
     /** Gibt den aktuellen Tag als Integer zurück*/
    public static int getDay() {
        return getDayFromDate(getDate());
    }
     /** Hilft dabei die von uns implementierte Formatierung von Integer -> String
      * zu behalten
      * */
    public static String addZerosToDate(int dayOfMonth, int month, int year) {
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

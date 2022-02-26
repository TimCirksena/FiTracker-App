package com.example.prog3projekt;

import static org.junit.Assert.*;

import com.example.prog3projekt.HelperClasses.DataTimeConverter;

import org.junit.Test;

import java.util.Date;

public class DataTimeConverterTest {
    /*Aufgrund den Eigenschaften der Java.util.Date Klasse fangen die Jahre bei 1900 an, also 2022=1900*122
      und die Monate fangen wie in einem Array bei 0 an, also Februar gleich Monat 1 */
    @Test
    public void formattedDate() {
        Date date = new Date(122, 1, 11);
        String output = DataTimeConverter.formattedDate(date);
        String expected = "11.02.2022";
        assertEquals(expected, output);
    }
    //Expected muss bei Wechsel des aktuellen Monats angepasst werden
    @Test
    public void getAmountDay() {
        int expected = 28;
        int output = DataTimeConverter.getAmountDay();
        assertEquals(expected, output);
    }
    @Test
    public void getDayFromDate() {
        String input = "11.02.2022";
        int expected = 11;
        int output = DataTimeConverter.getDayFromDate(input);
        assertEquals(expected,output);
    }

    @Test
    public void getMonthFromDate() {
        String input = "11.02.2022";
        int expected = 2;
        int output = DataTimeConverter.getMonthFromDate(input);
        assertEquals(expected,output);
    }

    @Test
    public void getYearFromDate() {
        String input = "11.02.2022";
        int expected = 2022;
        int output = DataTimeConverter.getYearFromDate(input);
        assertEquals(expected,output);
    }
    /* Bei der getMonth, getYear und getDay Methode müssen die Werte der erwarteten integer
       an das heutige Datum angepasst werden, da diese als Rückgabewert den akutellen Tag liefern.*/
    @Test
    public void getMonth() {
        int expected = 2;
        int output = DataTimeConverter.getMonth();
        assertEquals(expected, output);
    }

    @Test
    public void getYear() {
        int expected = 2022;
        int output = DataTimeConverter.getYear();
        assertEquals(expected, output);
    }

    @Test
    public void getDay() {
        int expected = 26;
        int output = DataTimeConverter.getDay();
        assertEquals(expected, output);
    }

    @Test
    public void addZerosToDate() {
        String expected = "07.01.2022";
        String actual = DataTimeConverter.addZerosToDate(7,1,2022);
        assertEquals(expected, actual);
    }
}
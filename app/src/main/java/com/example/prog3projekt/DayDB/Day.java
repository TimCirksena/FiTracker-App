package com.example.prog3projekt.DayDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;


@Entity(tableName = "day_table")
public class Day {



    @PrimaryKey(autoGenerate = true)
    private int dayId;

    @TypeConverters({DateConverter.class})

    private Date date;

    private String vorlage;



    private String datum;

    public Day(String datum){
        this.datum = datum;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVorlage() {
        return vorlage;
    }

    public void setVorlage(String vorlage) {
        this.vorlage = vorlage;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int id) {
        this.dayId = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}

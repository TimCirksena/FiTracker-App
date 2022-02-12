package com.example.prog3projekt.DayDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.prog3projekt.Note;

import java.util.Date;
import java.util.List;

@Entity(tableName = "day_table")
public class Day {

    @PrimaryKey(autoGenerate = true)
    private Date date;

    private List<Note> listExercises;

    private String vorlage;

    public Day(Date date){
        this.date = date;
    }

    public List<Note> getListExercises() {
        return listExercises;
    }
    public void setListExercises(List<Note> listExercises) {
        this.listExercises = listExercises;
    }
    public void addExercise(Note note){
        this.listExercises.add(note);
    }
}

package com.example.prog3projekt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String datum;

    private int schwierigkeit;

    private int wiederholungen;

    private int saetze;

    private int gewicht;

    private int pos;

    public int getPos() {
        return pos;
    }

    public Note(String name, String datum, int schwierigkeit, int wiederholungen, int saetze, int gewicht, int pos) {
        this.name = name;
        this.datum = datum;
        this.schwierigkeit = schwierigkeit;
        this.wiederholungen = wiederholungen;
        this.saetze = saetze;
        this.gewicht = gewicht;
        this.pos = pos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSchwierigkeit(int schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }

    public void setWiederholungen(int wiederholungen) {
        this.wiederholungen = wiederholungen;
    }

    public void setSaetze(int saetze) {
        this.saetze = saetze;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDatum() {
        return datum;
    }

    public int getSchwierigkeit() {
        return schwierigkeit;
    }

    public int getWiederholungen() {
        return wiederholungen;
    }

    public int getSaetze() {
        return saetze;
    }

    public int getGewicht() {
        return gewicht;
    }
}

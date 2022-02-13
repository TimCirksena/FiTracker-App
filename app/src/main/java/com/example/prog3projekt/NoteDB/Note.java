package com.example.prog3projekt.NoteDB;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.prog3projekt.DayDB.Day;

@Entity(tableName = "note_table", foreignKeys = {@ForeignKey(entity = Day.class, parentColumns = "dayId", childColumns = "dateId", onDelete = ForeignKey.CASCADE)})
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int dateId;

    private String datum;

    private String beschreibung;

    private int schwierigkeit;

    private int wiederholungen;

    private int saetze;

    private String gewicht;

    private int pos;



    public Note(String name, String datum, int dateId, String beschreibung, int schwierigkeit, int wiederholungen, int saetze, String gewicht, int pos)  {
        this.name = name;
        this.datum = datum;
        this.beschreibung = beschreibung;
        this.schwierigkeit = schwierigkeit;
        this.wiederholungen = wiederholungen;
        this.saetze = saetze;
        this.gewicht = gewicht;
        this.pos = pos;
        this.dateId = dateId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDateId(int dateId) {
        this.dateId = dateId;
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

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public int getId() {
        return id;
    }

    public String getBeschreibung() {
        return beschreibung;
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

    public String getGewicht() {
        return gewicht;
    }

    public int getPos() {
        return pos;
    }
    public int getDateId() {
        return dateId;
    }
}

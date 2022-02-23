package com.example.prog3projekt.ExerciseDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exersice_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String datum;

    private int datumInt;

    private String beschreibung;

    private int schwierigkeit;

    private int wiederholungen;

    private int saetze;

    private String gewicht;

    private String vorlage = null;

    private int pos;



    public Exercise(String name, String datum,int datumInt,  String beschreibung, int schwierigkeit, int wiederholungen, int saetze, String gewicht, int pos)  {
        this.name = name;
        this.datum = datum;
        this.datumInt = datumInt;
        this.beschreibung = beschreibung;
        this.schwierigkeit = schwierigkeit;
        this.wiederholungen = wiederholungen;
        this.saetze = saetze;
        this.gewicht = gewicht;
        this.pos = pos;
    }

    public void setVorlage(String s){
        this.vorlage=s;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getVorlage(){
        return vorlage;
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

    public int getDatumInt() {
        return datumInt;
    }

    public void setDatumInt(int datumInt) {
        this.datumInt = datumInt;
    }

}

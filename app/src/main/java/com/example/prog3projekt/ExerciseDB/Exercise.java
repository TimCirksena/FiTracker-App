package com.example.prog3projekt.ExerciseDB;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.prog3projekt.DataTimeConverter;

@Entity(tableName = "exersice_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String datum;
    private int tag;
    private int monat;
    private int jahr;
    private String beschreibung;
    private int schwierigkeit;
    private int wiederholungen;
    private int saetze;
    private String gewicht;
    private int pos;
    private String vorlage = null;

    public Exercise(String name, String datum,  String beschreibung, int schwierigkeit, int wiederholungen, int saetze, String gewicht, int pos)  {
        this.name = name;
        this.datum = datum;
        this.beschreibung = beschreibung;
        this.schwierigkeit = schwierigkeit;
        this.wiederholungen = wiederholungen;
        this.saetze = saetze;
        this.gewicht = gewicht;
        this.pos = pos;
        this.tag = DataTimeConverter.getDayFromDate(datum);
        this.monat= DataTimeConverter.getMonthFromDate(datum);
        this.jahr = DataTimeConverter.getYearFromDate(datum);
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

    public int getTag() {
        return tag;
    }

    public void setTag(int tagInt) {
        this.tag = tagInt;
    }

    public int getMonat() {
        return monat;
    }

    public void setMonat(int monatInt) {
        this.monat = monatInt;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahrInt) {
        this.jahr = jahrInt;
    }

}

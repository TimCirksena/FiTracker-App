package com.example.prog3projekt.HelperClasses;

/**
 * Tom Sattler
 * Hilfsklasse durch die, die RecyclerView implementiert werden kann
 */
public class Date {

    private boolean trained;
    private int day;
    /** Konstruktor der Hilfsklasse */
    public Date(int day, boolean trained) {
        this.day = day;
        this.trained = trained;
    }
    /** Implementation von Gettern und Settern*/
    public boolean getTrained() {
        return trained;
    }


    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
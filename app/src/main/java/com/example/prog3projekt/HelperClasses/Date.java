package com.example.prog3projekt.HelperClasses;

public class Date {



    /**
     * Tom Sattler
     */
    private boolean trained;
    private int day;

    public Date(int day, boolean trained) {
        this.day = day;
        this.trained = trained;
    }
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
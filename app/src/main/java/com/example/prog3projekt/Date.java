package com.example.prog3projekt;

public class Date {

    boolean trained;
    int day;
    public Date(int day, boolean trained){
        this.day=day;
        this.trained=trained;
    }
    public void setTrained(boolean trained) {
        this.trained = trained;
    }
    public int getDay(){
        return this.day;
    }
}
package com.example.prog3projekt.VorlageDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "vorlage_table")
public class Vorlage {


    @PrimaryKey(autoGenerate = true)
    private int vorlageId;

    private String name;

    public Vorlage(String name) {
        this.name = name;
    }

    public int getVorlageId() {
        return vorlageId;
    }

    public void setVorlageId(int vorlageId) {
        this.vorlageId = vorlageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

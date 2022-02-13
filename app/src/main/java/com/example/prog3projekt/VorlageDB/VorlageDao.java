package com.example.prog3projekt.VorlageDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prog3projekt.DayDB.Day;
import com.example.prog3projekt.NoteDB.Note;

import java.util.List;

@Dao
public interface VorlageDao {

    @Insert
    void insert(Vorlage vorlage);

    @Update
    void update(Vorlage vorlage);

    @Delete
    void delete(Vorlage vorlage);

    @Query("DELETE FROM note_table")
    void deleteAllVorlagen();

    @Query("SELECT * FROM vorlage_table ORDER BY vorlageId DESC")
    LiveData<List<Day>> getAllVorlage();
}

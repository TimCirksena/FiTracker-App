package com.example.prog3projekt.ExerciseDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exersice_table")
    void deleteAllExercises();

    @Query("SELECT * FROM exersice_table ORDER BY gewicht DESC")
    LiveData<List<Exercise>> getAllNotes();

    @Query("SELECT * FROM exersice_table WHERE monat=:monat AND tag=:tag AND jahr=:jahr")
    LiveData<List<Exercise>> getExercisesForDay(int tag, int monat, int jahr);

    @Query("DELETE FROM exersice_table WHERE datum = :datum")
    void deleteExercisesOfDay(String datum);

    @Query("SELECT * FROM exersice_table WHERE monat >= :monat AND tag >= :tag AND jahr >= :jahr ORDER BY tag ASC")
    LiveData<List<Exercise>> getAllExercisesLaterThan(int tag, int monat, int jahr);

    @Query("SELECT * FROM exersice_table WHERE monat >= :monatMin AND tag >= :tagMin AND jahr >= :jahrMin AND tag<= :tagMax AND monat<= :monatMax AND jahr<= :jahrMax  ORDER BY jahr,monat,tag ASC")
    LiveData<List<Exercise>> getAllExercisesBetweenDates(int tagMin, int monatMin, int jahrMin, int tagMax, int monatMax, int jahrMax);

    @Query("SELECT * FROM exersice_table WHERE monat >= :monatMin AND tag >= :tagMin AND jahr >= :jahrMin AND tag<= :tagMax AND monat<= :monatMax AND jahr<= :jahrMax  AND name = :name ORDER BY tag ASC")
    LiveData<List<Exercise>> getAllExercisesBetween(int tagMin, int monatMin, int jahrMin, int tagMax, int monatMax, int jahrMax, String name);

    @Query("SELECT * FROM exersice_table WHERE vorlage LIKE :vorlage GROUP BY name")
    LiveData<List<Exercise>> getAllExercisesForVorlage(String vorlage);

    @Query("SELECT * FROM exersice_table WHERE vorlage IS NOT NULL GROUP BY vorlage")
    LiveData<List<Exercise>> getAllExercisesWithVorlage();
}

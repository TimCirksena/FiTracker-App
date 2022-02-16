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

    @Query("SELECT * FROM exersice_table WHERE datumInt > :datum")
    LiveData<List<Exercise>> getAllExercisesLaterThan(int datum);

    @Query("SELECT * FROM exersice_table WHERE vorlage LIKE :vorlage")
    LiveData<List<Exercise>> getAllExercisesForVorlage(String vorlage);
}

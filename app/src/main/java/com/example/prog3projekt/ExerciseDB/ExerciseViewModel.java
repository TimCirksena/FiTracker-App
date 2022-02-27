package com.example.prog3projekt.ExerciseDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/** <h2>Tom Sattler & Tim Cirksena</h2>
    Implementation des ViewModels.
    Dient als Verbindung zwischen der Database und dem UI
 */
public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> allExercisesWithVorlage;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
        allExercisesWithVorlage = repository.getAllExercisesWithVorlage();
    }

    public void insert(Exercise exercise) {
        repository.insert(exercise);
    }

    public void update(Exercise exercise) {
        repository.update(exercise);
    }

    public void delete(Exercise exercise) {
        repository.delete(exercise);
    }

    public void deleteAllExercise() {
        repository.deleteAllExercises();
    }

    public void deleteExerciseForDay(String datum) {
        repository.deleteExerciseForDay(datum);
    }

    public LiveData<List<Exercise>> getExerciseForDate(int tag, int monat, int jahr) {
        return repository.getAllexercisesLaterThan(tag, monat, jahr);
    }

    public LiveData<List<Exercise>> getExerciseForDay(int tag, int monat, int jahr) {
        return repository.getExercisesForDay(tag, monat, jahr);
    }

    public LiveData<List<Exercise>> getAllExercisesInBetween(int tagMin, int monatMin, int jahrMin, int tagMax, int monatMax, int jahrMax, String name) {
        return repository.getAllExercisesInBetween(tagMin, monatMin, jahrMin, tagMax, monatMax, jahrMax, name);
    }

    public LiveData<List<Exercise>> getAllExercisesInBetweenDates(int tagMin, int monatMin, int jahrMin, int tagMax, int monatMax, int jahrMax) {
        return repository.getAllExercisesInBetweenDates(tagMin, monatMin, jahrMin, tagMax, monatMax, jahrMax);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public LiveData<List<Exercise>> getAllExercisesWithVorlage() {
        return allExercisesWithVorlage;
    }

    public LiveData<List<Exercise>> getAllExercisesForVorlage(String s) {
        return repository.getAllExercisesForVorlage(s);
    }
}
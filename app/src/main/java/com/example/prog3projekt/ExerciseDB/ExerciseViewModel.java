package com.example.prog3projekt.ExerciseDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel{
    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseViewModel(@NonNull Application application){
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
    }
    public void insert(Exercise exercise){
        repository.insert(exercise);
    }
    public void update(Exercise exercise){
        repository.update(exercise);
    }
    public void delete(Exercise exercise){
        repository.delete(exercise);
    }
    public void deleteAllNotes(){
        repository.deleteAllExercises();
    }

    //Methoden müssen im repository angepasst werden, Code müsste an
    // sich so stehen bleiben können

    /*
    public void getExerciseForDate(int date){
        repository.getAllExercisesLaterThan(date);
    }
    public void getAllExercisesForVorlage(String vorlage){
        repository.getAllExercisesForVorlage(vorlage);
    }
    */
    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }
}
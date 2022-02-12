package com.example.prog3projekt.DayDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DayViewModel extends AndroidViewModel {
    private DayRepository repository;
    private LiveData<List<Day>> allDays;

    public DayViewModel(@NonNull Application application){
        super(application);
        repository = new DayRepository((application));
        allDays = repository.getAllDays();
    }
    public void insert(Day day){
        repository.insert(day);
    }
    public void update(Day day){
        repository.update(day);
    }
    public void delete(Day day){
        repository.delete(day);
    }
    public void deleteAllDays(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Day>> getAllDays() {
        return allDays;
    }
}

package com.example.prog3projekt.VorlageDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;




import java.util.List;

public class VorlageViewModel extends AndroidViewModel {
    private VorlageRepository repository;
    private LiveData<List<Vorlage>> allVorlage;

    public VorlageViewModel(@NonNull Application application) {
        super(application);
        repository = new VorlageRepository((application));
        allVorlage = repository.
    }

    public void insert(Vorlage vorlage) {
        repository.insert(vorlage);
    }

    public void update(Vorlage vorlage) {
        repository.update(vorlage);
    }

    public void delete(Vorlage vorlage) {
        repository.delete(vorlage);
    }

    //public void deleteAllDays() {repository.deleteAllVorlage();}

    public LiveData<List<Vorlage>> getAllVorlage() {
        return allVorlage;
    }
}


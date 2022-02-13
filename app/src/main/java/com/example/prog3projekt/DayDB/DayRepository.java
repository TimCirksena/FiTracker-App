package com.example.prog3projekt.DayDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.prog3projekt.NoteDB.Note;
import com.example.prog3projekt.NoteDatabase;

import java.util.List;

public class DayRepository {
    private DayDao dayDao;
    private LiveData<List<Day>> allDays;

    public DayRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        dayDao = database.dayDao();
        allDays = dayDao.getAllDays();
    }
    public void insert(Day day){
        new DayRepository.InsertDayAsyncTask(dayDao).execute(day);
    }
    public void update(Day day){
        new DayRepository.UpdateDayAsyncTask(dayDao).execute(day);
    }
    public void delete(Day day){
        new DayRepository.DeleteDayAsyncTask(dayDao).execute(day);
    }
    public void deleteAllNotes(){
        new DayRepository.DeleteAllDayDayAsyncTask(dayDao).execute();
    }
    public LiveData<List<Day>> getAllDays(){
        return  allDays;
    }


    private static class InsertDayAsyncTask extends AsyncTask<Day, Void, Void> {
        private DayDao dayDao;

        private InsertDayAsyncTask(DayDao dayDao){
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(Day... days) {
            dayDao.insert(days[0]);
            return null;
        }
    }
    private static class UpdateDayAsyncTask extends AsyncTask<Day, Void, Void>{
        private DayDao dayDao;

        private UpdateDayAsyncTask(DayDao dayDao){
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(Day... days) {
            dayDao.update(days[0]);
            return null;
        }
    }
    private static class DeleteDayAsyncTask extends AsyncTask<Day, Void, Void>{
        private DayDao dayDao;

        private DeleteDayAsyncTask(DayDao dayDao){
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(Day... days) {
            dayDao.delete(days[0]);
            return null;
        }
    }
    private static class DeleteAllDayDayAsyncTask extends AsyncTask<Void, Void, Void>{
        private DayDao dayDao;

        private DeleteAllDayDayAsyncTask(DayDao dayDao){
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dayDao.deleteAllDays();
            return null;
        }
    }


}

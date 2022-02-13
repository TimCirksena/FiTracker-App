
package com.example.prog3projekt.VorlageDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.prog3projekt.DayDB.Day;
import com.example.prog3projekt.DayDB.DayDao;
import com.example.prog3projekt.DayDB.DayRepository;
import com.example.prog3projekt.NoteDatabase;

import java.util.List;

public class VorlageRepository {
        private VorlageDao vorlageDao;
        private LiveData<List<Vorlage>> allVorlage;

        public VorlageRepository(Application application){
            NoteDatabase database = NoteDatabase.getInstance(application);
            vorlageDao = database.vorlageDao();
            allVorlage = vorlageDao.getAllVorlage();
        }
        public void insert(Vorlage vorlage){
            new com.example.prog3projekt.VorlageDB..InsertVorlageAsyncTask(vorlageDao).execute(vorlage);
        }
        public void update(Vorlage vorlage){
            new com.example.prog3projekt.DayDB.DayRepository.UpdateDayAsyncTask(vorlageDao).execute(vorlage);
        }
        public void delete(Vorlage vorlage){
            new com.example.prog3projekt.DayDB.DayRepository.DeleteDayAsyncTask(vorlageDao).execute(vorlage);
        }
        public void deleteAllNotes(){
            new com.example.prog3projekt.DayDB.DayRepository.DeleteAllDayDayAsyncTask(vorlageDao).execute();
        }
        public LiveData<List<Vorlage>> getAllVorlage(){
            return  allVorlage;
        }


        private static class InsertDayAsyncTask extends AsyncTask<Vorlage, Void, Void> {
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
        private static class UpdateVorlageAsyncTask extends AsyncTask<Vorlage, Void, Void>{
            private VorlageDao vorlageDao;

            private UpdateVorlageAsyncTask(VorlageDao vorlageDao){
                this.dayDao = vorlageDao;
            }

            @Override
            protected Void doInBackground(Vorlage... vorlages) {
                VorlageDao.update(vorlages[0]);
                return null;
            }
        }
        private static class DeleteVorlageAsyncTask extends AsyncTask<Day, Void, Void>{
            private DayDao dayDao;

            private DeleteVorlageAsyncTask(DayDao dayDao){
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

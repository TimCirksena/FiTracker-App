package com.example.prog3projekt;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.prog3projekt.DayDB.Day;
import com.example.prog3projekt.DayDB.DayDao;
import com.example.prog3projekt.NoteDB.Note;
import com.example.prog3projekt.NoteDB.NoteDao;
import com.example.prog3projekt.VorlageDB.Vorlage;
import com.example.prog3projekt.VorlageDB.VorlageDao;

@Database(entities = {Note.class, Day.class, Vorlage.class}, version = 6)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract DayDao dayDao();
    public abstract NoteDao noteDao();
    public abstract VorlageDao vorlageDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private VorlageDao vorlageDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            vorlageDao = db.vorlageDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

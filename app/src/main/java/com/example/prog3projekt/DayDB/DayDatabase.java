package com.example.prog3projekt.DayDB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.prog3projekt.Note;
import com.example.prog3projekt.NoteDatabase;

@Database(entities = {Note.class}, version = 5)
public abstract class DayDatabase extends RoomDatabase {

    private static DayDatabase instance;

    public abstract DayDao dayDao();

    public static synchronized DayDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DayDatabase.class, "day_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private final DayDao dayDao;

        private PopulateDbAsyncTask(DayDatabase db){
            dayDao = db.dayDao();
        }
        @Override
        protected Void doInBackground(Void...voids){return null;}
    }
}

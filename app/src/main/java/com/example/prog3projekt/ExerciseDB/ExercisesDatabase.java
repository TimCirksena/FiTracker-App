package com.example.prog3projekt.ExerciseDB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
/** <h2> Tom Sattler & Tim Cirksena </h2>
 * Implementation der Database*/
@Database(entities = {Exercise.class},exportSchema = false, version = 7)
public abstract class ExercisesDatabase extends RoomDatabase {
    private static ExercisesDatabase instance;

    public abstract ExerciseDao exerciseDao();

    public static synchronized ExercisesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExercisesDatabase.class, "exercises_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;

        private PopulateDbAsyncTask(ExercisesDatabase db) {
            exerciseDao = db.exerciseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

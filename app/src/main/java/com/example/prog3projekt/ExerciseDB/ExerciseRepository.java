package com.example.prog3projekt.ExerciseDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allNotes;

    public ExerciseRepository(Application application){
        ExercisesDatabase database = ExercisesDatabase.getInstance(application);
        exerciseDao = database.exerciseDao();
        allNotes = exerciseDao.getAllNotes();
    }

    public void insert(Exercise exercise){
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }
    public void update(Exercise exercise){
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }
    public void delete(Exercise exercise){
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }
    public void deleteAllExercises(){
        new DeleteAllExercisesExercisesAsyncTask(exerciseDao).execute();
    }
    public LiveData<List<Exercise>> getAllExercises(){
        return  allNotes;
    }

    public LiveData<List<Exercise>> getExercisesForDay(int tag, int monat, int jahr){
        return exerciseDao.getExercisesForDay(tag,monat,jahr);
    }
    public LiveData<List<Exercise>> getAllexercisesLaterThan(int tag, int monat, int jahr) {
        return exerciseDao.getAllExercisesLaterThan(tag, monat, jahr);
    }

    public LiveData<List<Exercise>> getAllExercisesFromVorlage(String vorlage) {
        return exerciseDao.getAllExercisesForVorlage(vorlage);
    }

    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;

        private InsertExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.insert(exercises[0]);
            return null;
        }
    }
    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;

        private UpdateExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.update(exercises[0]);
            return null;
        }
    }
    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;

        private DeleteExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.delete(exercises[0]);
            return null;
        }
    }
    private static class DeleteAllExercisesExercisesAsyncTask extends AsyncTask<Void, Void, Void>{
        private ExerciseDao exerciseDao;

        private DeleteAllExercisesExercisesAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.deleteAllExercises();
            return null;
        }
    }

}


package com.example.prog3projekt.ExerciseDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allNotes;
    private LiveData<List<Exercise>> allExercisesWithVorlage;

    public ExerciseRepository(Application application){
        ExercisesDatabase database = ExercisesDatabase.getInstance(application);
        exerciseDao = database.exerciseDao();
        allNotes = exerciseDao.getAllNotes();
        allExercisesWithVorlage = exerciseDao.getAllExercisesWithVorlage();
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
        return  allExercisesWithVorlage;
    }

    public LiveData<List<Exercise>> getAllExercisesWithVorlage(){return allNotes;}

    public LiveData<List<Exercise>> getAllExercisesForVorlage(String s){return exerciseDao.getAllExercisesForVorlage(s);}
    //Methoden Passen noch nicht ganz, muss ich nochmal in der doc nachschauen

    /*public LiveData<List<Exercise>> getAllExercisesForVorlage(String vorlage){
        return new GetAllExercisesForVorlage(exerciseDao).execute(vorlage);
    }
    public LiveData<List<Exercise>> getAllExercisesLaterThan(int date){
        return  exerciseDao.getAllExercisesLaterThan(date);}
    */


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
    /**
    private static class GetAllExercisesForVorlage extends AsyncTask<String, Void, Void>{
        private ExerciseDao exerciseDao;

        private GetAllExercisesForVorlage(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            exerciseDao.getAllExercisesForVorlage(strings[0]);
            return null;
        }
    }
    */
    private static class GetAllExercisesFromInt extends AsyncTask<Integer, Void, Void>{
        private ExerciseDao exerciseDao;

        private GetAllExercisesFromInt(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(Integer... ints) {
            exerciseDao.getAllExercisesLaterThan(ints[0]);
            return null;
        }
    }
    /**
    private static class GetAllExercisesWithVorlage extends AsyncTask<String, Void, Void>{
        private ExerciseDao exerciseDao;

        private GetAllExercisesWithVorlage(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            exerciseDao.getAllExercisesWithVorlage();
            return null;
        }
    }
    */
}


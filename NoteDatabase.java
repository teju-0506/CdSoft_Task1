package com.example.notetakingapplication;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class},version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static  NoteDatabase instance;
    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static  RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

                //new PopulateDbAsyncTask(instance).execute();
            NoteDAO noteDAO= instance.noteDAO();
            ExecutorService executorsService= Executors.newSingleThreadExecutor();
            executorsService.execute(new Runnable() {
                @Override
                public void run() {
                    noteDAO.insert(new Note("Title 1","Descriptio 1","13-1-2024","12.30"));
                    noteDAO.insert(new Note("Title 2","Description 2","13-1-2024","12.30"));
                    noteDAO.insert(new Note("Title 3","Description 3","13-1-2024","12.30"));
                    noteDAO.insert(new Note("Title 4","Description 4","13-1-2024","12.30"));
                    noteDAO.insert(new Note("Title 5","Description 5","13-1-2024","12.30"));
                }
            });
        }
    };

    /*private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDAO noteDAO;
        private PopulateDbAsyncTask(NoteDatabase database){
            noteDAO=database.noteDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Title 1","Descriptio 1"));
            noteDAO.insert(new Note("Title 2","Description 2"));
            noteDAO.insert(new Note("Title 3","Description 3"));
            noteDAO.insert(new Note("Title 4","Description 4"));
            noteDAO.insert(new Note("Title 5","Description 5"));


            return null;
        }
    }*/

}

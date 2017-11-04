package com.example.aditya.itunestoppaidapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by aditya on 10/23/17.
 */

public class DatabaseDataManager  {

    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private FilterDAO filterDAO;

    public DatabaseDataManager(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(this.context);
        db = dataBaseHelper.getWritableDatabase();
        filterDAO = new FilterDAO(db);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public FilterDAO getFilterDAO(){
        return this.filterDAO;
    }

    public long saveNote(App filter){
        return filterDAO.save(filter);
    }

    public boolean deleteNote(App filter){
        return filterDAO.delete(filter);
    }

    public boolean updateNote(App filter){
        return filterDAO.update(filter);
    }

    public App getNote(String appname){
        return filterDAO.get(appname);
    }

    public List<App> getAllNote(){
        return filterDAO.getAll();
    }
}


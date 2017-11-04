package com.example.aditya.itunestoppaidapps;

/**
 * Created by aditya on 10/23/17.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "myfilter.db";
    static final int DB_VERSION = 3;
    public DataBaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FilterTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FilterTable.onUpgrade(db,oldVersion,newVersion);
    }
}

package com.example.aditya.itunestoppaidapps;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aditya on 10/23/17.
 */

public class FilterTable {
    static final String TABLENAME = "Filter";
    static final String COLUMNID = "id";
    static final String COLUMNAPPNAME = "appname";
    static final String COLUMNPRICE = "price";
    static final String COLUMNIMAGE1 = "image";
    static final String COLUMNIMAGE2 = "imageDown";

    static public void onCreate(SQLiteDatabase db){

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+"(");
        sb.append(COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(COLUMNAPPNAME +" TEXT NOT NULL, ");
        sb.append(COLUMNIMAGE1 +" TEXT NOT NULL, ");
        sb.append(COLUMNIMAGE2 +" TEXT NOT NULL, ");
        sb.append(COLUMNPRICE +" TEXT NOT NULL);");
        try{
            db.execSQL(sb.toString());
        }
        catch (SQLException e){

        }


    }

    static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        FilterTable.onCreate(db);
    }
}

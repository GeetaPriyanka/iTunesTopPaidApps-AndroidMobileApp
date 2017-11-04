package com.example.aditya.itunestoppaidapps;

/**
 * Created by aditya on 10/23/17.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class FilterDAO {

    private SQLiteDatabase db;

    public FilterDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(App filter){
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMNAPPNAME,filter.getName());
        values.put(FilterTable.COLUMNIMAGE1,filter.getImage());
        values.put(FilterTable.COLUMNIMAGE2,filter.getImageDown());
        values.put(FilterTable.COLUMNPRICE,filter.getPrice());
        return db.insert(FilterTable.TABLENAME,null,values);
    }

    public boolean update(App filter){
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMNAPPNAME,filter.getName());
        values.put(FilterTable.COLUMNIMAGE1,filter.getImage());
        values.put(FilterTable.COLUMNIMAGE2,filter.getImageDown());
        values.put(FilterTable.COLUMNPRICE,filter.getPrice());
        return db.update(FilterTable.TABLENAME,values, FilterTable.COLUMNAPPNAME+"=?",new String[]{filter.getName()})>0;
    }

    public boolean delete(App filter){
        return db.delete(FilterTable.TABLENAME, FilterTable.COLUMNAPPNAME+"=?",new String[]{filter.getName()})>0;
    }

    public App get(String appname){
        App filter = null;

        Cursor c = db.query(true, FilterTable.TABLENAME,new String[]{FilterTable.COLUMNAPPNAME, FilterTable.COLUMNPRICE, FilterTable.COLUMNIMAGE1, FilterTable.COLUMNIMAGE2}
                , FilterTable.COLUMNAPPNAME+"=?",new String[]{appname},null,null,null,null,null);
        if(c!=null && c.moveToFirst()){
            filter = buildFilterFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return filter;
    }

    public List<App> getAll(){

        List<App> filterList = new ArrayList<App>();
        Cursor c = db.query(FilterTable.TABLENAME,new String[]{FilterTable.COLUMNID, FilterTable.COLUMNAPPNAME,FilterTable.COLUMNIMAGE1,
                        FilterTable.COLUMNIMAGE2, FilterTable.COLUMNPRICE}
                ,null,null,null,null,null);
        if(c!=null && c.moveToFirst()){
            do{
                App filter = buildFilterFromCursor(c);
                if(filter!=null){
                    filterList.add(filter);
                }

            }
            while (c.moveToNext());
            if(!c.isClosed()){
                c.close();
            }
        }
        return filterList;
    }

    private App buildFilterFromCursor(Cursor c){
        App filter=null;

        if(c!=null){
            filter = new App();

            filter.setName(c.getString(1));
            filter.setImage(c.getString(2));
            filter.setImageDown(c.getString(3));
            filter.setPrice(c.getString(4));
        }

        return filter;
    }
}

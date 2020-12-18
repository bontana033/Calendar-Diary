package com.applandeo.materialcalendarsampleapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=2;
    public DBHelper(Context context){
        super(context, "calendar", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String memoSQL="create table calendar (" +
                "_id integer primary key autoincrement," +
                "title," +
                "content," +
                "year integer," +
                "month integer," +
                "day integer," +
                "mode integer," +
                "rangeStart," +
                "rangeEnd," +
                "place, " +
                "x real," +
                "y real" +
                ")";
        db.execSQL(memoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table calendar");
            onCreate(db);
        }
    }

    public void dropTable(SQLiteDatabase db, String tableName){
        db.execSQL("drop table " + tableName);
    }

}

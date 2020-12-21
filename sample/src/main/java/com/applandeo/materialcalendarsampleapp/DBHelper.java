package com.applandeo.materialcalendarsampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    private SQLiteDatabase db;
    private final int DATE_NULL = -987654321;

    public DBHelper(Context context){
        super(context, "calendar", null, DATABASE_VERSION);
        db = getWritableDatabase();
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

    public void addSchedule(String title, String content, int year, int month, int day, String place){
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("year", year);
        values.put("month", month);
        values.put("day", day);
        values.put("place", place);
        db.insert("calendar", null, values);
    }

    public void addSchedule(Schedule schedule){
        ContentValues values = new ContentValues();
        if(schedule.title != null)  values.put("title", schedule.title);
        if(schedule.content != null)    values.put("content", schedule.content);
        if(schedule.year != DATE_NULL)  values.put("year", schedule.year);
        if(schedule.month != DATE_NULL) values.put("month", schedule.month);
        if(schedule.day != DATE_NULL)   values.put("day", schedule.day);
        if(schedule.place != null) values.put("place", schedule.place);

        db.insert("calendar", null, values);
    }

    public Cursor selectAllSchedule(){
        Cursor cursor = db.rawQuery("select _id, title, content, year, month, day, place from calendar order by _id", null);
        return cursor;
    }

//    public Cursor selectQuery(int year, int month, int day, String title, String content, String place){
//        // to be constructed
//    }

    public Cursor selectQuery(Schedule schedule, String selectionArgs[]){
        String query1 = "select ";
        for (int i = 0; i < selectionArgs.length; i++) {
            if(i>0){
                query1 += ", ";
            }
            query1 += selectionArgs[i];
        }

        String query2 = " from ";
        query2 += "calendar ";

        boolean com3 = false;
        String query3 = "where ";
        if(schedule.title != null) {
            query3 += "title = " + schedule.title;
            com3 = true;
        }
        if(schedule.content != null){
            if(com3) query3 += " and ";
            query3 += "content = " + schedule.content;
            com3 = true;
        }
        if(schedule.year != DATE_NULL){
            if(com3) query3 += " and ";
            query3 += "year = " + schedule.year;
            com3 = true;
        }
        if(schedule.month != DATE_NULL){
            if(com3) query3 += " and ";
            query3 += "month = " + schedule.month;
            com3 = true;
        }
        if(schedule.day != DATE_NULL){
            if(com3) query3 += " and ";
            query3 += "day = " + schedule.day;
            com3 = true;
        }
        if(schedule.place != null){
            if(com3) query3 += " and ";
            query3 += "place = " + schedule.place;
        }
        String final_query = query1 + query2 + query3;
        Cursor cursor = db.rawQuery(final_query, null);

        return cursor;
    }

    public void dropTable(SQLiteDatabase db, String tableName){
        db.execSQL("drop table " + tableName);
    }

    public Cursor getOneSchedule(int id){
        Cursor cursor = db.rawQuery("select _id, title, content, year, month, day, place from calendar where _id = " + id, null);
        return cursor;
    }

    public void deleteOneSchedule(int scheduleId) {
        db.execSQL("delete from calendar where _id = " + scheduleId);
    }
}

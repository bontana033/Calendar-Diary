package com.applandeo.materialcalendarsampleapp;

public class Schedule {
    String title;
    String content;
    int year;
    int month;

    public Schedule(String title, String content, int year, int month, int day) {
        this.title = title;
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Schedule(String title, String content, String year, String month, String day) {
        this.title = title;
        this.content = content;
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
    }

    int day;
    double x;
    double y;
    int mode;
    int multiDays[];
    int rangeStart;
    int rangeEnd;

    public Schedule(){
        multiDays = new int[13];
    }
}

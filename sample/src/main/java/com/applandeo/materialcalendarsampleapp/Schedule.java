package com.applandeo.materialcalendarsampleapp;

public class Schedule {
    String title;
    String content;
    int year;
    int month;
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

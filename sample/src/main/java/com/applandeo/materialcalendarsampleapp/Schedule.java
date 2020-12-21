package com.applandeo.materialcalendarsampleapp;

public class Schedule {
    int id;
    String title;
    String content;
    int year;
    int month;
    int day;
    String place;
    double x;
    double y;
    int mode;
    int multiDays[] = new int[13];
    int rangeStart;
    int rangeEnd;

    public Schedule(String title, String content, int year, int month, int day) {
        this.title = title;
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Schedule(String title, String content, int year, int month, int day, String place) {
        this.title = title;
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        this.place = place;
    }

    public Schedule(String title, String content, String year, String month, String day, String place) {
        this.title = title;
        this.content = content;
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
        this.place = place;
    }

    public Schedule(int year, int month, int day) {
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

    public Schedule() {
    }
}

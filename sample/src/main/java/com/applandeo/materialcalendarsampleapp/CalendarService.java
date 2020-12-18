package com.applandeo.materialcalendarsampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarsampleapp.utils.DrawableUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarService {
    int monthArr0base[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private CalendarView calendarView;
    private ArrayList<EventDay> events;
    private Context context;
    private DBHelper helper;
    SQLiteDatabase db;
    private final String TAG = CalendarService.class.getSimpleName();
    // [year][month][day]
    int schedules101base[][][];
    LinearLayout container;
    Calendar curCalendar;
    // [year][month][day]
    int isDisplayed101base[][][];


    public CalendarService(CalendarView calendarView, Context context, int minimumMonthAmount, int maximumMonthAmount, View view) {
        this.calendarView = calendarView;
        this.context = context;
        events = new ArrayList<>();

        // 캘린더 뷰에서 display할 이전 달 개수 설정
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, minimumMonthAmount);

        // 캘린더 뷰에서 display할 다음 달 개수 설정
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, maximumMonthAmount);

        // 캘린더 뷰의 MinimumDate, MaximumDate 설정
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        // db
        helper =new DBHelper(context);
        db = helper.getWritableDatabase();

        // schedules
        schedules101base = new int[3000][13][32];

        // 해당 캘린더가 처음 보여질 때 icon 렌더링
        isDisplayed101base = new int[3000][13][32];

        // putIconOnSchedule에 사용할 Calendar 객체.
        curCalendar = Calendar.getInstance();

//        container 초기화(스크롤 뷰)
        container = (LinearLayout) view;

        // 이전 달, 다음 달 넘어갈 때마다 curCalendar의 month값을 바꿔주고 해당 달의 putIconOnCalendar를 호출.
        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener(){
            @Override
            public void onChange() {
                curCalendar.add(Calendar.MONTH, -1);
                Log.d(TAG, curCalendar.getTime().toString());
                putIconOnCalendar(curCalendar);
            }
        });

        // 다음 달 리스너
        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                curCalendar.add(Calendar.MONTH, 1);
                Log.d(TAG, curCalendar.getTime().toString());
                putIconOnCalendar(curCalendar);
            }
        });
    }

    // parameter 없을 시, 현재 date 기준 putIconOnCalendar
    public void putIconOnCalendar(){
        Calendar c = Calendar.getInstance();
        putIconOnCalendar(c);
    }

    // schedule를 새로 추가했을 때 해당 아이콘 +1 해주기(addEvent의 콜백)
    public void putIconOnCalendar(Calendar calendar) {
        // 해당 달에 이미 icon이 렌더링됐다면
        if(isDisplayed101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH)+1][calendar.get(Calendar.DAY_OF_MONTH)] > 0){
            return;
        }
        // 안됐다면
        isDisplayed101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH)+1][calendar.get(Calendar.DAY_OF_MONTH)] = 1;

        // events 중에서 해당 schedule를 찾아 숫자 + 1
        for (int i = 1; i <= monthArr0base[calendar.get(Calendar.MONTH)]; i++) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            c.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, i);
            int amount = schedules101base[c.get(Calendar.YEAR)][c.get(Calendar.MONTH)+1][i];
            if(amount > 0){
                events.add(new EventDay(c, DrawableUtils.getCircleDrawableWithText(context, amount)));
            }
        }
        // events 렌더링
        calendarView.setEvents(events);
    }

    // db에 스케줄 넣는 코드. schedule에 숫자를 추가하고 callendar에 icon 숫자 + 1
    public void addEvent(int year, int month, int day, String title, String content, String place){
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("year", year);
        values.put("month", month);
        values.put("day", day);
        values.put("place", place);
        db.insert("calendar", null, values);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        plusSchedules(calendar);
        updateIconOnCalendar(calendar);
        // displayScheduleCard(container, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    // db 전체 스케줄을 전체 달력에 넣음(클릭했을 떄 스크롤 뷰에 나옴. 아이콘은 putIconOnCalendar에서
    public void putSchedules() {
        Cursor cursor = db.rawQuery("select title, content, year, month, day, place from calendar order by _id", null);
        // Log.d(TAG, "cursor count : " + Integer.toString(cursor.getCount()));
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String content = cursor.getString(1);
                String year = cursor.getString(2);
                String month = cursor.getString(3);
                String day = cursor.getString(4);
                String place = cursor.getString(5);

                schedules101base[Integer.parseInt(year)][Integer.parseInt(month)][Integer.parseInt(day)]++;
            } while (cursor.moveToNext());
        }
    }

    // 해당 스케줄 숫자 +1
    public void plusSchedules(Calendar calendar){
        schedules101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH) + 1][calendar.get(Calendar.DAY_OF_MONTH)]++;
    }

    // 해당 아이콘 + 1
    public void updateIconOnCalendar(Calendar calendar){
        int amount = schedules101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH) + 1][calendar.get(Calendar.DAY_OF_MONTH)];
        Log.d(TAG, events.get(0).getCalendar().getTime().toString() + ",  " + amount + ",  " + calendar.get(Calendar.YEAR)  + ",  " + calendar.get(Calendar.MONTH)  + ",  " + calendar.get(Calendar.DAY_OF_MONTH));
        boolean isFound = false;
        for (int i = 0; i < events.size(); i++) {
            Calendar tc = events.get(i).getCalendar();
            if(tc.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && tc.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && tc.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
                events.get(i).changeDrawble(DrawableUtils.getCircleDrawableWithText(context, amount));
                isFound = true;
                break;
            }
        }
        if(!isFound){
            events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(context, amount)));
        }

        calendarView.setEvents(events);
    }

    // 스크롤 뷰 초기화용
    public void clearContainer(ViewGroup viewGroup){
        viewGroup.removeAllViews();
    }

    // 스크롤뷰에 스케줄 추가하기
    public void displayScheduleCard(View view, int year, int month, int day) {
        clearContainer((ViewGroup)view);

        Cursor cursor = db.rawQuery("select _id, title, place from calendar where year = " + year + " and month = " + month + " and day = " + day, null);
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                String place = cursor.getString(2);

                String cursorTitle = title;
                String cursorDate = year + "." + month + "." + day;
                String cursorPlace = place;

                ScheduleCard scheduleCardLayout = new ScheduleCard(context);
                TextView titleTextView = scheduleCardLayout.findViewById(R.id.schedule_card_title);
                TextView dateTextView = scheduleCardLayout.findViewById(R.id.schedule_card_date);
                TextView placeTextView = scheduleCardLayout.findViewById(R.id.schedule_card_place);

                titleTextView.setText(cursorTitle);
                dateTextView.setText(cursorDate);
                placeTextView.setText(cursorPlace);

                container.addView(scheduleCardLayout);
            } while (cursor.moveToNext());
        }
    }
}

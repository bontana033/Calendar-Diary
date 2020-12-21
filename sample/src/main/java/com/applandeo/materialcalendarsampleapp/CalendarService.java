package com.applandeo.materialcalendarsampleapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private DBHelper dbHelper;
    // SQLiteDatabase db;
//    DBHelper dbHel
    private final String TAG = CalendarService.class.getSimpleName();
    // int schedules101base[year][month][day] : 일자별 스케줄 개수. year, month, day 각각 1, 0, 1 base index.
    int schedules101base[][][];
    LinearLayout container;
    Calendar curCalendar;
    // [year][month][day]
    int isDisplayed101base[][][];


    // CalendarView가 필요없는 constructor
    public CalendarService(){

    }

    public CalendarService(Context context){
        this.context = context;
        // db
        dbHelper = new DBHelper(context);
    }

    // CalendarView가 필요한 constructor
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
        dbHelper = new DBHelper(context);


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

    // schedule를 새로 추가했을 때 해당 아이콘 +1 해주기(addSchedule의 콜백)
    public void putIconOnCalendar(Calendar calendar) {
        // 해당 달에 이미 icon이 렌더링됐다면
        if(isDisplayed101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH)][calendar.get(Calendar.DAY_OF_MONTH)] > 0){
            return;
        }
        // 안됐다면
        isDisplayed101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH)][calendar.get(Calendar.DAY_OF_MONTH)] = 1;

        // events 중에서 해당 schedule를 찾아 숫자 + 1
        for (int i = 1; i <= monthArr0base[calendar.get(Calendar.MONTH)]; i++) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            c.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, i);
            int amount = schedules101base[c.get(Calendar.YEAR)][c.get(Calendar.MONTH)][i];
            if(amount > 0){
                events.add(new EventDay(c, DrawableUtils.getCircleDrawableWithText(context, amount)));
            }
        }
        // events 렌더링
        calendarView.setEvents(events);

    }

    // db에 스케줄 넣는 코드. schedule에 숫자를 추가하고 callendar에 icon 숫자 + 1
//    public void addSchedule(int year, int month, int day, String title, String content, String place){
    public void addScheduleYearMonthDay(String title, String content, int year, int month, int day, String place){
        Schedule s = new Schedule(title, content, year, month, day, place);
//        dbHelper.addSchedule(year, month, day, title, content, place);
        dbHelper.addSchedule(s);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        plusSchedules(calendar);
        updateIconOnCalendar(calendar);
        // displayScheduleCard(container, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void addScheduleWithoutCallback(String title, String content, int year, int month, int day, String place){
        Schedule s = new Schedule(title, content, year, month, day, place);
//        dbHelper.addSchedule(year, month, day, title, content, place);
        dbHelper.addSchedule(s);


    }

    public void addScheduleWithoutCallback(Schedule s){
        addScheduleYearMonthDay(s);
    }

    public void addScheduleYearMonthDay(Schedule s){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, s.year);
        calendar.set(Calendar.MONTH, s.month);
        calendar.set(Calendar.DAY_OF_MONTH, s.day);
        plusSchedules(calendar);
        updateIconOnCalendar(calendar);
    }

    public void addSchedule(Schedule s){
        dbHelper.addSchedule(s);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, s.year);
        c.set(Calendar.MONTH, s.month);
        c.set(Calendar.DAY_OF_MONTH, s.day);
        Log.d("tt33", "스케줄 추가");

        plusSchedules(c);
        updateIconOnCalendar(c);
    }

    // db 전체 스케줄을 전체 달력에 넣음(클릭했을 떄 스크롤 뷰에 나옴. 아이콘은 putIconOnCalendar에서
    public void putSchedules() {
        Cursor cursor = dbHelper.selectAllSchedule();
        // Log.d(TAG, "cursor count : " + Integer.toString(cursor.getCount()));
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String year = cursor.getString(3);
                String month = cursor.getString(4);
                String day = cursor.getString(5);
                String place = cursor.getString(6);

                schedules101base[Integer.parseInt(year)][Integer.parseInt(month) - 1][Integer.parseInt(day)]++;
            } while (cursor.moveToNext());
        }
    }

    // 해당 스케줄 숫자 +1
    public void plusSchedules(Calendar c){
        schedules101base[c.get(Calendar.YEAR)][c.get(Calendar.MONTH)][c.get(Calendar.DAY_OF_MONTH)]++;
        Log.d("tt33", "스케줄 배열 +1");
    }

    private void minusIconOnCalendar(Calendar c) {
        schedules101base[c.get(Calendar.YEAR)][c.get(Calendar.MONTH)][c.get(Calendar.DAY_OF_MONTH)]--;
        Log.d(TAG, "amount : " + schedules101base[c.get(Calendar.YEAR)][c.get(Calendar.MONTH)][c.get(Calendar.DAY_OF_MONTH)] + "");
    }

    // 해당 아이콘 + 1
    public void updateIconOnCalendar(Calendar calendar){
        int amount = schedules101base[calendar.get(Calendar.YEAR)][calendar.get(Calendar.MONTH)][calendar.get(Calendar.DAY_OF_MONTH)];
//        if(amount == 0)
//            Log.d(TAG, events.get(0).getCalendar().getTime().toString() + ",  " + amount + ",  " + calendar.get(Calendar.YEAR)  + ",  " + calendar.get(Calendar.MONTH)  + ",  " + calendar.get(Calendar.DAY_OF_MONTH));
        boolean isFound = false;
        for (int i = 0; i < events.size(); i++) {
            Calendar tc = events.get(i).getCalendar();
            if(tc.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && tc.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && tc.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
                Log.d(TAG, "i found " + amount);
                if(amount == 0) events.get(i).deleteDrawble();
                else events.get(i).changeDrawble(DrawableUtils.getCircleDrawableWithText(context, amount));
                isFound = true;
                break;
            }
        }
        if(!isFound){
            events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(context, amount)));
        }

        calendarView.setEvents(events);
        Log.d("tt33", "icon update");
    }

    // 스크롤 뷰 초기화용
    public void clearContainer(ViewGroup viewGroup){
        viewGroup.removeAllViews();
    }

    // 스크롤뷰에 스케줄 추가하기
    public void displayScheduleCard(View view, int year, int month, int day) {
        clearContainer((ViewGroup)view);


        Schedule s = new Schedule(year, month+1, day);

//        Cursor cursor = db.rawQuery("select _id, title, place from calendar where year = " + year + " and month = " + month + " and day = " + day, null);
        Cursor cursor = dbHelper.selectQuery(s, new String[]{"_id", "title", "place"});
        if(cursor.moveToFirst()) {
            do {
                String scheduleId = cursor.getString(0);
                String scheduleTitle = cursor.getString(1);
                String schedulePlace = cursor.getString(2);

                String cursorId = cursor.getString(0);
                String cursorTitle = scheduleTitle;
                String cursorDate = year + "." + (month + 1) + "." + day;
                String cursorPlace = schedulePlace;

                ScheduleCard scheduleCardLayout = new ScheduleCard(context);

                TextView idTextView = scheduleCardLayout.findViewById(R.id.schedule_card_id);
                TextView titleTextView = scheduleCardLayout.findViewById(R.id.schedule_card_title);
                TextView dateTextView = scheduleCardLayout.findViewById(R.id.schedule_card_date);
                TextView placeTextView = scheduleCardLayout.findViewById(R.id.schedule_card_place);

                idTextView.setText(cursorId);
                titleTextView.setText(cursorTitle);
                dateTextView.setText(cursorDate);
                placeTextView.setText(cursorPlace);

                ImageButton button1 = scheduleCardLayout.findViewById(R.id.schedule_add_button);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ScheduleDetail.class);
                        intent.putExtra("scheduleId", scheduleId);
                        context.startActivity(intent);
                    }
                });

                ImageButton checkButton = scheduleCardLayout.findViewById(R.id.check_button);
                checkButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "삭제되었습니다!", Toast.LENGTH_SHORT).show();

                        checkButton.setSelected(true);
                        checkButton.setPressed(true);
                        checkButton.setBackgroundColor(Color.parseColor("#000000"));

                        Animation anim_test = AnimationUtils.loadAnimation(context,R.anim.anim_test);
                        scheduleCardLayout.startAnimation(anim_test);
                        checkButton.setImageResource(R.drawable.check_button);


                        deleteOneSchedule(Integer.parseInt(scheduleId));
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, day);
                        minusIconOnCalendar(c);
                        updateIconOnCalendar(c);
                        scheduleCardLayout.setVisibility(view.INVISIBLE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                scheduleCardLayout.setVisibility(view.GONE);
                            }
                        }, 1000);
                    }
                });

                container.addView(scheduleCardLayout);
            } while (cursor.moveToNext());
        }
    }

    private void deleteOneSchedule(int scheduleId) {
        dbHelper.deleteOneSchedule(scheduleId);
    }

    public Schedule getOneSchedule(int id){
        Cursor cursor = dbHelper.getOneSchedule(id);
        if(cursor.moveToFirst()){
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String year = cursor.getString(3);
            String month = cursor.getString(4);
            String day = cursor.getString(5);
            String place = cursor.getString(6);

            return new Schedule(title, content, year, month, day, place);
        }
        else    return null;
    }
}

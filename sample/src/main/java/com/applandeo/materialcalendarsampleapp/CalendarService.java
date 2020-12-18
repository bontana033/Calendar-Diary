package com.applandeo.materialcalendarsampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.lang.UScript;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarsampleapp.utils.DrawableUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarService {
    int monthArr[] = {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private CalendarView calendarView;
    private List<EventDay> events;
    private Context context;
    private DBHelper helper;
    SQLiteDatabase db;
    private final String TAG = CalendarService.class.getSimpleName();
    // ArrayList<Integer> schedules[][][];
    int schedules[][][];
    private int curYear, curMonth, curDay, cuttedCurYear;
    private int base1Year, base0Month, base0Day;


    public CalendarService(CalendarView calendarView, Context context, int minimumMonthAmount, int maximumMonthAmount) {
        this.calendarView = calendarView;
        this.context = context;
        events = new ArrayList<>();


        // 캘린더 뷰에서 display할 이전 달 개수 설정
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, minimumMonthAmount);

        // curMonth 설정
        curMonth = min.get(Calendar.MONTH) + 1;
        curYear = min.get(Calendar.YEAR) + 1;
        curDay = min.get(Calendar.DAY_OF_MONTH);
        cuttedCurYear = curYear - 2000;

        base1Year = min.get(Calendar.YEAR) + 1;
        base0Month = min.get(Calendar.MONTH);
        base0Day = min.get(Calendar.DAY_OF_MONTH);







        // 캘린더 뷰에서 display할 다음 달 개수 설정
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, maximumMonthAmount);

        // 캘린더 뷰의 MinimumDate, MaximumDate 설정
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);


        // db
        helper =new DBHelper(context);
        db = helper.getWritableDatabase();
        // helper.dropTable(db, "calendar");

        // schedules
//        schedules = new ArrayList[100][12][32];
        schedules = new int[30][13][32];


    }

    public void putIconOnCalendar() {
//        Log.d(TAG, "hihihihih  " + curMonth);
//        checkSchedules();
        List<EventDay> event = new ArrayList();

        // Calendar c = Calendar.getInstance();
        // c.set(Calendar.DAY_OF_MONTH, 1);





        Calendar tc = Calendar.getInstance();
        event.add(new EventDay(tc, DrawableUtils.getCircleDrawableWithText(context,   "as")));

        for (int i = 1; i <= monthArr[curMonth]; i++) {
            int amount = schedules[cuttedCurYear][curMonth][i];
            // Log.d(TAG, cuttedCurYear + " " + curMonth + " " + i + " " + amount);
            Calendar c = getCalendarYearMonthDay(base1Year, base0Month, i);
            if(amount > 10) {
                Log.d(TAG, "h1  " + c.getTime());
                event.add(new EventDay(c, DrawableUtils.getCircleDrawableWithText(context,   "10+")));
            }
            else if(amount > 0){
                Log.d(TAG, "h2  " + c.getTime());
                event.add(new EventDay(c, DrawableUtils.getCircleDrawableWithText(context, Integer.toString(amount))));
            }
            // Log.d(TAG, "month : " + c.getTime().toString());
        }



        calendarView.setEvents(event);

    }

    private Calendar getCalendarYearMonthDay(String year, String month, String day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month));
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

        return c;
    }
    private Calendar getCalendarYearMonthDay(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        return c;
    }

    // db 넣는 코드.
    public void addEvent(int year, int month, int day, String title, String content, String place){

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("year", year);
        values.put("month", month);
        values.put("day", day);
        values.put("place", place);

        db.insert("calendar", null, values);


//        Cursor cursor = db.rawQuery("select title, content from calendar order by _id", null);
//        Log.d(TAG, "hi");
//        Log.d(TAG, Integer.toString(cursor.getCount()));
//        cursor.moveToFirst();
//        do{
//            Log.d(TAG, cursor.toString());
//            Log.d(TAG, cursor.getString(0));
//        }while(cursor.moveToNext());
//
//        Log.d(TAG, "logcatcat3");


//        List<EventDay> event = new ArrayList();
//        // 이벤트 리스트
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month-1);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//
//        event.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(context, "20+")));
//        calendarView.setEvents(event);






        // Calendar 객체(단순 이벤트)
//        Calendar calendar = Calendar.getInstance();
//        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "A")));

        // Calendar1 객체(단순 이벤트)
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.add(Calendar.DAY_OF_MONTH, -1);
//        events.add(new EventDay(calendar1, R.drawable.sample_icon_2));
//
//        // Calendar2 객체(단순 이벤트)
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.add(Calendar.DAY_OF_MONTH, 10);
//        events.add(new EventDay(calendar2, R.drawable.sample_icon_3, Color.parseColor("#228B22")));
//
//        // Calendar3 객체(단순 이벤트)
//        Calendar calendar3 = Calendar.getInstance();
//        calendar3.add(Calendar.DAY_OF_MONTH, 7);
//        events.add(new EventDay(calendar3, R.drawable.sample_four_icons));
//
//        // Calendar4 객체(단순 이벤트)
//        Calendar calendar4 = Calendar.getInstance();
//        calendar4.add(Calendar.DAY_OF_MONTH, 13);
//        events.add(new EventDay(calendar4, DrawableUtils.getThreeDots(CalendarActivity.class.)));
//
//
//        Calendar calendar5 = Calendar.getInstance();
////         calendar5.set(2020, 12, 20);
//        calendar5.set(Calendar.MONTH, 0);
////         calendar5.add(Calendar.YEAR, 2020);
////         calendar5.add(Calendar.DAY_OF_WEEK, 1);
////         calendar5.set(Calendar.DAY_OF_MONTH, 11);
////         calendar5.set(Calendar.DAY_OF_YEAR, );
////         calendar5.set(Calendar.MONTH, );
//
//        events.add(new EventDay(calendar5, DrawableUtils.getCircleDrawableWithText(context, "T")));
//
//        Calendar calendar6 = Calendar.getInstance();
//        calendar6.set(Calendar.YEAR, 2021);
//        calendar6.set(Calendar.MONTH, 1);
//        calendar6.set(Calendar.DAY_OF_MONTH, 3);
//        events.add(new EventDay(calendar6, DrawableUtils.getCircleDrawableWithText(context, "5+")));
        // events.add(new EventDay(calendar6, DrawableUtils.getCircleDrawableWithText(this, "3+")));







//         calendar5.add(Calendar.DAY_OF_MONTH, 1);
//         calendar5.add(Calendar.DAY_OF_MONTH, 2);
        // calendar5.set(2020, 12, 20);
        // calendar5.add

        // events.add(new EventDay(calendar5, ));





        // 캘린더 뷰
        // CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);



        // 캘린더 객체들 일괄 등록


        // calendarView.setDisabledDays(getDisabledDays());

        // 날짜 클릭 이벤트 핸들러
//        calendarView.setOnDayClickListener(eventDay ->
//            //{
//                Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString() + eventDay.isEnabled(), Toast.LENGTH_SHORT).show()
//
//                // 현재 클릭된 날짜 반환 함수
//                // eventDay.getCalendar().getTime();
//            // }
//        );



        // 랜덤 날짜 선택해주고 날자 토스트 띄우기
//        Button setDateButton = (Button) findViewById(R.id.setDateButton);
//        setDateButton.setOnClickListener(v -> {
//            try {
//                Calendar randomCalendar = getRandomCalendar();
//                String text = randomCalendar.getTime().toString();
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
//                calendarView.setDate(randomCalendar);
//            } catch (OutOfDateRangeException exception) {
//                exception.printStackTrace();
//
//                Toast.makeText(getApplicationContext(),
//                        "Date is out of range",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
    }
    public void commit(){
        calendarView.setEvents(events);
    }
    public void resetEvents(){
        events.clear();
    }

    public void checkDB() {
        Cursor cursor = db.rawQuery("select title, content, year, month, day from calendar order by _id", null);
        // Log.d(TAG, "cursor count : " + Integer.toString(cursor.getCount()));
        cursor.moveToFirst();
        do{
//            Log.d(TAG, cursor.toString());
//            Log.d(TAG, cursor.getString(0));
//            Log.d(TAG, cursor.getString(1));
//            Log.d(TAG, cursor.getString(2));
//            Log.d(TAG, cursor.getString(3));
//            Log.d(TAG, cursor.getString(4));
        }while(cursor.moveToNext());

        // Log.d(TAG, "logcatcat3");
    }

    public void display(View view) {
        LinearLayout container = (LinearLayout) view;

        Cursor cursor = db.rawQuery("select title, content, year, month, day, place from calendar order by _id", null);
        Log.d(TAG, "cursor count : " + Integer.toString(cursor.getCount()));
        cursor.moveToFirst();
        do{
            /*
            0 title
            1 content
            2 year
            3 month
            4 day
            5 place
             */




            String year = cursor.getString(2);
            String month = cursor.getString(3);
            String day = cursor.getString(4);

            schedules[Integer.parseInt(year)-2000][Integer.parseInt(month)][Integer.parseInt(day)]++;
            // Log.d(TAG, year + ", " + month  + ", " + day);


            String cursorTitle = cursor.getString(0);
            String cursorDate = year + "." + month + "." + day;
            String cursorPlace = cursor.getString(5);


            ScheduleCard scheduleCardLayout = new ScheduleCard(context);
            TextView title = scheduleCardLayout.findViewById(R.id.schedule_card_title);
            TextView date = scheduleCardLayout.findViewById(R.id.schedule_card_date);
            TextView place = scheduleCardLayout.findViewById(R.id.schedule_card_place);

            title.setText(cursorTitle);
            date.setText(cursorDate);
            place.setText(cursorPlace);

            container.addView(scheduleCardLayout);




        }while(cursor.moveToNext());
    }

    public void checkSchedules(){
        for (int i = 0; i < 32; i++) {
            Log.d(TAG, i + " : " + Integer.toString(schedules[20][12][i]));
        }
    }
}

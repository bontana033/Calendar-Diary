package com.applandeo.materialcalendarsampleapp;

import android.content.Context;
import android.graphics.Color;

import com.applandeo.materialcalendarsampleapp.utils.DrawableUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarService {
    private CalendarView calendarView;
    List<EventDay> events;
    Context context;
    public CalendarService(CalendarView calendarView, Context context, int minimumMonthAmount, int maximumMonthAmount) {
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
    }

    public void addEvent(int year, int month, int day){
        List<EventDay> event = new ArrayList();
        // 이벤트 리스트

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        event.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(context, "20+")));
//        EventDay
        calendarView.setEvents(event);
        





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
}

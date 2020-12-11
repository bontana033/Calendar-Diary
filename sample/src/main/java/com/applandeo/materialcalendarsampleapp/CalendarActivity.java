package com.applandeo.materialcalendarsampleapp;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarsampleapp.utils.DrawableUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Kornakiewicz on 26.05.2017.
 */

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        // 이벤트 리스트
        List<EventDay> events = new ArrayList<>();

        // Calendar 객체(단순 이벤트)
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "M")));

        // Calendar1 객체(단순 이벤트)
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, 10);
        events.add(new EventDay(calendar1, R.drawable.sample_icon_2));

        // Calendar2 객체(단순 이벤트)
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 10);
        events.add(new EventDay(calendar2, R.drawable.sample_icon_3, Color.parseColor("#228B22")));

        // Calendar3 객체(단순 이벤트)
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DAY_OF_MONTH, 7);
        events.add(new EventDay(calendar3, R.drawable.sample_four_icons));

        // Calendar4 객체(단순 이벤트)
        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DAY_OF_MONTH, 13);
        events.add(new EventDay(calendar4, DrawableUtils.getThreeDots(this)));



        // 캘린더 뷰
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        // 캘린더 뷰에서 display할 이전 달 개수 설정
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -2);

        // 캘린더 뷰에서 display할 다음 달 개수 설정
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 2);

        // 캘린더 뷰의 MinimumDate, MaximumDate 설정
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        // 캘린더 객체들 일괄 등록
         calendarView.setEvents(events);

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

    // disable시킬 날짜 반환하는 함수
    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        // DisabledDays에서의 amount는 현재
        // firstDisabled.add(Calendar.DAY_OF_MONTH, -1);
        firstDisabled.add(Calendar.DAY_OF_YEAR, -1);

//        Calendar secondDisabled = DateUtils.getCalendar();
//        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);
//
//        Calendar thirdDisabled = DateUtils.getCalendar();
//        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
//        calendars.add(secondDisabled);
//        calendars.add(thirdDisabled);
        return calendars;
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}

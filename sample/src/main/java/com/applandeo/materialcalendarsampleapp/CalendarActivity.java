package com.applandeo.materialcalendarsampleapp;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.applandeo.materialcalendarsampleapp.utils.DrawableUtils;
import com.applandeo.materialcalendarsampleapp.view.DateActivity;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Kornakiewicz on 26.05.2017.
 */

public class CalendarActivity extends BlankActivity {

    private final String TAG = "my"+CalendarActivity.class.getSimpleName();
    CalendarView calendarView;
    CalendarService calendarService;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_btn1 :
                navigateTo(DateActivity.class, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        // schedule card view
        View container = findViewById(R.id.schedule_card_container);

        // calendarView, calendarService
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarService = new CalendarService(calendarView, this, -12, 12, container);


        // 더미 데이터 추가 코드
//        insertDummy3();




        // schedules 배열 채우기
        calendarService.putSchedules();

        // 현재 month icon 추가
        calendarService.putIconOnCalendar();

//         날짜 클릭 이벤트 핸들러 (스크롤 뷰에 스케쥴 카드 추가
        calendarView.setOnDayClickListener(eventDay ->
            {
                int year = eventDay.getCalendar().get(Calendar.YEAR);
                int month = eventDay.getCalendar().get(Calendar.MONTH);
                int day = eventDay.getCalendar().get(Calendar.DAY_OF_MONTH);
                calendarService.displayScheduleCard(container, year, month+1, day);
             }
        );

        // 임시버튼, 스크롤 뷰 비우기
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calendarService.clearContainer((ViewGroup)container);
            }
        });


        // 임시버튼, 임의 날짜 스케줄 추가, 아이콘 변경, 스크롤 뷰는 미갱신
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calendarService.addEvent(2020, 12, 26, "hi title", "dddd", "파리");
            }
        });
    }

    private void insertDummy() {
        calendarService.addEvent(2020, 12, 20, "Test", "testContnetn1", "논현");
        calendarService.addEvent(2020, 12, 21, "Test2", "testContnetn2",  "청량리");
        calendarService.addEvent(2020, 12, 21, "Test2", "testContnetn2",  "청량리");
        calendarService.addEvent(2020, 12, 21, "Test2", "testContnetn2",  "청량리");
        calendarService.addEvent(2020, 12, 21, "Test2", "testContnetn2",  "청량리");
        calendarService.addEvent(2020, 12, 21, "Test2", "testContnetn2",  "청량리");
        calendarService.addEvent(2020, 12, 21, "Test2", "testContnetn2",  "청량리");
        calendarService.addEvent(2020, 12, 22, "Test3", "testContnetn3", "판교");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 23, "Test4", "testContnetn4", "왕십리");
        calendarService.addEvent(2020, 12, 25, "Test7", "testContnetn5", "회기");
        calendarService.addEvent(2020, 12, 25, "Test7", "testContnetn5", "회기");
        calendarService.addEvent(2020, 12, 25, "Test7", "testContnetn5", "회기");
        calendarService.addEvent(2020, 12, 25, "Test7", "testContnetn5", "회기");
        calendarService.addEvent(2020, 12, 25, "Test7", "testContnetn5", "회기");
        calendarService.addEvent(2020, 12, 25, "Test7", "testContnetn5", "회기");
        calendarService.addEvent(2020, 12, 31, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 12, 31, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 12, 31, "Test8", "testContnetn5", "상봉");
    }
    public void insertDummy2(){
        calendarService.addEvent(2020, 11, 31, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 31, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 31, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 31, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 31, "Test8", "testContnetn5", "상봉");

        calendarService.addEvent(2020, 11, 11, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 12, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 12, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 12, "Test8", "testContnetn5", "상봉");


        calendarService.addEvent(2020, 11, 1, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 1, "Test8", "testContnetn5", "상봉");
        calendarService.addEvent(2020, 11, 1, "Test8", "testContnetn5", "상봉");
    }

    public void insertDummy3(){
        for (int i = 1; i <= 31; i++) {
            Log.d(TAG, (int)(10*Math.random())+"");
            for (int j = 0; j < (int)(10*Math.random()); j++) {
                calendarService.addEvent(2021, 1, i, "Test" + i, "testContnent" + i, "hi");
            }
        }
    }

    // disable시킬 날짜 반환하는 함수
    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        // DisabledDays에서의 amount는 현재
        firstDisabled.add(Calendar.DAY_OF_MONTH, -1);
        firstDisabled.add(Calendar.DAY_OF_YEAR, -1);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}

package com.applandeo.materialcalendarsampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleAddActivity extends BlankActivity {
    /**
     * todo: 1. 기간 선택 화면 구현
     *       2. 완료 시 DB 저장
      */

    /**
     * todo: 기간 선택 화면 시
     *       1. OneDayPicker, ManyDaysPicker, RangePicker 모드 필요
     */
    private final String TAG = ScheduleAddActivity.class.getSimpleName();
    private CalendarService calendarService;

    TextView scheduleTitle;
    TextView scheduleDate;
    TextView schedulePlace;
    TextView scheduleContent;

    Button oneDayButton;
    Button manyDaysButton;
    Button rangeDaysButton;
    Button reSelectButton;

    LinearLayout button3Layout;
    LinearLayout dateLayout;

    List<Calendar> selectedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);

        calendarService = CalendarActivity.getCalendarService();

        scheduleTitle = findViewById(R.id.schedule_add_title);
        scheduleDate = findViewById(R.id.schedule_add_date);
        schedulePlace = findViewById(R.id.schedule_add_place);
        scheduleContent = findViewById(R.id.schedule_add_content);

        button3Layout = findViewById(R.id.button3_layout);
        dateLayout = findViewById(R.id.schedule_add_date_hide);

        reSelectButton = findViewById(R.id.re_select);

        selectedCalendar = new ArrayList();

        // 글 작성 완료 버튼 클릭
        Button okButton = findViewById(R.id.btn_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = scheduleTitle.getText().toString();
                String content = scheduleContent.getText().toString();
                String place = schedulePlace.getText().toString();

                String year = "";
                String month = "";
                String day = "";

                Schedule s;

                if(selectedCalendar.size() == 1) {
                    Calendar c = selectedCalendar.get(0);
                    year = Integer.toString(c.get(Calendar.YEAR));
                    month = Integer.toString(c.get(Calendar.MONTH));
                    day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
                    s = new Schedule(title, content, year, month, day, place);
                    calendarService.addSchedule(s);
                }
                else{
                    for (int i = 0; i < selectedCalendar.size(); i++) {
                        Calendar c = selectedCalendar.get(i);
                        year = Integer.toString(c.get(Calendar.YEAR));
                        month = Integer.toString(c.get(Calendar.MONTH));
                        day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));

                        s = new Schedule(title, content, year, month, day, place);
                        calendarService.addSchedule(s);
                    }
                }

                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        // 글 취소 버튼 클릭
        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 단일 날짜 선택 리스너
        OnSelectDateListener listenerOneDay = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                Calendar c = calendars.get(0);
                selectedCalendar.clear();
                selectedCalendar.add(c);
                String displayedDate = c.get(Calendar.YEAR) + "." + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.DAY_OF_MONTH);
                toggleDate(displayedDate);
            }
        };

        // 단일 날짜 date picker
        oneDayButton = findViewById(R.id.one_day_button);
        oneDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder builder = new DatePickerBuilder(ScheduleAddActivity.this, listenerOneDay)
                        .pickerType(CalendarView.ONE_DAY_PICKER);
                DatePicker datePicker = builder.build();
                datePicker.show();
            }
        });

        // 복수 날짜 선택 리스너
        OnSelectDateListener listenerManyDays = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                Log.d(TAG, "복수 날짜 호출");
                String displayedDate = "";

                int daysSize = calendars.size();
                if(daysSize > 2){
                    displayedDate += calendars.get(0).get(Calendar.YEAR) + "." + calendars.get(0).get(Calendar.MONTH) + "." + calendars.get(0).get(Calendar.DAY_OF_MONTH) + " 등 " + daysSize + "개";
                }
                else{
                    for (int i = 0; i < calendars.size(); i++) {
                        if(i > 0)   displayedDate += ", ";
                        displayedDate += calendars.get(i).get(Calendar.YEAR) + "." + calendars.get(i).get(Calendar.MONTH) + "." + calendars.get(i).get(Calendar.DAY_OF_MONTH);
                    }
                }
                toggleDate(displayedDate);

                selectedCalendar.clear();
                selectedCalendar.addAll(calendars);
            }
        };

        // 복수 날짜 date picker
        manyDaysButton = findViewById(R.id.many_days_button);
        manyDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder builder = new DatePickerBuilder(ScheduleAddActivity.this, listenerManyDays)
                        .pickerType(CalendarView.MANY_DAYS_PICKER);
                DatePicker datePicker = builder.build();
                datePicker.show();
            }
        });

        // 범위 날짜 선택 리스너
        OnSelectDateListener listenerRangeDays = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                Log.d(TAG, "범위 날짜 호출");
                String displayedDate = "";
                int lastIndex = calendars.size() - 1;

//                for (int i = 0; i < calendars.size(); i++) {
//                    Log.d(TAG, calendars.get(i).get(Calendar.DAY_OF_MONTH)+"");
//                }

                if(calendars.size() == 1){
                    displayedDate = calendars.get(0).get(Calendar.YEAR) + "." + calendars.get(0).get(Calendar.MONTH) + calendars.get(0).get(Calendar.DAY_OF_MONTH);
                }
                else{
                    displayedDate = calendars.get(0).get(Calendar.YEAR) + "." + calendars.get(0).get(Calendar.MONTH) + "." + calendars.get(0).get(Calendar.DAY_OF_MONTH) + " ~ " +
                            calendars.get(lastIndex).get(Calendar.YEAR) + "." + calendars.get(lastIndex).get(Calendar.MONTH) + "." + calendars.get(lastIndex).get(Calendar.DAY_OF_MONTH);
                }

                toggleDate(displayedDate);

                selectedCalendar.clear();
                selectedCalendar.addAll(calendars);
            }
        };

        // 범위 날짜 추가.
        rangeDaysButton = findViewById(R.id.range_picker_button);
        rangeDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder builder = new DatePickerBuilder(ScheduleAddActivity.this, listenerRangeDays)
                        .pickerType(CalendarView.RANGE_PICKER);
                DatePicker datePicker = builder.build();
                datePicker.show();
            }
        });



        // date 선택된 상태로 다시 선택하기.
        reSelectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toggleDate(null);
            }
        });

    }

    private void toggleDate(String displayedDate) {
        if(button3Layout.getVisibility() == View.GONE){
            dateLayout.setVisibility(View.GONE);

            button3Layout.setVisibility(View.VISIBLE);
        }
        else{
            button3Layout.setVisibility(View.GONE);

            scheduleDate.setText(displayedDate);
            dateLayout.setVisibility(View.VISIBLE);
        }

    }
}
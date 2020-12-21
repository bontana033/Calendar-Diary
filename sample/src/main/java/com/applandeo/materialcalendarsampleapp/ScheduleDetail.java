package com.applandeo.materialcalendarsampleapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScheduleDetail extends AppCompatActivity {
    private final String TAG = ScheduleDetail.class.getSimpleName();
    CalendarService calendarService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        calendarService = new CalendarService(this);
//        calendarService = new CalendarService();
        // calendarService.getOneSchedule(schedule_detail_id);


        TextView title = findViewById(R.id.schedule_detail_title);
        TextView date = findViewById(R.id.schedule_detail_date);
        TextView place = findViewById(R.id.schedule_detail_place);
        TextView content = findViewById(R.id.schedule_detail_content);

//        place.setText("placeTest");
//        date.setText("dateTest");
//        content.setText("contentTest12");

        Intent intent = getIntent();
        int scheduleId = Integer.parseInt(intent.getStringExtra("scheduleId"));
        Log.d(TAG, scheduleId+" hi");

        Schedule schedule = calendarService.getOneSchedule(scheduleId);

        title.setText(schedule.title);
        String _date = schedule.year + "." + schedule.month + "." + schedule.day;
        date.setText(_date);
        place.setText(schedule.place);
        content.setText(schedule.content);


        ImageButton imageButtonBackSpace = findViewById(R.id.imageButton_backspace);
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
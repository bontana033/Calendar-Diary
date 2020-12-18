package com.applandeo.materialcalendarsampleapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarsampleapp.utils.DrawableUtils;
import com.applandeo.materialcalendarsampleapp.view.DateActivity;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Kornakiewicz on 26.05.2017.
 */

public class CalendarActivity extends BlankActivity {

    private final String TAG = "my"+CalendarActivity.class.getSimpleName();

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

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        CalendarService calendarService = new CalendarService(calendarView, this, -12, 12);

        // calendarService.addEvent(2020, 12, 13);
        // calendarService.addEvent(2020, 12, 13);

        insertDummy();




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

    private void insertDummy() {
        DBHelper helper =new DBHelper(this);
        SQLiteDatabase db =helper.getWritableDatabase();
        db.execSQL("delete from calendar");

        ContentValues values = new ContentValues();
        values.put("title", "testTitle");
        values.put("content", "hi, i'm bong");

        db.insert("calendar", null, values);

        ContentValues values2 = new ContentValues();
        values2.put("title", "toystory");
        values2.put("content", "hi, i'm kim");

        db.insert("calendar", null, values2);

        Cursor cursor = db.rawQuery("select title, content from calendar order by _id", null);
        Log.d(TAG, "hi");
        Log.d(TAG, Integer.toString(cursor.getCount()));
        cursor.moveToFirst();
        do{
            Log.d(TAG, cursor.toString());
            Log.d(TAG, cursor.getString(0));
        }while(cursor.moveToNext());

        Log.d(TAG, "logcatcat3");
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

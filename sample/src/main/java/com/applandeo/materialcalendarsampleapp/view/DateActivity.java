package com.applandeo.materialcalendarsampleapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarsampleapp.BlankActivity;
import com.applandeo.materialcalendarsampleapp.CalendarActivity;
import com.applandeo.materialcalendarsampleapp.ManyDaysPickerActivity;
import com.applandeo.materialcalendarsampleapp.R;
import com.applandeo.materialcalendarsampleapp.RangePickerActivity;
import com.applandeo.materialcalendarsampleapp.view.dialog.DatePickerDialog;

public class DateActivity extends BlankActivity {
    /**
     * todo: 1. 기간 선택 화면 구현
     *       2. 완료 시 DB 저장
      */

    /**
     * todo: 기간 선택 화면 시
     *       1. OneDayPicker, ManyDaysPicker, RangePicker 모드 필요
     */
    private final String TAG = DateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);



        // 글 작성 완료 버튼 클릭
        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "btn_ok");

                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        // 글 취소 버튼 클릭
        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "btn_cancle");

                Toast.makeText(getApplicationContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        // 기간 클릭 시 date range dialog 나오게 하기.
        TextView t_dateRange = findViewById(R.id.t_dateRange);
        t_dateRange.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.ActivityDialogTheme);
            dialog.show();
        });
    }
}
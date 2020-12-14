package com.applandeo.materialcalendarsampleapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.applandeo.materialcalendarsampleapp.BlankActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(l -> finish() );
        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(l -> finish() );

        TextView t_dateRange = findViewById(R.id.t_dateRange);
        t_dateRange.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.ActivityDialogTheme);
            dialog.show();
        });
    }
}
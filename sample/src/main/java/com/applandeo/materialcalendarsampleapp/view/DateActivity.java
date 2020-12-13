package com.applandeo.materialcalendarsampleapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.applandeo.materialcalendarsampleapp.BlankActivity;
import com.applandeo.materialcalendarsampleapp.R;

public class DateActivity extends BlankActivity {
    /**
     * todo: 1. 기간 선택 화면 구현
     *       2. 완료 시 DB 저장
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(l -> finish() );
        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(l -> finish() );
    }
}
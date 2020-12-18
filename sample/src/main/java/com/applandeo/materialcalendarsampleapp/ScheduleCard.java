package com.applandeo.materialcalendarsampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ScheduleCard extends LinearLayout {

    public ScheduleCard(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_schedule_card, this, true);
    }
}
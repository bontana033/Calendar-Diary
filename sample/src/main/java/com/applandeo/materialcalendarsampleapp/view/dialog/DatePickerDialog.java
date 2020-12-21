package com.applandeo.materialcalendarsampleapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applandeo.materialcalendarsampleapp.R;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;

import java.util.Calendar;
import java.util.List;

/**
 * btn_single : OneDayPicker Mode
 * btn_range : RangePicker Mode
 * btn_multiple : ManyDaysPicker Mode
 */
public class DatePickerDialog extends Dialog {
    private final String TAG = DatePickerDialog.class.getSimpleName();
    Context context;

    public DatePickerDialog(@NonNull Context context) {
        super(context);
    }

//    public DatePickerDialog(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
//    }

    public DatePickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }



    protected DatePickerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker_dialog);

//        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
//
//        Button btn_single = findViewById(R.id.btn_single);
//        btn_single.setOnClickListener(l -> {
//            if (calendarView.getCalendarType() == CalendarView.ONE_DAY_PICKER) {
//                return;
//            }
//            calendarView.setCalendarType(CalendarView.ONE_DAY_PICKER);
//            calendarView.reset(Calendar.getInstance());
//        });
//        Button btn_range = findViewById(R.id.btn_range);
//        btn_range.setOnClickListener(l -> {
//            if (calendarView.getCalendarType() == CalendarView.RANGE_PICKER) {
//                return;
//            }
//            calendarView.setCalendarType(CalendarView.RANGE_PICKER);
//            calendarView.reset(Calendar.getInstance());
//        });
//        Button btn_multiple = findViewById(R.id.btn_multiple);
//        btn_multiple.setOnClickListener(l -> {
//            if (calendarView.getCalendarType() == CalendarView.MANY_DAYS_PICKER) {
//                return;
//            }
//            calendarView.setCalendarType(CalendarView.MANY_DAYS_PICKER);
//            calendarView.reset(Calendar.getInstance());
//        });

        DatePickerBuilder builder = new DatePickerBuilder(context, listener)
                .pickerType(CalendarView.ONE_DAY_PICKER);

        DatePicker datePicker = builder.build();
        datePicker.show();

    }

    private OnSelectDateListener listener = new OnSelectDateListener() {
        @Override
        public void onSelect(List<Calendar> calendars) {
            Log.d(TAG, "I'm Listening");
        }
    };
}

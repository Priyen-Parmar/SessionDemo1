package com.session.demo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatetimePickerActivity extends AppCompatActivity {

    EditText dateEdit, timeEdit;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime_picker);

        dateEdit = findViewById(R.id.date_picker);
        timeEdit = findViewById(R.id.time_picker);

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateEdit.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        TimePickerDialog.OnTimeSetListener timeClick = new TimePickerDialog.OnTimeSetListener() {
               @Override
               public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                   // Format the selected time and set it to the EditText
                   calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                   calendar.set(Calendar.MINUTE, minute);

                   String amPm = (hourOfDay < 12) ? "AM" : "PM";

                   String formattedTime = String.format("%02d:%02d %s", hourOfDay, minute, amPm);
                   timeEdit.setText(formattedTime);
               }
        };

        dateEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DatePickerDialog datePicker = new DatePickerDialog(DatetimePickerActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                //current date
                //datePicker.getDatePicker().setMinDate(System.currentTimeMillis());

                //Age 18 or above
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - (18 * 365 * 24 * 60 * 60 * 1000L));
                datePicker.show();

                return true;
            }
        });



        timeEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(DatetimePickerActivity.this, timeClick, hour, minute, false);
                timePicker.show();
                return true;
            }
        });

    }
}
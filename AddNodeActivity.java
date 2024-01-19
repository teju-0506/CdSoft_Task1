package com.example.notetakingapplication;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddNodeActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    EditText taskdate;
    EditText tasktime;
    TimePickerDialog timePickerDialog;
    int mYear, mMonth, mDay;
    int mHour, mMinute;
    DatePickerDialog datePickerDialog;
    Button cancle;
    Button save;
    public static int count = 0;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_node);
        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        title=findViewById(R.id.Title);
        description=findViewById(R.id.editTextDescription);
        cancle=findViewById(R.id.cancel);
        taskdate=findViewById(R.id.taskdate);
        tasktime=findViewById(R.id.tasktime);
        save=findViewById(R.id.save);

        taskdate.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            taskdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        tasktime.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        (view12, hourOfDay, minute) -> {
                            tasktime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Nothing Saved",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    public void saveNote(){

        if(validateFields()){
            String noteTitle=title.getText().toString();
            String noteDescription=description.getText().toString();
            String Date=taskdate.getText().toString();
            String Time=tasktime.getText().toString();

            Intent intent=new Intent();
            intent.putExtra("noteTitle",noteTitle);
            intent.putExtra("noteDescription",noteDescription);
            intent.putExtra("noteDate",Date);
            intent.putExtra("noteTime",Time);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    public boolean validateFields() {
        if(title.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please enter a valid title", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(description.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please enter a valid description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(taskdate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(tasktime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please enter time", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

}
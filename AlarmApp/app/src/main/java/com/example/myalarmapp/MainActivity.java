package com.example.myalarmapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private Button setAlarm;
    private Button cancelAlarm;
    private TextView textView;
    private Context context;
    private Calendar calendar;
    private PendingIntent pendingIntent;

    private DatePicker datePicker;
    private TimePicker timePicker;

    private long timeInMilis;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();


        setAlarm = findViewById(R.id.date_time_set);
        cancelAlarm = findViewById(R.id.cancel_button);
        textView = findViewById(R.id.textAlarm);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        Intent alarmIntent = new Intent(this.context, Alarm_Receiver.class);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                textView.setText("Alarm is on");

                mYear = datePicker.getYear();
                mMonth = datePicker.getMonth();
                mDay = datePicker.getDayOfMonth();

                mHour = timePicker.getHour();
                mMinute = timePicker.getMinute();

                calendar.set(mYear, mMonth, mDay, mHour, mMinute);

                textView.setText("Date: " + mYear + "/" + (mMonth + 1) + "/" + mDay + "\nTime: " + mHour + ":" + mMinute);

                alarmIntent.putExtra("command", "setAlarm");

                timeInMilis = calendar.getTimeInMillis();
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilis, pendingIntent);
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmIntent.putExtra("command", "turnOffAlarm");
                sendBroadcast(alarmIntent);
                alarmManager.cancel(pendingIntent);
                textView.setText("Alarm is off");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
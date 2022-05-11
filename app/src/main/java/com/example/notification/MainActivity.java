package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int notificationid=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.setbt).setOnClickListener(this);
        findViewById(R.id.cancelbt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText editText=findViewById(R.id.edit);
        TimePicker picker=findViewById(R.id.time);

        Intent intent =new Intent(MainActivity.this,AlarmReceiver.class);
        intent.putExtra("notification",notificationid);
        intent.putExtra("message",editText.getText().toString());

        PendingIntent pendingIntent=PendingIntent.getBroadcast(
                MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT
        );

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        switch(view.getId()){
            case R.id.setbt:
                int hour=picker.getCurrentHour();
                int minute = picker.getCurrentMinute();
                Calendar calendar= Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);
                long alarmstart=calendar.getTimeInMillis();
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmstart,pendingIntent);
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                break;




            case R.id.cancelbt:
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
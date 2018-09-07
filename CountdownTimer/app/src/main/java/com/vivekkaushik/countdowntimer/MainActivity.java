package com.vivekkaushik.countdowntimer;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    TextView timer, endTimeView;
    Button button, button2, button3;

    long endTime = 0;
    long currentTime = 0;
    long remainingTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        endTimeView = findViewById(R.id.timerEndTime);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                clockSetup(3 * 1000);
                break;
            case R.id.button2:
                clockSetup(10 * 60 * 1000);
                break;
            case R.id.button3:
                clockSetup(30 * 60 * 1000);
                break;
        }
    }

    void clockSetup(long time) {
        Date cTime = Calendar.getInstance().getTime();
        currentTime = cTime.getTime();
        endTime = currentTime + time;
        remainingTime = endTime - currentTime;

        DateFormat formatter = new SimpleDateFormat("HH:mm", getCurrentLocale());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String text = "Countdown is set for " + formatter.format(new Date(endTime));
        endTimeView.setText(text);

        final Handler handler=new Handler();

        final Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                updateTime();
                if(remainingTime > 0) {
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.postDelayed(updateTask,0);
    }

    void updateTime() {
        DateFormat formatter = new SimpleDateFormat("mm:ss", getCurrentLocale());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String text = formatter.format(new Date(remainingTime));

        timer.setText(text);

        Date cTime = Calendar.getInstance().getTime();
        currentTime = cTime.getTime();
        remainingTime = endTime - currentTime;
    }

    Locale getCurrentLocale(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return getResources().getConfiguration().locale;
        }
    }
}

package com.example.homework6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // поля для вывода на экран нужных значений
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonTimer;
    private TextView watchOut;
    // дополнительные поля логики
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timePause = 0L;
    private long updatedTime = 0L;
    private boolean timeMod;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // присваивание переменным элементов представления activity_main
        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonTimer = findViewById(R.id.buttonTimer);
        watchOut = findViewById(R.id.watchOut);
        // выполнение действий при нажатии кнопки
        buttonStart.setOnClickListener(listener);
        buttonPause.setOnClickListener(listener);
        buttonTimer.setOnClickListener(listener);    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonStart:
                    timeMod = true;
                    startTime = SystemClock.uptimeMillis();
                    if (SystemClock.uptimeMillis() - startTime == 0L) {
                        timePause = 0L;
                        timePause = updatedTime;
                    } else {
                        timeMod = true;
                    }
                    handler.postDelayed(updateTimerThread, 0);
                    break;
                case R.id.buttonPause:
                    timePause = 0L;
                    timePause = updatedTime;
                    handler.removeCallbacks(updateTimerThread);
                    break;
                case  R.id.buttonTimer:
                    timeMod = false;
                    startTime = SystemClock.uptimeMillis();
                    if (SystemClock.uptimeMillis() - startTime == 0L) {
                    timePause = 0L;
                    timePause = updatedTime;
                } else {
                    timeMod = false;
                }
                    handler.postDelayed(updateTimerThread, 0);
                    break;
            }
        }
    };


    // создание нового потока для обновления времени спомощью объекта интерфейса Runnable
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (timeMod) {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timePause + timeInMilliseconds;
            } else {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timePause - timeInMilliseconds;
            }
            if (updatedTime < 0) {
                updatedTime = 0L;
                startTime = SystemClock.uptimeMillis();
                handler.removeCallbacks(updateTimerThread);
                watchOut.setText("0:00:00:00:000");
                return;
            }


            int milliseconds = (int) (updatedTime % 1000);
            int second = (int) (updatedTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;
            int day = hour / 24;

            second = second % 60;
            minute = minute % 60;
            hour = hour % 24;

            // запись времени в окне вывода информации
            watchOut.setText("" + day + ":" + String.format("%02d",hour) + ":" + String.format("%02d",minute) + ":" + String.format("%02d", second) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}
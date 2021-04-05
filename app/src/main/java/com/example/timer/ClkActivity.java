package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.timer.util.DbManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ClkActivity extends AppCompatActivity {
    private static long time;
    Chronometer clk;
    Button start;
    Button pause;
    Button go;
    private int tag = 0;
    private long recordingTime = 0;
    private long totalTime = 0;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clk);
        //获取计时器组件
        clk = findViewById(R.id.test);
        //获取开始按钮
        start = (Button) findViewById(R.id.start) ;
        //暂停计时按钮
        pause = (Button) findViewById(R.id.pause);
        //继续计时按钮
        go = (Button) findViewById(R.id.go_on);

//        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
//        time = System.currentTimeMillis();
//        clk.setFormat("%h");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tag = 1;
                Toast.makeText(ClkActivity.this,"开始记时", Toast.LENGTH_LONG).show();
                clk.setBase((SystemClock.elapsedRealtime()-recordingTime));
                clk.start();
                pause.setEnabled(true);
                go.setEnabled(false);
                start.setEnabled(false);
            }
        });
        //暂停按钮监听器
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClkActivity.this,"暂停计时", Toast.LENGTH_LONG).show();
                clk.stop();
                recordingTime = SystemClock.elapsedRealtime()-clk.getBase();
                start.setEnabled(true);
                go.setEnabled(true);
                pause.setEnabled(false);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //存入数据库  (SystemClock.elapsedRealtime() - clk.getBase()) / 1000) s
                long t = (recordingTime) / 1000;
                Toast.makeText(ClkActivity.this,t+"",Toast.LENGTH_LONG).show();
                ContentValues values = new ContentValues();
                values.put("time",t);
                SQLiteDatabase db = DbManager.getInstance(ClkActivity.this).dbHelper.getWritableDatabase();
                db.insert("Time_Record",null,values);

                Toast.makeText(ClkActivity.this,"停止计时", Toast.LENGTH_LONG).show();
                recordingTime = 0;
                tag = 1;
                clk.setBase(SystemClock.elapsedRealtime());
                clk.stop();
                start.setEnabled(true);
                pause.setEnabled(true);
                go.setEnabled(false);
            }
        });
        //为Chronomter绑定事件监听器
        clk.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //如果计时到现在超过了一小时秒
                 if(tag == 1 ) {
                     clk.setText(FormatMiss((SystemClock.elapsedRealtime() - clk.getBase()) / 1000));
                 }
                 else{
                     clk.setText("00:00:00");
                 }

            }
        });
    }

    public static String FormatMiss(long time){
        String hh=time/3600>9?time/3600+"":"0"+time/3600;
        String mm=(time% 3600)/60>9?(time% 3600)/60+"":"0"+(time% 3600)/60;
        String ss=(time% 3600) % 60>9?(time% 3600) % 60+"":"0"+(time% 3600) % 60;
        return hh+":"+mm+":"+ss;
    }
}
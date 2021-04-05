package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timer.util.DbManager;
import com.example.timer.util.FeedReaderContract;

public class CreateGoalActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        btn = (Button) findViewById(R.id.create_ok);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGoalActivity.this,HabitActivity.class);
                TextView Name = (TextView) findViewById(R.id.Goals_name);
                TextView Describe = (TextView) findViewById(R.id.Describe);
                TextView Days = (TextView) findViewById(R.id.days);
                String name = Name.getText().toString();
                String describe = Describe.getText().toString();
                String day = Days.getText().toString();
                String tag  = "0";
                if(name.length()!=0){
                    //插入数据
                    ContentValues values = new ContentValues();
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,name);
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT,describe);
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,day);
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE,tag);
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TAG,0);
                    SQLiteDatabase db = DbManager.getInstance(CreateGoalActivity.this).dbHelper.getWritableDatabase();
                    //dbHelper.onUpgrade(db,0,1);
                    long newR = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,null,values);


                    //创建相应的表
                    //创建新表
                    String create_table =
                            "CREATE TABLE " + name + " (" +
                            "ID" + " INTEGER PRIMARY KEY," +
                            "days" + " TEXT" + ")";
                    db.execSQL(create_table);

                    Toast.makeText(CreateGoalActivity.this, "创建成功", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(CreateGoalActivity.this, "名字信息不能为空", Toast.LENGTH_LONG).show();
                }
                //startActivity(intent);

            }
        });

    }
}
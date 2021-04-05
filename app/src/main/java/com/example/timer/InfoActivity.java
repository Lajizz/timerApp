package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timer.util.DbManager;
import com.example.timer.util.FeedReaderContract;
import com.example.timer.util.FeedReaderDbHelper;
import com.example.timer.util.Fruit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    private TextView info_name;
    private TextView info_content;
    private TextView info_daka;
    private  Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        String message = intent.getStringExtra(HabitActivity.EXTRA_MESSAGE);
        btn = (Button) findViewById(R.id.xxx);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  deleteGoal(message);
            }
        });
        info_name = (TextView) findViewById(R.id.info_name);
        info_content = (TextView) findViewById(R.id.info_content);
        info_daka = (TextView) findViewById(R.id.info_times);
        info_name.setText(message);
        display(message);
        displayDaka(message);

    }

    public void display(String message){
        SQLiteDatabase db = DbManager.getInstance(this).dbHelper.getWritableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE+ " = ?";
        String[] selectionArgs = { message };
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        int itemid = 0;
        String itemname = "1";
        String itemcontent = "2";
        String days = "3";
        String isfinished = "4";

        while(cursor.moveToNext()) {
            itemid = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));

            itemname = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)
            );
            itemcontent = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT)
            );
            days = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME)
            );
            isfinished = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE)
            );
        }
        String str = "没有完成";
        if(Integer.parseInt(isfinished)==1)
        {
            str="已经完成";
        }
        Toast.makeText(this, isfinished, Toast.LENGTH_LONG).show();
        info_content.setText(itemcontent+"\n"+str);
        //cursor.close();

    }

    public void displayDaka(String message){
        SQLiteDatabase db = DbManager.getInstance(this).dbHelper.getWritableDatabase();
        String context = "";
        String[] projection = {"ID","days"};
        String selection = "ID"+ " >= ?";
        String[] selectionArgs = { "0"};
        String sortOrder = "ID" + " DESC";
        Cursor cursor = db.query(
                message,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while(cursor.moveToNext()) {
            context += cursor.getString(cursor.getColumnIndexOrThrow("days")) + '\n';
        }
        info_daka.setText((context));

    }

    public void deleteGoal(String message){
        SQLiteDatabase db  = DbManager.getInstance(this).dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TAG,1);
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + "= ?";
        String[] selectionArgs = { message };
//        String[] selectionArgs = { "www" };

        db.update("Goal",values,selection,selectionArgs);
    }
}
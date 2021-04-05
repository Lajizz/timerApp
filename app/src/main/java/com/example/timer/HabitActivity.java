package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.timer.util.DbManager;
import com.example.timer.util.FeedReaderContract;
import com.example.timer.util.FeedReaderDbHelper;
import com.example.timer.util.Fruit;
import com.example.timer.util.FruitAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.timer.util.DbManager.dbHelper;

public class HabitActivity extends AppCompatActivity {
    private List<Fruit> fruitList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FruitAdapter adapter;
    FloatingActionButton btn;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HabitActivity.this,CreateGoalActivity.class);
                startActivity(intent);
            }
        });

        initAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_views);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListen(new FruitAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onClick(int position){

                Intent intent = new Intent(HabitActivity.this,InfoActivity.class);
                String message =fruitList.get(position).getName();
                intent.putExtra(EXTRA_MESSAGE, message);
                Toast.makeText(HabitActivity.this, message, Toast.LENGTH_LONG).show();
                startActivity(intent);

            }

            @Override
            public void btnClick(int position) {
                int tag = fruitList.get(position).getBtnName();
                String Name = fruitList.get(position).getName();
                //目前时间
                Calendar now = Calendar.getInstance();
                String time = ""+now.get(Calendar.YEAR) + (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH);
                //上次打卡时间
                SQLiteDatabase db = DbManager.getInstance(HabitActivity.this).dbHelper.getWritableDatabase();
                String[] projection = {
                        "ID",
                        "days"
                };
                String selection = "days" + " >= ?";
                String[] selectionArgs = { "0" };
                // How you want the results sorted in the resulting Cursor
                String sortOrder = "ID" + " DESC";

                Cursor cursor = db.query(
                        Name,   // The table to query
                        projection,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        sortOrder               // The sort order
                );
                int times = cursor.getCount();
                changeState(times,Name);
                if(cursor.moveToNext()){
                    int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String day = cursor.getString(cursor.getColumnIndexOrThrow("days"));
                    if(day.equals(time))
                    {
                        Toast.makeText(HabitActivity.this, "今日已打卡", Toast.LENGTH_LONG).show();
                    }else{
                        ContentValues values = new ContentValues();
                        values.put("ID",itemId+1);
                        values.put("days",time);
                        db.insert(Name,null,values);
                        Toast.makeText(HabitActivity.this, time+" 打卡成功", Toast.LENGTH_LONG).show();

                    }

                }
                else{
                    ContentValues values = new ContentValues();
                    values.put("ID",0);
                    values.put("days",time);
                    db.insert(Name,null,values);
                    Toast.makeText(HabitActivity.this, time+" 打卡成功", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void initAdapter(){

        SQLiteDatabase db = DbManager.getInstance(this).dbHelper.getWritableDatabase();
//
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };
//
//        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TAG + " = ?";
        String[] selectionArgs = { "0" };
//
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " ASC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        List itemNames = new ArrayList();
        while(cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            itemIds.add(itemId);

            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)
            );

            //getTag


            itemNames.add(itemName);
        }
        Log.i("getname:", String.valueOf(itemIds.size()));
        for (int i = 0; i < itemIds.size(); i++) {
            //Log.i("info: ",itemNames.get(i));
            Fruit apple = new Fruit(itemNames.get(i).toString(), R.drawable.item,0);
            fruitList.add(apple);
        }
//        cursor.close();
    }


    public void changeState(int times,String Name){
        SQLiteDatabase db = DbManager.getInstance(this).dbHelper.getWritableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { Name };
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
        if(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME));
            if(itemId<=times){
                //更新数据
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE,"1");
                db.update(FeedReaderContract.FeedEntry.TABLE_NAME,values, FeedReaderContract.FeedEntry._ID +"=?",new String[]{ id+"" });
            }
        }

    }
}


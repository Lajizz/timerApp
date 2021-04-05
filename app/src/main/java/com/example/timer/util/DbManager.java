package com.example.timer.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {
    public static DbManager dbmanager;
    public static SQLiteDatabase db;
    public static FeedReaderDbHelper dbHelper;
    private DbManager(Context context){
       dbHelper = new FeedReaderDbHelper(context);
       db = dbHelper.getWritableDatabase();
        //创建新表
//        String create_table =
//                "CREATE TABLE " + "Time_Record" + " (" +
//                        "time" + " INTEGER" + ")";
//        db.execSQL(create_table);
    }
    public synchronized static DbManager getInstance(Context context){
        if(dbmanager == null)
        {
            dbmanager = new DbManager(context);
        }
        return dbmanager;
    }
}

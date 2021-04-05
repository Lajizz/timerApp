package com.example.timer.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "FeedReader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT + " Integer," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TIME + " Integer," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TAG + " Integer," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " Integer)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context){
        super(context,DATA_BASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
        String create_table =
                "CREATE TABLE " + "Time_Record" + " (" +
                        "time" + " INTEGER" + ")";
        db.execSQL(create_table);
        create_table = "CREATE TABLE " + "BG" + " (" +
                FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                "path" + " TEXT)";
        db.execSQL(create_table);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }

}

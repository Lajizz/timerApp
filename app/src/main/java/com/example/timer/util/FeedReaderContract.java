package com.example.timer.util;

import android.app.usage.UsageStats;
import android.provider.BaseColumns;

public class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Goal";
        public static final String COLUMN_NAME_TITLE = "Name";
        public static final String COLUMN_NAME_CONTEXT = "Context";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_SUBTITLE = "IsFinished";
        public static final String COLUMN_NAME_TAG = "IsDeleted";

    }

//    public static class Goal implements BaseColumns {
//        public static  String TABLE_NAME;
//        public static   String COLUMN_NAME_TITLE;
//        public static String COLUMN_NAME_SUBTITLE;
//    }

}

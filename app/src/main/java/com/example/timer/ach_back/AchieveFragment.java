package com.example.timer.ach_back;

import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timer.R;
import com.example.timer.util.DbManager;
import com.example.timer.util.FeedReaderContract;
import com.example.timer.util.Item;
import com.example.timer.util.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class AchieveFragment extends Fragment {
    TextView max_time;
    TextView total_time;
    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ItemAdapter adapter;
    private AchieveViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AchieveViewModel.class);
        View root = inflater.inflate(R.layout.achieve_fragment, container, false);

        max_time = (TextView) root.findViewById(R.id.max_time);
        total_time = (TextView) root.findViewById(R.id.total_time);
        max_time.setText(getMaxTime()+"h");
        total_time.setText(getTotalTime()+"h");

        //initAdapter();

//        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_ach);
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new ItemAdapter(itemList);
//        recyclerView.setAdapter(adapter);
        return root;
    }

    public double getMaxTime(){

        SQLiteDatabase db = DbManager.getInstance(getActivity()).dbHelper.getWritableDatabase();
        String[] projection = { "time" };
        String selection = "time" + " != ?";
        String[] selectionArgs = { "null" };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = "time"+ " DESC";

        Cursor cursor = db.query(
                "Time_Record",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        if (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            double t = Integer.parseInt(time)/3600.0;
            t = (int)(t * 100)/100.0;
            return t;
        }
        return 0;
    }
    public double getTotalTime(){
        SQLiteDatabase db = DbManager.getInstance(getActivity()).dbHelper.getWritableDatabase();
        String[] projection = { "time" };
        String selection = "time" + " != ?";
        String[] selectionArgs = { "null" };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = "time"+ " DESC";

        Cursor cursor = db.query(
                "Time_Record",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        double t=0;
        if (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            t += Integer.parseInt(time)/3600.0;

        }
        t = (int)(t * 100)/100.0;
        return t;
    }


    public void initAdapter(){


        SQLiteDatabase db = DbManager.getInstance(getActivity()).dbHelper.getWritableDatabase();
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
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " = ?";
        String[] selectionArgs = { "1" };
//
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
            Item apple = new Item(itemNames.get(i).toString(), R.drawable.item);
            itemList.add(apple);
        }


        //修改文字。
    }

}
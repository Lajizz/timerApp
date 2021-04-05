package com.example.timer.home_back;

import androidx.lifecycle.ViewModelProvider;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.timer.ClkActivity;
import com.example.timer.CreateGoalActivity;
import com.example.timer.HabitActivity;
import com.example.timer.R;
import com.example.timer.util.DbManager;
import com.example.timer.util.FeedReaderContract;


import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private HomeViewModel mViewModel;
    static private Button btn1;
    static private Button btn2;
    static private Button btn3;
    static Uri selectImage;
    static private ImageView img_view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        btn1 = (Button) root.findViewById(R.id.btn_check);
        btn2 = (Button) root.findViewById(R.id.btn_clk);
        btn3 = (Button) root.findViewById(R.id.changebg);
        img_view = (ImageView) root.findViewById(R.id.bg);
        setBg();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), HabitActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ClkActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(selectPicture(),100);
            }
        });


        return root;
    }

    public Intent selectPicture()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
            selectImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            //查询我们需要的数据
            Cursor cursor = getContext().getContentResolver().query(selectImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            insert_bg(picturePath);
            //拿到了图片的路径picturePath可以自行使用
            img_view.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            //img_view.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void setBg(){
        SQLiteDatabase db = DbManager.getInstance(getActivity()).dbHelper.getWritableDatabase();
        String[] projection = { BaseColumns._ID,"path"};
        String selection = FeedReaderContract.FeedEntry._ID + " > ?";
        String[] selectionArgs = { "0" };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.FeedEntry._ID + " DESC";

        Cursor cursor = db.query(
                "BG",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        if (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
            img_view.setImageBitmap(BitmapFactory.decodeFile(path));

        }else{
            Log.i("ssssssssssssssssssss", "setBg: no data");
        }

    }

    public void insert_bg(String path){
        //插入数据
        ContentValues values = new ContentValues();
        values.put("path",path);
        SQLiteDatabase db = DbManager.getInstance(getContext()).dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(db,0,1);
        db.insert("BG",null,values);
    }

}
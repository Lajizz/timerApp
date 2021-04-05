package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.timer.util.DbManager;
import com.example.timer.util.FeedReaderContract;
import com.example.timer.util.FeedReaderDbHelper;
import com.example.timer.util.Fruit;
import com.example.timer.util.FruitAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.timer.util.DbManager.dbHelper;


public class MainActivity extends AppCompatActivity {
    private Button btn1;
    //private Button btn2;
    private Button btn3;
    //private FeedReaderDbHelper dbHelper= new FeedReaderDbHelper(this);
    //public final FeedReaderDbHelper dbHelper= new FeedReaderDbHelper(this);
    //SQLiteDatabase db;
    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<String> mPermissionList = new ArrayList<>();

    // private ImageView welcomeImg = null;
    private static final int PERMISSION_REQUEST = 1;
// 检查权限

    private void checkPermission() {
        mPermissionList.clear();
        //判断哪些权限未授予
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
        }
    }
    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //btn1 = (Button) findViewById(R.id.btn_check);
        //btn2 = (Button) findViewById(R.id.btn_achieve);
        //btn3 = (Button) findViewById(R.id.btn_clk);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this,HabitActivity.class);
//                startActivity(intent);
//            }
//        });

//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this,AchieveActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this,ClkActivity.class);
//                startActivity(intent);
//            }
//        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //DbManager.getInstance(this);
        //db = DbManager.getInstance(this).dbHelper;
        //测试数据库
        //testDb();
        //测试适配器

    }

    public void testDb(){

        //插入信息
//        ContentValues values = new ContentValues();
//        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,"Math");
//        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT,"Read the book, 5 pages by day");
//        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,"30");
//        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE,"0");
//        SQLiteDatabase db = DbManager.getInstance(this).dbHelper.getWritableDatabase();
//        //dbHelper.onUpgrade(db,0,1);
//        long newR = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,null,values);

        //创建新表
//        String create_table =
//                "CREATE TABLE " + "Time_Record" + " (" +
//                        "time" + " INTEGER" + ")";
//        db.execSQL(create_table);
        //db.close();
        //dbHelper.onUpgrade(db,0,1);

//        //读取信息
//        String[] projection = {
//                BaseColumns._ID,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
//        };
//
//        // Filter results WHERE "title" = 'My Title'
//        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = { "Read a Book" };
//
//        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
//
//        Cursor cursor = db.query(
//                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
//                projection,             // The array of columns to return (pass null to get all)
//                selection,              // The columns for the WHERE clause
//                selectionArgs,          // The values for the WHERE clause
//                null,                   // don't group the rows
//                null,                   // don't filter by row groups
//                sortOrder               // The sort order
//        );
//
//        List itemIds = new ArrayList<>();
//        while(cursor.moveToNext()) {
//            String itemId = cursor.getString(
//                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
//            itemIds.add(itemId);
//        }
//        for (int i = 0; i < itemIds.size(); i++) {
//            Log.i("info: ",itemIds.get(i).toString());
//        }
//        cursor.close();

        //删除表
//        String delete_table =
//                "DROP TABLE IF EXISTS " + "Goal";
//
//        db.execSQL(delete_table);
    }





}
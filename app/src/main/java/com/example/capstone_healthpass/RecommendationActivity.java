package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstone_healthpass.DB.DBType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RecommendationActivity extends Activity  {
    String receivedData;
    Intent intent;
    DBType dbHelper = null;
    TextView textView;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        textView = findViewById(R.id.textView);
        dbTest(textView);
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(RecommendationActivity.this, MainActivity.class);
                        startActivity(intent);//다음 액티비티 화면에
                        AppManager.finishAllActivities();
                        break;
                    case R.id.navigation_mypage:
                        Intent intent1 = new Intent(RecommendationActivity.this, MYpageActivity.class);
                        startActivity(intent1);//다음 액티비티 화면에
                        // 예: 마이페이지 화면으로 이동
                        AppManager.finishAllActivities();
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(RecommendationActivity.this,QrActivity.class);
                        startActivity(intent3);
                        AppManager.finishAllActivities();
                        break;
                }
                return true;
            }
        });
    }

    @SuppressLint("Range")
    public void dbTest(TextView textView){
        intent = getIntent();
        receivedData = intent.getStringExtra("name");

        dbHelper = new DBType(RecommendationActivity.this);
        String query = "SELECT * FROM info where exercise_area = ? ORDER BY RANDOM() LIMIT 1";
        Log.d("query",query);
        Cursor cursor = dbHelper.validRawQuery(query,receivedData);

        String area="",name="",description="";
        if (cursor.moveToFirst()){
            area = cursor.getString(cursor.getColumnIndex("exercise_area"));
            name = cursor.getString(cursor.getColumnIndex("exercise_name"));
            description = cursor.getString(cursor.getColumnIndex("description"));
        }
        TextView textView1 = findViewById(R.id.ex_area);
        TextView textView2 = findViewById(R.id.ex_name);
        TextView textView3 = findViewById(R.id.ex_description);
        TextView textView4 = findViewById(R.id.youtube);
        String newName = name.replace(" ","+");
        String text = "https://www.youtube.com/results?search_query="+newName+"+운동+방법";


        textView1.setText(area);
        textView2.setText(name);
        textView3.setText(description);
        textView4.setText(text);


    }


}
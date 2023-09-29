package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.capstone_healthpass.DB.DBType;

import java.util.ArrayList;

public class RecommendationActivity extends Activity  {
    String receivedData;
    Intent intent;
    DBType dbHelper = null;
    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        textView = findViewById(R.id.textView);
        dbTest(textView);


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

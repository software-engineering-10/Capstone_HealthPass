package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_healthpass.DB.DBType;

public class RoutineActivity extends Activity {
    private RadioGroup radioGroup;

    String str = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 선택된 라디오 버튼의 ID를 가져옵니다.
                RadioButton selectedRadioButton = findViewById(checkedId);

                // 선택된 라디오 버튼의 텍스트를 가져옵니다.
                str = selectedRadioButton.getText().toString();

                // 선택된 라디오 버튼의 텍스트를 토스트 메시지로 표시합니다.
                Toast.makeText(RoutineActivity.this, "선택된 라디오 버튼: " + str, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void clickCheck(View view){
        if (str!="") {
            Intent intent = new Intent(RoutineActivity.this, RecommendationActivity.class);
            intent.putExtra("name", str);
            Log.d("strCheck",str);
            startActivity(intent);//다음 액티비티 화면에 출력
        }
        else{
            Toast.makeText(this, "부위를 선택해주세요", Toast.LENGTH_SHORT).show();
        }

    }

}

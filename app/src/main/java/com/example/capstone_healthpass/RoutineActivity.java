package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.capstone_healthpass.DB.DBType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RoutineActivity extends Activity {
    private RadioGroup radioGroup;
    private BottomNavigationView bottomNavigationView;

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





            }
        });
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(RoutineActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);//다음 액티비티 화면에

                        break;
                    case R.id.navigation_mypage:
                        Intent intent1 = new Intent(RoutineActivity.this, MYpageActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);//다음 액티비티 화면에

                        // 예: 마이페이지 화면으로 이동
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(RoutineActivity.this,QrActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);

                        break;
                }
                return true;
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
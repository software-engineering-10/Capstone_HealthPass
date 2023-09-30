package com.example.capstone_healthpass;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.capstone_healthpass.DB.DataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Button join_Btn;
    private Button reserve_btn;
    private Button routine_btn;
    private Button gps_btn;
    private TextView name;
    private Button Health_diary_btn;
    private WeekPlanFragment weekPlanFragment;
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            Log.i("권한 테스트", "권한 추가해야함.");
        } else {
            Log.i("권한 테스트", "처음부터 권한이 있었어요.");
        }
        getDB();
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);//다음 액티비티 화면에
                        break;
                    case R.id.navigation_mypage:
                        Intent intent1 = new Intent(MainActivity.this, MYpageActivity.class);
                        startActivity(intent1);//다음 액티비티 화면에
                        // 예: 마이페이지 화면으로 이동
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(MainActivity.this, QrActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        join_Btn = (Button) findViewById(R.id.join_Btn);
        join_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 액티비티로 가는 것
                //Intent
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);

                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
        //회원가입 누르면 JoinActivity로 이동

        name = (TextView) findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) findViewById(R.id.name);
                Intent intent = getIntent();
                textView.setText(intent.getStringExtra("nameText").toString());
//가져온 값을 뿌려줌 메인에서 클릭을 해야지만 사람이름이 나옴. 이거 어케 db에 저장합쇼..?
            }
        });

        reserve_btn = (Button) findViewById(R.id.reserve_btn);
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 액티비티로 가는 것
                //Intent
                Intent intent = new Intent(MainActivity.this, ReserveDaytimeActivity.class);
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
//운동기구 예약 누르면 DaytimeActivity로 이동

        //헬스다이어리 누르면 WeekPlanFragment 로이동


        routine_btn = (Button) findViewById(R.id.routine_btn);
        routine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoutineActivity.class);

                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
//운동추천 누르면 RoutineActivity로 이동


        gps_btn= (Button) findViewById(R.id.gps_btn);
        gps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,gpsActivity.class);
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
        //메인엑티비티에서 스크롤엑티비티로 이동가능하게하는intent 사용



    Health_diary_btn =findViewById(R.id.Health_diary_btn);

// Health_diary_btn 클릭 이벤트 핸들러 설정
        Health_diary_btn.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        // 기존 버튼들 숨기기
        join_Btn.setVisibility(View.GONE);
        reserve_btn.setVisibility(View.GONE);
        routine_btn.setVisibility(View.GONE);
        gps_btn.setVisibility(View.GONE);
        Health_diary_btn.setVisibility(View.GONE);

        // WeekPlanFragment를 화면에 표시
        if (weekPlanFragment == null) {
            weekPlanFragment = new WeekPlanFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weekPlanFragment)
                .commit();
    }
    });
}

    // 뒤로 가기 버튼 또는 다른 방법으로 WeekPlanFragment를 숨길 때 기존 버튼들을 다시 표시
// 예를 들어, WeekPlanFragment에서 뒤로 가기 버튼을 처리할 때
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // 기존 버튼들 다시 표시
        join_Btn.setVisibility(View.VISIBLE);
        reserve_btn.setVisibility(View.VISIBLE);
        routine_btn.setVisibility(View.VISIBLE);
        gps_btn.setVisibility(View.VISIBLE);
        Health_diary_btn.setVisibility((View.VISIBLE));
    }
    @SuppressLint("Range")
    public void getDB(){
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.close();
    }
}


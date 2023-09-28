package com.example.capstone_healthpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class JoinActivity extends AppCompatActivity {


    private ImageButton client;
    private EditText join_name,join_email,join_password,join_pwck;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        join_name= findViewById(R.id.join_name);
        join_email = findViewById(R.id.join_email);
        join_password= findViewById(R.id.join_password);
        join_pwck = findViewById(R.id.join_pwck);
        client = findViewById(R.id.client);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 버튼 클릭 시 실행되는 코드
                String email = join_email.getText().toString();
                String password = join_password.getText().toString();

                if (isValid(email, password)) {
                    // 회원가입 로직을 이곳에 추가
                    // 예를 들어, 서버에 회원 정보를 전송하거나 로컬 데이터베이스에 저장
                    Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(JoinActivity.this, "올바른 이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                        startActivity(intent);//다음 액티비티 화면에
                        break;
                    case R.id.navigation_mypage:
                        Intent intent1 = new Intent(JoinActivity.this, MYpageActivity.class);
                        startActivity(intent1);//다음 액티비티 화면에
                        // 예: 마이페이지 화면으로 이동
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(JoinActivity.this,QrActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });
        // 클라이언트 버튼 클릭 리스너
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = join_name.getText().toString();

                // MainActivity로 데이터를 전달하기 위한 Intent 생성
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                intent.putExtra("nameText", name); // "nameText" 키로 이름 데이터 전달
                startActivity(intent);
            }
        });
    }

    // 입력 유효성 검사 메서드 예제
    private boolean isValid(String email, String password) {
        // 간단한 예제: 이메일 형식 및 비밀번호 길이 검사
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 6;
    }
}





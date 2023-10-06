package com.example.capstone_healthpass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.server.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {


    private Button client;
    private EditText join_name,join_email,join_password,join_pwck,join_phone;
    private BottomNavigationView bottomNavigationView;
    Gson gson;
    private Retrofit retrofit;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        join_name= findViewById(R.id.join_name);
        join_email = findViewById(R.id.join_email);
        join_password= findViewById(R.id.join_password);
        join_pwck = findViewById(R.id.join_pwck);
        client = findViewById(R.id.client);
        join_phone = findViewById(R.id.join_phone);
        bottomNavigationView = findViewById(R.id.bottom_navigation);



        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);//다음 액티비티 화면에

                        break;
                    case R.id.navigation_mypage:
                        Intent intent1 = new Intent(JoinActivity.this, MYpageActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);//다음 액티비티 화면에

                        // 예: 마이페이지 화면으로 이동
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(JoinActivity.this,QrActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);

                        break;
                }
                return true;
            }
        });


    }

    // 입력 유효성 검사 메서드 예제
    private boolean isValid(String email, String password, String check) {
        // 간단한 예제: 이메일 형식 및 비밀번호 길이 검사
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 6 && check.length() >= 6 && password.equals(check);
    }

    public void register(View v){

        String name = join_name.getText().toString();
        String email = join_email.getText().toString();
        String password = join_password.getText().toString();
        String phone = join_phone.getText().toString();
        String check = join_pwck.getText().toString();

        if (isValid(email, password,check)) {
            registerAccount(name,phone,email,password);



        } else {
            if(!password.equals(check)){
                Toast.makeText(JoinActivity.this, "두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<6)
                Toast.makeText(JoinActivity.this, "비밀번호는 6자 이상입니다.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(JoinActivity.this, "올바른 이메일을 입력하세요.", Toast.LENGTH_SHORT).show();

        }
    }
    private void registerAccount(final String name,final String phone, final String email, final String password){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://b1ca-220-69-208-115.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        apiService = retrofit.create(ApiService.class);
        apiService.requestPost(name,phone,email,password).enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d("checkResponse","response "+response.code());

                if(response.code()==201) {
                    Toast.makeText(JoinActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                    Log.d("RegisterSuccess", "회원가입 성공");
                    Intent result = new Intent(JoinActivity.this, MainActivity.class);
                    startActivity(result);
                    finish();
                }
                else if(response.code()==202){
                    Log.d("RegisterError", "이메일 중복");
                    Toast.makeText(JoinActivity.this, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Log.d("RegisterError", t.getMessage());
            }
        });
    }


}





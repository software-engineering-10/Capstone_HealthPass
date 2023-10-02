package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.server.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ApiService apiService;

    private Button btnCheck, btnCancel;

    private EditText emailText, pwdText;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        btnCheck = findViewById(R.id.login_activity_btn);
        btnCancel = findViewById(R.id.cancel_btn);
        emailText = findViewById(R.id.editTextTextEmailAddress);
        pwdText = findViewById(R.id.editTextTextPassword);

    }

    public void LoginAccount(View v){
        String email = emailText.getText().toString();
        String pwd = pwdText.getText().toString();
        if (email!=null && pwd!=null){
            loginAccount(email,pwd);
        }
    }
    public void cancel(View v){

    }

    private void loginAccount(final String email, final String password){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://0666-220-69-208-115.ngrok.io")
                .addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();

        apiService = retrofit.create(ApiService.class);
        apiService.loginPost(email,password).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d("checkResponse","response "+response.code());
                if(response.code()==201) {
                    Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                    Intent result = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(result);
                }
                else if(response.code()==202){
                    Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(response.code()==203){
                    Toast.makeText(LoginActivity.this, "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Log.d("LoginError", t.getMessage());

                Log.d("responseBody",call.request().body().toString());
            }
        });
    }
}

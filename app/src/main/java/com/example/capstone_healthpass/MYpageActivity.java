package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.server.ApiService;
import com.example.capstone_healthpass.server.Reservation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MYpageActivity  extends AppCompatActivity {

    public String fname=null;
    public String str=null;

    private String reserveMinute;
    private String reserveDay;
    private String reserveTime;
    private String reserveEx_name;
    private String reserveSeat;
    private RadioGroup radioGroup;
    private ArrayList<String> dataList; // 라디오 버튼에 추가할 데이터 목록
    public BottomNavigationView bottomNavigationView;
    private Retrofit retrofit;
    private ApiService apiService;
    private ArrayList<String> array;
    String selectedOption="";
    private Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        reservedInfos(MainActivity.email);
        button = findViewById(R.id.reserveCancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canceled(reserveDay,reserveTime,reserveMinute,reserveSeat,reserveEx_name);
            }
        });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(MYpageActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);//다음 액티비티 화면에
                        break;
                    case R.id.navigation_mypage:
                        Intent intent1 = new Intent(MYpageActivity.this, MYpageActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);//다음 액티비티 화면에

                        // 예: 마이페이지 화면으로 이동
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(MYpageActivity.this, QrActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });
        radioGroup = findViewById(R.id.radioGroup);


    }
    public void reservedInfos(final String email){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://b1ca-220-69-208-115.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        apiService = retrofit.create(ApiService.class);
        apiService.reservedInfo(email).enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if(response.code()==201){
                    List<Reservation> reservationList = response.body();
                    array=new ArrayList<>();
                    for(Reservation reservation:reservationList) {
                        reserveMinute = reservation.getMinute();
                        reserveTime = reservation.getTime();
                        reserveDay = reservation.getDay();
                        reserveSeat = reservation.getSeat();
                        reserveEx_name = reservation.getEx_name();
                        String str = reserveDay + " " + reserveTime + " " + reserveMinute + " " + reserveSeat + " "+reserveEx_name;
                        RadioButton radioButton = new RadioButton(MYpageActivity.this);
                        radioButton.setText(str);
                        radioGroup.addView(radioButton);
                        radioButton.setOnClickListener(view -> {
                            // 선택된 라디오 버튼의 텍스트를 가져와서 사용 가능
                            selectedOption = ((RadioButton) view).getText().toString();
                            Toast.makeText(MYpageActivity.this, selectedOption, Toast.LENGTH_SHORT).show();
                        });

                        array.add(str);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.d("myPageTest",t.toString());
            }
        });
    }
    public void canceled(final String day, final String time, final String minute, final String seat, final String ex_name){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://b1ca-220-69-208-115.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        apiService = retrofit.create(ApiService.class);
        apiService.reservedCancel(day,time,minute,seat,ex_name).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201){
                    Toast.makeText(MYpageActivity.this, selectedOption+"예약을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MYpageActivity.this,MYpageActivity.class);
                    startActivity(intent);
                    finish();

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("삭제",t.toString());
            }
        });
    }

}

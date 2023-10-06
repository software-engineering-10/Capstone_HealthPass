package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstone_healthpass.server.ApiService;

import retrofit2.Retrofit;

public class ReserveEndActivity extends Activity {

    TextView tvYear4, tvMonth4, tvDay4, tvHour4, tvMinute4;
    TextView tvName3;

    Button btnReturnToHome;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_end);
        setTitle("예약 완료");

        tvName3 = (TextView) findViewById(R.id.tvName3);
        tvName3.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).tvName.getText());
        //예약 날 정보
        //이전 day time 변수들
        tvYear4 = (TextView) findViewById(R.id.tvYear4);
        tvMonth4 = (TextView) findViewById(R.id.tvMonth4);
        tvDay4 = (TextView) findViewById(R.id.tvDay4);
        tvHour4 = (TextView) findViewById(R.id.tvHour4);
        tvMinute4 = (TextView) findViewById(R.id.tvMinute4);
        int minute = Integer.parseInt(tvMinute4.getText().toString());

        tvYear4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvYear.getText());


        tvMonth4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvMonth.getText());
        tvDay4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvDay.getText());
        tvHour4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvHour.getText());
        tvMinute4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvMinute.getText());
         if(minute==30){
             tvMinute4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvMinute.getText()+"~60");
         }
         else{
             tvMinute4.setText(((ReserveDaytimeActivity) ReserveDaytimeActivity.DayContext).tvMinute.getText()+"~30");
         }
        btnReturnToHome = (Button) findViewById(R.id.BtnReturnToHome);
        btnReturnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다시 메인 액티비티로 가는 것
                //Intent
                Intent intent = new Intent(ReserveEndActivity.this, MainActivity.class); //다음 클래스 정보 입력
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
    }
}





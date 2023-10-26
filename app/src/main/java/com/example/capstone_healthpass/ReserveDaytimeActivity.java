package com.example.capstone_healthpass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.capstone_healthpass.server.RetrofitManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveDaytimeActivity extends Activity {
    public static final int TIME_PICKER_INTERVAL = 30;
    private boolean mIgnoreEvent = false;
    //Context 로 다음 액티비티에서 정보 사용
    public static Context DayContext;

    RadioButton rdoCal, rdoTime;
    DatePicker dPicker;
    TimePicker tPicker;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMinute;
    Button btnDayOk;
    Button btnToTable;

    RetrofitManager retrofitManager = new RetrofitManager();
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserve_daytime);
        setTitle("예약 날짜 시간 선택");

        //컨텍스트 설정
        DayContext = this;

        // 라디오버튼 2개
        rdoCal = (RadioButton) findViewById(R.id.rdoCal);
        rdoTime = (RadioButton) findViewById(R.id.rdoTime);

        // FrameLayout의 2개 위젯
        dPicker = (DatePicker) findViewById(R.id.datePicker1);

        tPicker = (TimePicker) findViewById(R.id.timePicker1);

        // 텍스트뷰 중에서 연,월,일,시,분 숫자
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMinute = (TextView) findViewById(R.id.tvMinute);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        int m = Calendar.getInstance().get(Calendar.MINUTE);

        int hour = currentDate.getHours()+1;
        if(hour==24)
            hour=0;
        dPicker.setMinDate(System.currentTimeMillis());
        if(m>30) {
            tPicker.setMinute(0);
            tPicker.setHour(hour);
        }
        else {

            tPicker.setMinute(30);
        }





        tPicker.setVisibility(View.INVISIBLE);
        dPicker.setVisibility(View.INVISIBLE);
        tPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {


                // 분 값이 0 또는 30이 아닌 경우 수정
                if (!mIgnoreEvent) {
                    if (minute % TIME_PICKER_INTERVAL != 0) {
                        int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
                        minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
                        if (minute == 60) {
                            minute = 0;
                        }
                        mIgnoreEvent = true;
                        tPicker.setCurrentMinute(minute);
                        mIgnoreEvent = false;
                    }
                }
            }
        });
        rdoCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tPicker.setVisibility(View.INVISIBLE);
                dPicker.setVisibility(View.VISIBLE);
            }
        });

        rdoTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tPicker.setVisibility(View.VISIBLE);
                dPicker.setVisibility(View.INVISIBLE);
            }
        });

        btnDayOk = (Button) findViewById(R.id.BtnDayOk);
        btnDayOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYear.setText(Integer.toString(dPicker.getYear()));
                tvMonth.setText(Integer.toString(1 + dPicker.getMonth()));
                tvDay.setText(Integer.toString(dPicker.getDayOfMonth()));

                tvHour.setText(Integer.toString(tPicker.getCurrentHour()));
                tvMinute.setText(Integer.toString(tPicker.getCurrentMinute()));
            }
        });


        btnToTable = (Button) findViewById(R.id.BtnToTable);
        btnToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Integer.parseInt(tvYear.getText() .toString()));
                calendar.set(Calendar.MONTH, Integer.parseInt(tvMonth.getText().toString()));

                calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(tvDay.getText() .toString()));
                Date date = calendar.getTime();

                // 원하는 날짜 형식 지정
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // 날짜를 문자열로 변환
                String formattedDate = sdf.format(date);
                //다음 액티비티로 가는 것
                //Intent
                String day = formattedDate;
                String hour = tvHour.getText().toString();
                String minute = tvMinute.getText().toString();
                Calendar currentCalendar = Calendar.getInstance();
                int currentYear = currentCalendar.get(Calendar.YEAR);
                int currentMonth = currentCalendar.get(Calendar.MONTH);
                int currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);

                if((tPicker.getHour() < Calendar.getInstance().get(Calendar.HOUR_OF_DAY) &&
                        tPicker.getMinute() < Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                || dPicker.getYear() < currentYear
                || dPicker.getMonth() < currentMonth
                || dPicker.getDayOfMonth() < currentDayOfMonth){
                    Toast.makeText(getApplicationContext(), "이전 날짜는 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                    reservedTime(day,hour,minute,MainActivity.email);

            }
        });
    }
    public void reservedTime(final String day, final String time, final String minute, final String email){

        retrofitManager.getApiService().reservedTime(day,time,minute,MainActivity.email).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.code()==201){
                    Intent intent = new Intent(ReserveDaytimeActivity.this, ReserveMachineActivity.class); //다음 클래스 정보 입력

                    startActivity(intent);//다음 액티비티 화면에 출력
                    finish();
                }
                else if (response.code()==203){
                    Toast.makeText(ReserveDaytimeActivity.this,"선택하신 시간은 이미 예약하셨습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("Error",t.toString());
            }
        });
    }
}

package com.example.capstone_healthpass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.capstone_healthpass.server.ApiService;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReserveMachineActivity extends Activity {
    //Context로 다음 액티비티에서 정보 사용
    private Retrofit retrofit;
    private ApiService apiService;
    public static Context ReserveContext;
    //daytime 에서 가져올 변수
    TextView tvYear2, tvMonth2, tvDay2, tvHour2, tvMinute2;

    //변수
    TextView seat,personnel;//자리 버튼 선택시 2가지 정보 보여짐

    Button btnNext; //다음 액티비티로 넘어가는 버튼

    Button[] numButtons = new Button[16];
    Integer[] numBtnIDs = { R.id.BtnNum0,R.id.BtnNum1,R.id.BtnNum2,R.id.BtnNum3,R.id.BtnNum4,
            R.id.BtnNum5,R.id.BtnNum6,R.id.BtnNum7,
            R.id.BtnNum8,R.id.BtnNum9, R.id.BtnNum10,R.id.BtnNum11,
            R.id.BtnNum12, R.id.BtnNum13, R.id.BtnNum14,R.id.BtnNum15};

    int i;
    //dialog 변수
    TextView tvName,tvPhone; //보여지는 텍스트
    EditText dlgEdtName,dlgEdtPhone; //dialg박스에 입력 받는 부분
    View dialogView; //dialog1.xml 인플레이트할 변수

    //이전 Time Day 로 돌아가기
    Button btnReturnToDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_machine);
        setTitle("운동기구 예약");

        //이전 day time 변수들
        tvYear2 = (TextView) findViewById(R.id.tvYear2);
        tvMonth2 = (TextView) findViewById(R.id.tvMonth2);
        tvDay2 = (TextView) findViewById(R.id.tvDay2);
        tvHour2 = (TextView) findViewById(R.id.tvHour2);
        tvMinute2 = (TextView) findViewById(R.id.tvMinute2);

        tvYear2.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvYear.getText());
        tvMonth2.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvMonth.getText());
        tvDay2.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvDay.getText());
        tvHour2.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvHour.getText());
        tvMinute2.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvMinute.getText());

        //예약자 명,연락처 dialog를 위한 변수 연결
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        if(MainActivity.userName!=""&&MainActivity.phone!=""){
            tvName.setText(MainActivity.userName);
            tvPhone.setText(MainActivity.phone);
        }
        else{
            tvName.setText("");
            tvPhone.setText("");
        }

        //이전으로 돌아가기
        btnReturnToDay =(Button)findViewById(R.id.BtnReturnToDay);
        btnReturnToDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Context
        ReserveContext = this;

        //좌석 버튼 관련 요소들 연결
        seat = (TextView) findViewById(R.id.Seat);
        personnel = (TextView) findViewById(R.id.Personnel);

        for(i=0;i<numButtons.length;i++){
            numButtons[i] = (Button)findViewById(numBtnIDs[i]);
        }



        //버튼 번호따라 자리 선택된 정보 띄움
        for(i=0;i<numButtons.length;i++){
            final int index;
            index = i;
            numButtons[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    //클릭 시 색 바뀌는거
                    //빨강 : 다리
                    //파랑 : 팔, 어깨
                    if (index < 5) {
                        seat.setText((index + 1) + "번"); //버튼 번호를 받아와 띄움
                        personnel.setText("런닝머신 (30분)");
                        seat.setTextColor(Color.BLUE);
                        personnel.setTextColor(Color.BLUE);

                    } else if (index == 5 || index == 8) {
                        seat.setText((index + 1) + "번"); //버튼 번호를 받아와 띄움
                        personnel.setText("펙덱머신 (30분)");
                        seat.setTextColor(Color.BLUE);
                        personnel.setTextColor(Color.BLUE);

                    } else if (index == 6 || index == 7) {
                        seat.setText((index + 1) + "번 자리"); //버튼 번호를 받아와 띄움
                        personnel.setText("사이클 머신 (30분)");
                        seat.setTextColor(Color.RED);
                        personnel.setTextColor(Color.RED);


                    } else if (index == 10) {
                        seat.setText((index + 1) + "번 자리"); //버튼 번호를 받아와 띄움
                        personnel.setText("사이클 머신 (30분)");
                        seat.setTextColor(Color.RED);
                        personnel.setTextColor(Color.RED);



                    } else if (index == 9 || index == 11) {
                        seat.setText((index + 1) + "번"); //버튼 번호를 받아와 띄움
                        personnel.setText("랫폴다운 머신 (30분)");
                        seat.setTextColor(Color.BLUE);
                        personnel.setTextColor(Color.BLUE);


                    } else if (index == 12 || index == 13){
                        seat.setText((index + 1) + "번"); //버튼 번호를 받아와 띄움
                        personnel.setText("레그프레스 (30분)");
                        seat.setTextColor(Color.RED);
                        personnel.setTextColor(Color.RED);

                    }
                    else{
                        seat.setText((index + 1) + "번"); //버튼 번호를 받아와 띄움
                        personnel.setText("벤치프레스 (30분)");
                        seat.setTextColor(Color.RED);
                        Log.d("체크",index+"번");
                        personnel.setTextColor(Color.RED);
                    }

                }
            });
        }//for문 끝



        //다음 액티비티로..넘어가기전에 입력 정보들 확인 후 입력 안한것 하나라도 있으면 못넘어가게
        btnNext = (Button)findViewById(R.id.BtnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seat.length() <= 0 || tvName.length() <= 0 || tvPhone.length() <= 0){ //좌석 정보 없으면 인원 정보도 없는 것

                    if(seat.length() <= 0){
                        Toast.makeText(getApplicationContext(),"운동기구가 있는 자리를 선택하세요"
                                ,Toast.LENGTH_SHORT).show();
                    }
                    if(tvName.length() <= 0 && tvPhone.length() <= 0){
                        Toast.makeText(getApplicationContext(),"예약자 정보를 입력하세요"
                                ,Toast.LENGTH_SHORT).show();
                    }
                    if(tvName.length() > 0 && tvPhone.length() <= 0){
                        Toast.makeText(getApplicationContext(),
                                "예약자 '연락처'는 필수 정보입니다.\n 예약자 정보를 다시 입력해주세요"
                                ,Toast.LENGTH_SHORT).show();
                    }
                    if(tvName.length() <= 0 && tvPhone.length() > 0){
                        Toast.makeText(getApplicationContext(),
                                "예약자 '성함'은 필수 정보입니다.\n 예약자 정보를 다시 입력해주세요"
                                ,Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, Integer.parseInt(tvYear2.getText() .toString()));
                    calendar.set(Calendar.MONTH, Integer.parseInt(tvMonth2.getText().toString())); // 월은 0부터 시작하므로 9는 10월을 나타냅니다.
                    calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(tvDay2.getText() .toString()));
                    Date date = calendar.getTime();

                    // 원하는 날짜 형식 지정
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    // 날짜를 문자열로 변환
                    String formattedDate = sdf.format(date);
                    //다음 액티비티로 가는 것
                    //Intent
                    String day = formattedDate;

                    String hour = tvHour2.getText().toString();
                    String minute = tvMinute2.getText().toString();
                    String seats = seat.getText().toString();
                    String name = personnel.getText().toString();
                    reservedMachine(day,hour,minute,seats,name);

                }

            }
        });


    }//onCreate 끝
    public void reservedMachine(final String day, final String time, final String minute, final String seat, final String ex_name){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://b1ca-220-69-208-115.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        apiService = retrofit.create(ApiService.class);
        apiService.reservedMachine(day,time,minute,seat,ex_name).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                if(response.code()==201) {
                    Intent intent = new Intent(ReserveMachineActivity.this, ReserveConfirmActivity.class);
                    startActivity(intent);//다음 액티비티 화면에 출력
                }
                else if (response.code()==202){
                    Toast.makeText(ReserveMachineActivity.this,"선택하신 시간의 운동기구는 이미 예약 되어 있습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }
}

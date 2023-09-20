package com.example.capstone_healthpass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ReserveMachineActivity extends Activity {
    //Context로 다음 액티비티에서 정보 사용
    public static Context ReserveContext;
    //daytime 에서 가져올 변수
    TextView tvYear2, tvMonth2, tvDay2, tvHour2, tvMinute2;

    //변수
    TextView seat,personnel;//자리 버튼 선택시 2가지 정보 보여짐
    Button btnInfo;//예약자 정보 입력
    Button btnNext; //다음 액티비티로 넘어가는 버튼

    Button[] numButtons = new Button[15];
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
        btnInfo = (Button)findViewById(R.id.BtnInfo);
        for(i=0;i<numButtons.length;i++){
            numButtons[i] = (Button)findViewById(numBtnIDs[i]);
        }
        //예약자 명,연락처 dialog를 위한 변수 연결
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        btnInfo = (Button)findViewById(R.id.BtnInfo);

        //다이얼로그 창 띄우는 버튼
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog1.xml 파일 인플레이트
                dialogView = (View) View.inflate(ReserveMachineActivity.this,R.layout.activity_reserve_name,null);

                //alertDialog.Builder 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(ReserveMachineActivity.this);
                dlg.setTitle("예약자 정보 입력");
                dlg.setIcon(R.drawable.ic_menu_allfriends);
                dlg.setView(dialogView); //인플레이트한 것을 대화상자로 사용

                //setPositiveButton
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dlgEdtName = (EditText)dialogView.findViewById(R.id.dlgEdt1);
                                dlgEdtPhone =(EditText)dialogView.findViewById(R.id.dlgEdt2);

                                tvName.setText(dlgEdtName.getText().toString());
                                tvPhone.setText(dlgEdtPhone.getText().toString());

                                Toast.makeText(getApplicationContext(),"예약자 정보 확인했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                //
                dlg.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"취소했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                dlg.show(); // dialog창 보이기
            }
        });

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
                        seat.setText((index + 1) + "번 "); //버튼 번호를 받아와 띄움
                        personnel.setText("런닝머신 (1시간)");
                        seat.setTextColor(Color.BLUE);
                        personnel.setTextColor(Color.BLUE);
                        Toast.makeText(getApplicationContext(),
                                "런닝머신" + (index + 1) + " 번을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                    } else if (index == 5 || index == 8) {
                        seat.setText((index + 1) + "번 "); //버튼 번호를 받아와 띄움
                        personnel.setText("펙덱머신 (30분)");
                        seat.setTextColor(Color.BLUE);
                        personnel.setTextColor(Color.BLUE);
                        Toast.makeText(getApplicationContext(),
                                "펙덱머신" + (index + 1) + " 번을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                    } else if (index == 6 || index == 7) {
                        seat.setText((index + 1) + "번 자리"); //버튼 번호를 받아와 띄움
                        personnel.setText("바이크 (30분)");
                        seat.setTextColor(Color.RED);
                        personnel.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(),
                                "바이크" + (index + 1) + "번을 선택하셨습니다.", Toast.LENGTH_SHORT).show();

                    } else if (index == 10) {
                        seat.setText((index + 1) + "번 자리"); //버튼 번호를 받아와 띄움
                        personnel.setText("바이크 (30분)");
                        seat.setTextColor(Color.RED);
                        personnel.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(),
                                "바이크" + (index + 1) + "번을 선택하셨습니다.", Toast.LENGTH_SHORT).show();


                    } else if (index == 9 || index == 11) {
                        seat.setText((index + 1) + "번 "); //버튼 번호를 받아와 띄움
                        personnel.setText("하이풀 머신 (30분)");
                        seat.setTextColor(Color.BLUE);
                        personnel.setTextColor(Color.BLUE);
                        Toast.makeText(getApplicationContext(),
                                "하이풀 머신" + (index + 1) + "번을 선택하셨습니다.", Toast.LENGTH_SHORT).show();

                    } else{
                        seat.setText((index + 1) + "번 "); //버튼 번호를 받아와 띄움
                        personnel.setText("레그프레스 (30분)");
                        seat.setTextColor(Color.RED);
                        personnel.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(),
                                "레그프레스" + (index + 1) + "번을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
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
                    //Intent
                    Intent intent = new Intent(ReserveMachineActivity.this,ReserveConfirmActivity.class);
                    startActivity(intent);//다음 액티비티 화면에 출력
                }

            }
        });

    }//onCreate 끝

}

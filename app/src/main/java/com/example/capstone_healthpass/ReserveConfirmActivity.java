package com.example.capstone_healthpass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ReserveConfirmActivity  extends Activity {

    //daytime 에서 가져올 변수
    TextView tvYear3, tvMonth3, tvDay3, tvHour3, tvMinute3;

    TextView tvName2,tvPhone2;
    TextView seat2,personnel2;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_confirm);
        setTitle("운동기구 예약 확정");

        //예약 날 정보
        //이전 day time 변수들
        tvYear3 = (TextView) findViewById(R.id.tvYear3);
        tvMonth3 = (TextView) findViewById(R.id.tvMonth3);
        tvDay3 = (TextView) findViewById(R.id.tvDay3);
        tvHour3 = (TextView) findViewById(R.id.tvHour3);
        tvMinute3 = (TextView) findViewById(R.id.tvMinute3);

        tvYear3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvYear.getText());
        tvMonth3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvMonth.getText());
        tvDay3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvDay.getText());
        tvHour3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvHour.getText());
        tvMinute3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvMinute.getText());

        //메인 액티비티로 이동해 정보 수정 가능 back기능
        Button btnReturn = (Button)findViewById(R.id.BtnReturnToTable);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //두번째 액티비티의 화면 동작 설계
        //이전 화면 정보 뿌리기
        tvName2 = (TextView)findViewById(R.id.tvName2);
        tvPhone2 = (TextView)findViewById(R.id.tvPhone2);
        seat2 = (TextView)findViewById(R.id.Seat2);
        personnel2 = (TextView)findViewById(R.id.Personnel2);

        //앞의 MainActivity 의 정보 받아오기
        //context로 액티비티 전체를 받아와 이전에 저장된 텍스트 변수의 텍스트를 가져옴
        tvName2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).tvName.getText());
        tvPhone2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).tvPhone.getText());
        seat2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).seat.getText());
        personnel2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).personnel.getText());

        btnConfirm =(Button)findViewById(R.id.BtnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(ReserveConfirmActivity.this);
                dlg.setTitle("예약 확정");
                dlg.setIcon(R.drawable.cat);
                dlg.setMessage("정말 예약을 확정하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"예약 확정 되었습니다.\n 완료 버튼을 누르세요",
                                Toast.LENGTH_SHORT).show();
                        //다음 페이지에서 인사 멘트로 끝내기??-예약해주셔서 감사합니다. 몇월 몇날 몇시 좌석으로 모시겠습니다.

                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소했습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show(); //***

            }
        });

        Button btnFinish = (Button)findViewById(R.id.BtnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent
                Intent intent = new Intent(ReserveConfirmActivity.this, ReserveEndActivity.class);
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });

    }

}